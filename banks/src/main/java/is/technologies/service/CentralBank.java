package is.technologies.service;

import is.technologies.entities.bankAccounts.DebitAccount;
import is.technologies.entities.bankAccounts.DepositAccount;
import is.technologies.entities.commands.TransferMoneyCommand;
import is.technologies.entities.commands.UpToBalanceCommand;
import is.technologies.entities.commands.WithDrawCommand;
import is.technologies.interfaces.IBankAccount;
import is.technologies.interfaces.ICentralBank;
import is.technologies.interfaces.ICommand;
import is.technologies.interfaces.IObserver;
import is.technologies.entities.Bank;
import is.technologies.entities.TimeManager;
import is.technologies.models.Client;
import is.technologies.tools.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class CentralBank implements ICentralBank, IObserver
{
    public CentralBank()
    {
        ListOfBanks = new ArrayList<Bank>();
        Transactions = new ArrayList<ICommand>();
        TimeManager = new TimeManager();
        TimeManager.AddObserver(this);
    }

private int Id = 1;
private ArrayList<Bank> ListOfBanks;
private TimeManager TimeManager;
private ArrayList<ICommand> Transactions;
public List<Bank> GetBanks()
{
        return  Collections.unmodifiableList(ListOfBanks);
}

public void TimeMangaerPlusDays(int value) throws InterestOnBalanceException, MinAmountOfMoneyException {
        TimeManager.PlusDays(value);
        }

public void TimeMangaerPlusMonths(int value) throws InterestOnBalanceException, MinAmountOfMoneyException {
        TimeManager.PlusMonths(value);
        }

public void TimeMangaerPlusYears(int value) throws InterestOnBalanceException, MinAmountOfMoneyException {
        TimeManager.PlusYears(value);
        }

public Bank AddBank(double creditInterest, double interestOnBalance, double minOfNegativeAmountOfMoney, double limitOfMoneyTransaction, String nameOfBank) throws LimitOfMoneyException, CreditInterestException, InterestOnBalanceException, MinAmountOfMoneyException {
        if(nameOfBank == null)
                throw new NullPointerException();
        Bank bank = new Bank(creditInterest, interestOnBalance, minOfNegativeAmountOfMoney, limitOfMoneyTransaction, nameOfBank);
        ListOfBanks.add(bank);
        return bank;
        }

public TransferMoneyCommand MoneyTransfer(Bank bankFrom, Bank bankTo, IBankAccount bankAccountFrom, IBankAccount bankAccountTo, double money) throws DateException, VerificationException, MinAmountOfMoneyException {
        if(bankFrom == null || bankTo == null || bankAccountFrom == null || bankAccountTo == null)
                throw new NullPointerException();
        var currentBankFrom = ListOfBanks.stream().filter(x -> x.getNameOfBank() == bankFrom.getNameOfBank()).findFirst().orElse(null);
        var currentBankTo = ListOfBanks.stream().filter(x -> x.getNameOfBank() == bankTo.getNameOfBank()).findFirst().orElse(null);
        if (currentBankFrom == null || currentBankTo == null)
                throw new NullPointerException();
        IBankAccount currentAccFrom = currentBankFrom
        .GetAccounts().stream().filter(x -> x.getClient() == bankAccountFrom.getClient()).findFirst().orElse(null);
        IBankAccount currentAccTo = currentBankTo
        .GetAccounts().stream().filter(x-> x.getClient() == bankAccountTo.getClient()).findFirst().orElse(null);
        if(currentAccFrom == null || currentAccTo == null)
                throw new NullPointerException();

        ICommand command = new TransferMoneyCommand(bankAccountFrom, bankAccountTo, money, Id);
        Id++;
        command.Execute();
        Transactions.add(command);
        return (TransferMoneyCommand)command;
        }

public Client AddClientBase(Bank bank, String name, String surname) throws PassportNumberException {
        if(bank == null)
                throw new NullPointerException();
        var currentBank = ListOfBanks.stream().filter(x -> x == bank).findFirst().orElse(null);
        if(currentBank == null)
                throw new NullPointerException();

        return currentBank.AddClientBase(name, surname);
        }

public void AddPasspotToClient(Bank bank, int passport, Client client) throws PassportNumberException {
        if(bank == null)
                throw new NullPointerException();
        var currentBank = ListOfBanks.stream().filter(x-> x == bank).findFirst().orElse(null);
        if (currentBank == null)
                throw new NullPointerException();

        currentBank.AddPasspotToClient(passport, client);
        }

public void AddAdressToClient(Bank bank, String adress, Client client)
        {
        if(bank == null)
                throw new NullPointerException();
        var currentBank = ListOfBanks.stream().filter(x -> x == bank).findFirst().orElse(null);
        if(currentBank == null)
                throw new NullPointerException();

        currentBank.AddAdressToClient(adress, client);
        }

public Client AddClientFullVersion(Bank bank, String name, String surname, String adress, int passport) throws PassportNumberException {
        if(bank == null || name == null || surname == null || adress == null)
                throw new NullPointerException();
        var currentBank = ListOfBanks.stream().filter(x -> x== bank).findFirst().orElse(null);
        if (currentBank == null)
        {
        throw new NullPointerException();
        }

        return currentBank.AddClientFullVersion(name, surname, adress, passport);
        }

public IBankAccount AddCreditAccount(Bank bank, Client client, double firstContribution) throws LimitOfMoneyException, CreditInterestException, NegativeAmountOfMoneyException, MinAmountOfMoneyException {
        if (bank == null || client == null)
                throw new NullPointerException();
        var currentBank = ListOfBanks.stream().filter(x -> x == bank).findFirst().orElse(null);
        if (currentBank == null)
        {
        throw new NullPointerException();
        }

        return currentBank.AddCreditAccount(client, firstContribution);
        }

public void ChangeCredintInterest(Bank bank, double newCredintInterest) throws CreditInterestException {
        if (bank == null)
                throw new NullPointerException();
        var currentBank = ListOfBanks.stream().filter(x -> x == bank).findFirst().orElse(null);
        if (currentBank == null)
        {
        throw new NullPointerException();
        }

        currentBank.ChangeCredintInterest(newCredintInterest);
        }

public void ChangeInterestOnBalance(Bank bank, double newInterestOnBalance) throws InterestOnBalanceException {
        if (bank == null)
                throw new NullPointerException();
        var currentBank = ListOfBanks.stream().filter(x -> x == bank).findFirst().orElse(null);
        if (currentBank == null)
        {
        throw new NullPointerException();
        }

        currentBank.ChangeInterestOnBalance(newInterestOnBalance);

        }

public void ChangeLimitOfMoneyTransaction(Bank bank, double newLimit)
        {
        if (bank == null)
                throw new NullPointerException();
        var currentBank = ListOfBanks.stream().filter(x -> x == bank).findFirst().orElse(null);
        if (currentBank == null)
        {
        throw new NullPointerException();
        }

        currentBank.ChangeLimitOfMoneyTransaction(newLimit);
        }

public IBankAccount AddDebitAccount(Bank bank, Client client, double firstContribution) throws LimitOfMoneyException, NegativeAmountOfMoneyException, InterestOnBalanceException {
        if (bank == null || client == null)
                throw new NullPointerException();
        var currentBank = ListOfBanks.stream().filter(x -> x == bank).findFirst().orElse(null);
        if (currentBank == null)
        {
        throw new NullPointerException();
        }

        return currentBank.AddDebitAccount(client, firstContribution);
        }
public IBankAccount AddDepositeAccount(Bank bank, Client client, double firstContribution, int accountTerm) throws LimitOfMoneyException, NegativeAmountOfMoneyException, InterestOnBalanceException
        {
        if (bank == null || client == null)
                throw new NullPointerException();
        var currentBank = ListOfBanks.stream().filter(x -> x == bank).findFirst().orElse(null);
        if (currentBank == null)
        {
                throw new NullPointerException();
        }

        return currentBank.AddDepositeAccount(client, firstContribution, accountTerm);
        }

public void Subscribe(Bank bank, IObserver observer)
        {
        if (bank == null || observer == null)
                throw new NullPointerException();
        var currentBank = ListOfBanks.stream().filter(x -> x == bank).findFirst().orElse(null);
        if (currentBank == null)
        {
        throw new NullPointerException();
        }

        currentBank.AddObserver(observer);
        }

public void Unsubscribe(Bank bank, IObserver observer)
        {
        if (bank == null || observer == null)
                throw new NullPointerException();
        var currentBank = ListOfBanks.stream().filter(x -> x == bank).findFirst().orElse(null);
        if (currentBank == null)
        {
        throw new NullPointerException();
        }

        currentBank.RemoveObserever(observer);
        }

public UpToBalanceCommand UpToBalance(Bank bank, IBankAccount bankAccount, double money) throws DateException, VerificationException, MinAmountOfMoneyException {
        if (bank == null || bankAccount == null)
                throw new NullPointerException();
        var currentBank = ListOfBanks.stream().filter(x -> x == bank).findFirst().orElse(null);
        if (currentBank == null)
        {
        throw new NullPointerException();
        }
        var currentAcc = currentBank.GetAccounts().stream().filter(x -> x == bankAccount).findFirst().orElse(null);
        if (currentAcc == null)
        {
        throw new NullPointerException();
        }

        UpToBalanceCommand command = new UpToBalanceCommand(currentAcc, money, Id);
        Id++;
        command.Execute();
        Transactions.add(command);
        return command;
        }

public WithDrawCommand WithDrawMoney(Bank bank, IBankAccount bankAccount, double money) throws DateException, VerificationException, MinAmountOfMoneyException {
        if (bank == null || bankAccount == null)
                throw new NullPointerException();
        var currentBank = ListOfBanks.stream().filter(x -> x == bank).findFirst().orElse(null);
        if (currentBank == null)
        {
        throw new NullPointerException();
        }
        var currentAcc = currentBank.GetAccounts().stream().filter(x -> x == bankAccount).findFirst().orElse(null);
        if (currentAcc == null)
        {
        throw new NullPointerException();
        }

        WithDrawCommand command = new WithDrawCommand(currentAcc, money, Id);
        Id++;
        command.Execute();
        Transactions.add(command);
        return command;
        }

public void CancelTransaction(int id) throws DateException, VerificationException, MinAmountOfMoneyException {
        if (id == 0)
                throw new NullPointerException();
        var currentCommand = Transactions.stream().filter(x -> x.GetId() == id).findFirst().orElse(null);
        if (currentCommand == null)
        {
        throw new NullPointerException();
        }
        currentCommand.Undo();
        Transactions.remove(currentCommand);
        }

public void Update(Object obj) throws InterestOnBalanceException, MinAmountOfMoneyException {
        if (obj == null)
                throw new NullPointerException();
        LocalDate dateTime = (LocalDate) obj;
        for (var i : ListOfBanks)
        {
        for (var bankAccount : i.GetAccounts())
        {
        if (bankAccount instanceof DepositAccount)
        {
        var j = (DepositAccount)bankAccount;
        j.CalculateInterestInDate(dateTime);
        }
        }

        for (var bankAccount : i.GetAccounts())
        {
        if (bankAccount instanceof DebitAccount)
        {
        var a = (DebitAccount)bankAccount;
        a.CalculateInterestInDate(dateTime);
        }
        }
        }

        System.out.println("Время перемотано на " + dateTime.toString() + " .Проценты начислены.");
        }

public ArrayList<String> GetNamesOfBanks()
        {
        ArrayList<String> list = new ArrayList<String>();
        if (ListOfBanks.size() == 0)
        {
        throw new NullPointerException();
        }

        ListOfBanks.forEach(x -> list.add(x.getNameOfBank()));
        return list;
        }

public Bank FindBank(String name)
        {
        if (name == null)
                throw new NullPointerException();
        var currentBank = ListOfBanks.stream().filter(x -> x.getNameOfBank().equals(name)).findFirst().orElse(null);
        if (currentBank == null)
                throw new NullPointerException();
        return currentBank;
        }

public ArrayList<String> GetClients(Bank bank)
        {
        if (bank == null)
                throw new NullPointerException();
        var currentBank = ListOfBanks.stream().filter(x -> x.getNameOfBank().equals(bank.getNameOfBank())).findFirst().orElse(null);
        if (currentBank == null)
                throw new NullPointerException();
        return currentBank.GetFullNamesOfClients();
        }

public Client FindClient(String bankName, String name, String surname)
        {
        if (bankName == null || name == null || surname == null)
                throw new NullPointerException();
        var currentBank = ListOfBanks.stream().filter(x -> x.getNameOfBank().equals(bankName)).findFirst().orElse(null);
        if (currentBank == null)
                throw new NullPointerException();
        return currentBank.FindClient(name, surname);
        }

public Client ClientFind(String bankName, String fullName)
        {
        if (bankName == null || fullName == null)
                throw new NullPointerException();
        var currentBank = ListOfBanks.stream().filter(x -> x.getNameOfBank().equals(bankName)).findFirst().orElse(null);
        if (currentBank == null)
                throw new NullPointerException();
        return currentBank.FindClient(fullName);
        }

public ArrayList<String> GetNamesClients(Bank bank)
        {
        if (bank == null)
                throw new NullPointerException();
        var currentBank = ListOfBanks.stream().filter(x -> x.getNameOfBank().equals(bank.getNameOfBank())).findFirst().orElse(null);
        if (currentBank == null)
                throw new NullPointerException();
        return currentBank.GetNameOfClients();
        }

public ArrayList<String> GetSurnamesClients(Bank bank)
        {
        if (bank == null)
                throw new NullPointerException();
        var currentBank = ListOfBanks.stream().filter(x -> x.getNameOfBank().equals(bank.getNameOfBank())).findFirst().orElse(null);
        if (currentBank == null)
                throw new NullPointerException();
        return currentBank.GetSurnamesOfClients();
        }

public ArrayList<IBankAccount> FindAccounts(Bank bank, Client client)
        {
        if (bank == null || client == null)
                throw new NullPointerException();
        var currentBank = ListOfBanks.stream().filter(x -> x.getNameOfBank().equals(bank.getNameOfBank())).findFirst().orElse(null);
        if (currentBank == null)
                throw new NullPointerException();
        var list = (ArrayList<IBankAccount>) ListOfBanks.stream().map(x -> x.GetAccounts()).flatMap(List::stream).collect(Collectors.toList()).stream().filter(x -> x.getClient() == client).toList();
        if (list.size() == 0)
        {
        throw new NullPointerException();
        }

        return list;
        }
        }
