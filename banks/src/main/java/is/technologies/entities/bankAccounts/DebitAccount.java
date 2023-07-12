package is.technologies.entities.bankAccounts;

import is.technologies.interfaces.IBankAccount;
import is.technologies.models.Client;
import is.technologies.tools.*;

import java.time.LocalDate;

public class DebitAccount implements IBankAccount
        {
public DebitAccount(Client client, double amountOfMoney, double interestOnBalace, double limitOfMoneyTransaction) throws NegativeAmountOfMoneyException, InterestOnBalanceException, LimitOfMoneyException {
        if(client == null)
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

        if (limitOfMoneyTransaction <= 0)
        {
        throw new LimitOfMoneyException("Limit is less than 0");
        }

        Client = client;
        AmountOfMoney = amountOfMoney;
        InterestOnBalance = interestOnBalace;
        DateTimeOfLastUpdate = LocalDate.now();
        BufferDateTime = LocalDate.now();
        LimitOfMoneyTransaction = limitOfMoneyTransaction;
        }

private double LimitOfMoneyTransaction;
private double InterestOnBalance;
private Client Client;
private double AmountOfMoney;
private LocalDate DateTimeOfLastUpdate;
private LocalDate BufferDateTime;
private double AmountOfAccuralFromBalace = 0;

public boolean GetVerification()
        {
        return Client.isVerificationPassed();
        }

public void WithdrawMoney(double money) throws MinAmountOfMoneyException {
        if(money == 0)
        {
                throw new NullPointerException();
        }
        if (Client.isVerificationPassed() == false)
        {
        if (money > LimitOfMoneyTransaction)
        throw new MinAmountOfMoneyException("Thats more than limit");
        }

        if (money <= 0)
        {
        throw new MinAmountOfMoneyException("Money is less than 0");
        }

        if (money > AmountOfMoney)
        {
        throw new MinAmountOfMoneyException("Not enough money");
        }

        AmountOfMoney -= money;
        }

public void TopUpBalance(double money) throws MinAmountOfMoneyException {
        if(money == 0)
        {
                throw new NullPointerException();
        }
        if (money <= 0)
        {
        throw new MinAmountOfMoneyException("Money is less than 0");
        }

        AmountOfMoney += money;
        }

public void ChangeInterest(double newInterest) throws InterestOnBalanceException {
        if(newInterest == 0)
        {
                throw new NullPointerException();
        }
        if (newInterest <= 0)
        {
        throw new InterestOnBalanceException("Вы нас разорите...");
        }

        InterestOnBalance = newInterest;
        }

public void ChangeLimitOfMoneyTransaction(double newLimit) throws LimitOfMoneyException {
        if (newLimit == 0)
                throw new NullPointerException();
        if (newLimit <= 0)
        {
        throw new LimitOfMoneyException("Limit cannot be less than 0");
        }

        LimitOfMoneyTransaction = newLimit;
        }
public Client getClient() {
                        return Client;
                }
public void CalculateInterestInDate(LocalDate dateTime) throws MinAmountOfMoneyException {
        if (dateTime == null)
        {
                throw new NullPointerException();
        }
        if (AmountOfMoney == 0)
        {
        throw new MinAmountOfMoneyException("Not enough money");
        }

        double interestOfDay = InterestOnBalance / 365;
        int newDateTimeSpan = (dateTime.getDayOfYear() + dateTime.getYear()*365) - (BufferDateTime.getDayOfYear() + BufferDateTime.getYear() * 365);
        for (int i = 1; i <= newDateTimeSpan; i++)
        {
        AmountOfAccuralFromBalace += interestOfDay * AmountOfMoney;
        if (i % 30 == 0)
        {
        CaclculateMounthInterest();
        }
        }

        BufferDateTime = dateTime;
        }

public void TransferToAccount(IBankAccount account, double money) throws MinAmountOfMoneyException {
        if(account == null || money == 0)
        {
                throw new NullPointerException();
        }
        this.WithdrawMoney(money);
        account.TopUpBalance(money);
        }

private void CaclculateMounthInterest() throws MinAmountOfMoneyException {
        if (AmountOfAccuralFromBalace < 0)
        {
        throw new MinAmountOfMoneyException("Not enough money");
        }

        AmountOfMoney += AmountOfAccuralFromBalace;
        AmountOfAccuralFromBalace = 0;
        DateTimeOfLastUpdate = DateTimeOfLastUpdate.plusMonths(1);
        }
        }
