package is.technologies.entities.bankAccounts;

import is.technologies.interfaces.IBankAccount;
import is.technologies.models.Client;
import is.technologies.tools.*;

import java.time.LocalDate;

public class DepositAccount implements IBankAccount
        {
public DepositAccount(Client client, double amountOfMoney, double interestOnBalace, int accountTerm, double limitOfMoneyTransaction) throws NegativeAmountOfMoneyException, InterestOnBalanceException, LimitOfMoneyException {
        if(client == null || accountTerm == 0)
        {
            throw new NullPointerException();
        }
        if (amountOfMoney <= 0)
        {
        throw new NegativeAmountOfMoneyException("Negative amount of money");
        }

        if (interestOnBalace <= 0)
        {
        throw new InterestOnBalanceException("Вы нас разорите...");
        }

        if (accountTerm <= 0)
        {
        throw new RuntimeException();
        }

        if (limitOfMoneyTransaction <= 0)
        {
        throw new LimitOfMoneyException("Limit is less than 0");
        }

        AccountTerm = accountTerm;
        DateTimeAccountTerm = LocalDate.now().plusDays(accountTerm);
        DateTimeOfCreateAccount = LocalDate.now();
        BufferDateTime = LocalDate.now();
        Client = client;
        AmountOfMoney = amountOfMoney;
        InterestOnBalance = MakeNewInterest(interestOnBalace, amountOfMoney);
        LimitOfMoneyTransaction = limitOfMoneyTransaction;
        }

private double LimitOfMoneyTransaction;
private int AccountTerm;
private LocalDate DateTimeAccountTerm;
private double InterestOnBalance;

private Client Client;
private double AmountOfMoney;
private LocalDate DateTimeOfCreateAccount;
private LocalDate BufferDateTime;
private double DepositAmountOfAccuralFromBalace = 0;
public Client getClient() {
                        return Client;
                }
public void ChangeInterest(double newInterest) throws InterestOnBalanceException {
        if(newInterest == 0)
            throw new NullPointerException();
        if (newInterest <= 0)
        {
        throw new InterestOnBalanceException("Вы нас разорите...");
        }

        InterestOnBalance = newInterest;
        }

public void ChangeLimitOfMoneyTransaction(double newLimit) throws LimitOfMoneyException {
        if(newLimit == 0)
            throw new NullPointerException();
        if (newLimit <= 0)
        {
        throw new LimitOfMoneyException("Limit cannot be less than 0");
        }

        LimitOfMoneyTransaction = newLimit;
        }

public void WithdrawMoney(double money) throws DateException, MinAmountOfMoneyException, VerificationException {
        if ((DateTimeOfCreateAccount.getYear()*365 + DateTimeOfCreateAccount.getDayOfYear()) < (DateTimeAccountTerm.getYear()*365 + DateTimeAccountTerm.getDayOfYear()))
        {
        throw new DateException("Its not time to this");
        }

        if (money <= 0)
        {
        throw new MinAmountOfMoneyException("Money is less than 0");
        }

        if (GetVerification() == false)
        {
        if (money > LimitOfMoneyTransaction)
        {
        throw new VerificationException("You cannot do this without verification");
        }
        }

        if(money == 0)
        {
            throw new NullPointerException();
        }
        if (money > AmountOfMoney)
        {
        throw new MinAmountOfMoneyException("Not enough money");
        }

        AmountOfMoney -= money;
        }

public void TopUpBalance(double money) throws MinAmountOfMoneyException {
        if(money == 0)
            throw new NullPointerException();
        if (money <= 0)
        {
        throw new MinAmountOfMoneyException("Money is less than 0");
        }

        AmountOfMoney += money;
        }

public boolean GetVerification()
        {
        return Client.isVerificationPassed();
        }

public void CalculateInterestInDate(LocalDate dateTime) throws InterestOnBalanceException, MinAmountOfMoneyException {
        if (DateTimeOfCreateAccount.getDayOfYear() >= DateTimeAccountTerm.getDayOfYear())
        {
        return;
        }

        if (AmountOfMoney == 0)
        {
        throw new MinAmountOfMoneyException("Not enough money");
        }

        double interestOfDay = InterestOnBalance / 365;
        int newDateTimeSpan = (dateTime.getDayOfYear() + dateTime.getYear()*365) - (BufferDateTime.getDayOfYear() + BufferDateTime.getYear() * 365);
        for (int i = 0; i < newDateTimeSpan; i++)
        {
        DepositAmountOfAccuralFromBalace += interestOfDay * AmountOfMoney;
        BufferDateTime.plusDays(1);
        if (dateTime.getMonthValue() == BufferDateTime.getMonthValue() && dateTime.getDayOfMonth() == BufferDateTime.getDayOfMonth())
        {
        CaclculateAllInterest(dateTime);
        }
        }

        BufferDateTime = dateTime;
        }

public void TransferToAccount(IBankAccount account, double money) throws DateException, VerificationException, MinAmountOfMoneyException {
        this.WithdrawMoney(money);
        account.TopUpBalance(money);
        }

private void CaclculateAllInterest(LocalDate dateTime) throws InterestOnBalanceException {
        if (DepositAmountOfAccuralFromBalace <= 0)
        {
        throw new InterestOnBalanceException("Not enough money from interest");
        }

        AmountOfMoney += DepositAmountOfAccuralFromBalace;
        DepositAmountOfAccuralFromBalace = 0;
        DateTimeOfCreateAccount = dateTime;
        }

private double MakeNewInterest(double firstPercentage, double amountOfMoney)
        {
        if (amountOfMoney <= 50000)
        {
        return firstPercentage;
        }

        if (amountOfMoney > 50000 && AmountOfMoney < 100000)
        {
        return firstPercentage + 0.5;
        }

        if (amountOfMoney >= 100000)
        {
        return firstPercentage + 1;
        }

        throw new NullPointerException();
        }

private void CaclculateMounthInterest()
        {
        if (DepositAmountOfAccuralFromBalace <= 0)
        {
        throw new NullPointerException();
        }

        AmountOfMoney += DepositAmountOfAccuralFromBalace;
        DepositAmountOfAccuralFromBalace = 0;
        }
        }
