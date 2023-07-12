package is.technologies.banksConsole;

import is.technologies.entities.Bank;
import is.technologies.interfaces.IBankAccount;
import is.technologies.interfaces.ICentralBank;
import is.technologies.models.Client;
import is.technologies.service.CentralBank;
import is.technologies.tools.*;

import java.io.IOException;
import java.util.Scanner;

public class BanksConsole implements IBanksConsole
{
    public BanksConsole(){
         centralBank = new CentralBank();
    }
    private ICentralBank centralBank;
    public void printListOfOperations() {
        System.out.println("""
                1. Зарегестрировать банк
                2. Создать клиента(Имя,фамилия,адресс и номер паспорта)
                3. Создать дебетовый счет клиенту
                4. Создать кредитный счет клиенту
                5. Создать депозитный счет клиенту
                6. Поменять процентную ставку в банке
                7. Поменять коммисию банка для кредитных счетов
                8. Поменять лимитный перевод для неверефицованных аккаунтов
                9. Подписаться на уведомления от банка
                10. Отписаться от уведомлений от банка
                11. Пополнить баланс счета
                12. Вывести деньги с счета
                13. Отменить транзакцию
                14. Перевести деньги с одного счета на другой
                15. Выйти
                """);
    }
    public int getOutNumberOfTransaction() throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("Выберите номер транзакции (1,2,..,14)");
        int value = in.nextInt();
        if(value < 1 || value > 17)
            throw new NullPointerException();
        if(value != 1 && this.centralBank.GetBanks().size() == 0)
            throw new NullPointerException();
        return value;
    }
    public void addBankToSystem() throws LimitOfMoneyException, CreditInterestException, InterestOnBalanceException, MinAmountOfMoneyException {
        System.out.println("Введите имя банка");
        Scanner in = new Scanner(System.in);
        String bankName = in.next();
        System.out.println("Введите кредитный процент");
        double creditInterest = in.nextDouble();
        System.out.println("Введите процентную ставку");
        var interestOnBalance = in.nextDouble();
        System.out.println("Введите минимальный отрицательный баланс кредитного счета");
        var minOfNegativeAmountOfMoney = in.nextDouble();
        System.out.println("Введите лимит перевода");
        var limitOfMoneyTransaction = in.nextDouble();
        centralBank.AddBank(creditInterest,interestOnBalance,minOfNegativeAmountOfMoney,limitOfMoneyTransaction,bankName);
    }
    public void addClientToBank() throws PassportNumberException {
        System.out.println("Введите имя банка");
        Scanner in = new Scanner(System.in);
        String bankName = in.next();
        System.out.println("Введите имя клиента");
        String clientName = in.next();
        System.out.println("Введите фамилию клиента");
        String clientSurname = in.next();
        System.out.println("Введите пасспорт клиента");
        int clientPassport = in.nextInt();
        System.out.println("Введите адресс клиента");
        String clientAdrres = in.next();
        Bank bank = centralBank.FindBank(bankName);
        centralBank.AddClientFullVersion(bank,clientName,clientSurname,clientAdrres,clientPassport);
    }
    public void addDebitAccountToClient() throws LimitOfMoneyException, NegativeAmountOfMoneyException, InterestOnBalanceException {
        System.out.println("Введите имя банка");
        Scanner in = new Scanner(System.in);
        String bankName = in.next();
        System.out.println("Введите имя клиента");
        String clientName = in.next();
        System.out.println("Введите фамилию клиента");
        String clientSurname = in.next();
        System.out.println("Введите первый взнос");
        double firstContribution = in.nextDouble();
        Client client = centralBank.FindClient(bankName,clientName,clientSurname);
        Bank bank = centralBank.FindBank(bankName);
        centralBank.AddDebitAccount(bank,client,firstContribution);
    }
    public void addDepositAccountToClient() throws LimitOfMoneyException, NegativeAmountOfMoneyException, InterestOnBalanceException {
        System.out.println("Введите имя банка");
        Scanner in = new Scanner(System.in);
        String bankName = in.next();
        System.out.println("Введите имя клиента");
        String clientName = in.next();
        System.out.println("Введите фамилию клиента");
        String clientSurname = in.next();
        System.out.println("Введите первый взнос");
        double firstContribution = in.nextDouble();
        System.out.println("Введите срок действия аккаунта");
        int accountTerm = in.nextInt();
        Client client = centralBank.FindClient(bankName,clientName,clientSurname);
        Bank bank = centralBank.FindBank(bankName);
        centralBank.AddDepositeAccount(bank,client,firstContribution,accountTerm);
    }
    public void addCreditAccountToClient() throws LimitOfMoneyException, NegativeAmountOfMoneyException, InterestOnBalanceException, CreditInterestException, MinAmountOfMoneyException {
        System.out.println("Введите имя банка");
        Scanner in = new Scanner(System.in);
        String bankName = in.next();
        System.out.println("Введите имя клиента");
        String clientName = in.next();
        System.out.println("Введите фамилию клиента");
        String clientSurname = in.next();
        System.out.println("Введите первый взнос");
        double firstContribution = in.nextDouble();
        Client client = centralBank.FindClient(bankName,clientName,clientSurname);
        Bank bank = centralBank.FindBank(bankName);
        centralBank.AddCreditAccount(bank,client,firstContribution);
    }
    public void changeInterestOfBank() throws InterestOnBalanceException {
        System.out.println("Введите имя банка");
        Scanner in = new Scanner(System.in);
        String bankName = in.next();
        System.out.println("Введите новую процентную ставку");
        double newInterestOnBalance = in.nextDouble();
        Bank bank = centralBank.FindBank(bankName);
        centralBank.ChangeInterestOnBalance(bank,newInterestOnBalance);
    }
    public void changeCreditInterestfBank() throws InterestOnBalanceException, CreditInterestException {
        System.out.println("Введите имя банка");
        Scanner in = new Scanner(System.in);
        String bankName = in.next();
        System.out.println("Введите новую коммисию для банка");
        double newCreditInterest = in.nextDouble();
        Bank bank = centralBank.FindBank(bankName);
        centralBank.ChangeCredintInterest(bank,newCreditInterest);
    }
    public void changeLimitOfTransfer() throws InterestOnBalanceException {
        System.out.println("Введите имя банка");
        Scanner in = new Scanner(System.in);
        String bankName = in.next();
        System.out.println("Введите новый лимит перевода");
        double newLimitOfTransfer = in.nextDouble();
        Bank bank = centralBank.FindBank(bankName);
        centralBank.ChangeLimitOfMoneyTransaction(bank,newLimitOfTransfer);
    }
    public void subscribeClient()
    {
        System.out.println("Введите имя банка");
        Scanner in = new Scanner(System.in);
        String bankName = in.next();
        System.out.println("Введите имя клиента");
        String clientName = in.next();
        System.out.println("Введите фамилию клиента");
        String clientSurname = in.next();
        Client client = centralBank.FindClient(bankName,clientName,clientSurname);
        Bank bank = centralBank.FindBank(bankName);
        centralBank.Subscribe(bank,client);
    }
    public void unsubscribeClient()
    {
        System.out.println("Введите имя банка");
        Scanner in = new Scanner(System.in);
        String bankName = in.next();
        System.out.println("Введите имя клиента");
        String clientName = in.next();
        System.out.println("Введите фамилию клиента");
        String clientSurname = in.next();
        Client client = centralBank.FindClient(bankName,clientName,clientSurname);
        Bank bank = centralBank.FindBank(bankName);
        centralBank.Unsubscribe(bank,client);
    }
    public void upToBalanceToClient() throws DateException, VerificationException, MinAmountOfMoneyException {
        System.out.println("Введите имя банка");
        Scanner in = new Scanner(System.in);
        String bankName = in.next();
        System.out.println("Введите имя клиента");
        String clientName = in.next();
        System.out.println("Введите фамилию клиента");
        String clientSurname = in.next();
        System.out.println("Введите сумму пополнения баланса");
        double money = in.nextDouble();
        Client client = centralBank.FindClient(bankName,clientName,clientSurname);
        Bank bank = centralBank.FindBank(bankName);
        IBankAccount bankAccount = centralBank.FindAccounts(bank,client).stream().filter(x -> x.getClient() == client).findFirst().orElse(null);
        centralBank.UpToBalance(bank,bankAccount,money);

    }
    public void withdrawBalanceOfClient() throws DateException, VerificationException, MinAmountOfMoneyException {
        System.out.println("Введите имя банка");
        Scanner in = new Scanner(System.in);
        String bankName = in.next();
        System.out.println("Введите имя клиента");
        String clientName = in.next();
        System.out.println("Введите фамилию клиента");
        String clientSurname = in.next();
        System.out.println("Введите сумму снятия с баланса");
        double money = in.nextDouble();
        Client client = centralBank.FindClient(bankName,clientName,clientSurname);
        Bank bank = centralBank.FindBank(bankName);
        IBankAccount bankAccount = centralBank.FindAccounts(bank,client).stream().filter(x -> x.getClient() == client).findFirst().orElse(null);
        centralBank.WithDrawMoney(bank,bankAccount,money);

    }
    public void cancelTransactions() throws DateException, VerificationException, MinAmountOfMoneyException {
        System.out.println("Введите номер транзакции");
        Scanner in = new Scanner(System.in);
        int id = in.nextInt();
        centralBank.CancelTransaction(id);
    }
    public void transferMoneyFromAccountToAccount() throws DateException, VerificationException, MinAmountOfMoneyException {
        System.out.println("Введите имя банка откуда снять деньги");
        Scanner in = new Scanner(System.in);
        String bankName1 = in.next();
        System.out.println("Введите имя банка куда перевести");
        String bankName2 = in.next();
        System.out.println("Введите имя клиента");
        String clientName1 = in.next();
        System.out.println("Введите фамилию клиента");
        String clientSurname1 = in.next();
        System.out.println("Введите имя клиента");
        String clientName2 = in.next();
        System.out.println("Введите фамилию клиента");
        String clientSurname2 = in.next();
        Bank bank1 = centralBank.FindBank(bankName1);
        Bank bank2 = centralBank.FindBank(bankName2);
        Client client1 = centralBank.FindClient(bankName1,clientName1,clientSurname1);
        Client client2 = centralBank.FindClient(bankName2,clientName2,clientSurname2);
        IBankAccount bankAccount1 = bank1.GetAccounts().stream().filter(x -> x.getClient() == client1).findFirst().orElse(null);
        IBankAccount bankAccount2 = bank2.GetAccounts().stream().filter(x -> x.getClient() == client2).findFirst().orElse(null);
        System.out.println("Введите сумму перевода");
        int money = in.nextInt();
        centralBank.MoneyTransfer(bank1,bank2,bankAccount1,bankAccount2,money);
    }
    public void execute() throws LimitOfMoneyException, CreditInterestException, InterestOnBalanceException, MinAmountOfMoneyException, PassportNumberException, NegativeAmountOfMoneyException, DateException, VerificationException, IOException {
        while (true) {
            printListOfOperations();
            int id = getOutNumberOfTransaction();
            switch (id) {
                case (1):
                    addBankToSystem();
                    break;
                case (2):
                    addClientToBank();
                    break;
                case (3):
                    addDebitAccountToClient();
                    break;
                case (4):
                    addDepositAccountToClient();
                    break;
                case (5):
                    addCreditAccountToClient();
                    break;
                case (6):
                    changeInterestOfBank();
                    break;
                case (7):
                    changeCreditInterestfBank();
                    break;
                case (8):
                    changeLimitOfTransfer();
                    break;
                case (9):
                    subscribeClient();
                    break;
                case (10):
                    unsubscribeClient();
                    break;
                case (11):
                    upToBalanceToClient();
                    break;
                case (12):
                    withdrawBalanceOfClient();
                    break;
                case (13):
                    cancelTransactions();
                    break;
                case (14):
                    transferMoneyFromAccountToAccount();
                    break;
                case (15):
                    System.exit(0);
                    break;
            }
        }

    }

}
