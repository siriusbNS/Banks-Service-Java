package is.technologies.entities.bankAccounts;

import is.technologies.interfaces.IBankAccount;
import is.technologies.tools.*;
import is.technologies.models.Client;

import java.math.BigDecimal;

public class CreditAccount implements IBankAccount {
    public CreditAccount(Client client, double amountOfMoney, double creditInterest, double minOfNegativeAmountOfMoney, double limitOfMoneyTransaction) throws NegativeAmountOfMoneyException, CreditInterestException, MinAmountOfMoneyException, LimitOfMoneyException {
        if (client == null)
            throw new NullPointerException();
        if (amountOfMoney <= 0) {
            throw new NegativeAmountOfMoneyException("Negative amount of money");
        }

        if (creditInterest <= 0) {
            throw new CreditInterestException("Вы нас разорите...");
        }

        if (minOfNegativeAmountOfMoney >= 0) {
            throw new MinAmountOfMoneyException("Min amount of money is more than 0");
        }

        if (limitOfMoneyTransaction <= 0) {
            throw new LimitOfMoneyException("Limit of money is less than 0");
        }

        Client = client;
        AmountOfMoney = amountOfMoney;
        CredintInterest = creditInterest;
        MinOfNegativeAmountOfMoney = minOfNegativeAmountOfMoney;
        LimitOfMoneyTransaction = limitOfMoneyTransaction;
    }

    private double LimitOfMoneyTransaction;
    private double MinOfNegativeAmountOfMoney;
    private double CredintInterest;
    private Client Client;
    private double AmountOfMoney;

    public boolean GetVerification() {
        return Client.isVerificationPassed();
    }

    public Client getClient() {
        return Client;
    }

    public void WithdrawMoney(double money) throws MinAmountOfMoneyException, VerificationException {
        if (money == 0) {
            throw new NullPointerException();
        }
        if (AmountOfMoney - money < MinOfNegativeAmountOfMoney) {
            throw new MinAmountOfMoneyException("Not enough money");
        }

        if (money <= 0) {
            throw new MinAmountOfMoneyException("Money is less than 0");
        }

        if (!GetVerification()) {
            if (money > LimitOfMoneyTransaction) {
                throw new VerificationException("You cant do this without verification");
            }
        }

        if (AmountOfMoney <= 0) {
            AmountOfMoney -= money * CredintInterest;
            return;
        }

        AmountOfMoney -= money;
    }

    public void ChangeCreditInterest(double newInterest) throws CreditInterestException {
        if (newInterest == 0) {
            throw new NullPointerException();
        }
        if (newInterest <= 0) {
            throw new CreditInterestException("Вы нас разорите...");
        }

        CredintInterest = newInterest;
    }

    public void ChangeLimitOfMoneyTransaction(double newLimit) throws LimitOfMoneyException {
        if (newLimit == 0) {
            throw new NullPointerException();
        }
        if (newLimit <= 0) {
            throw new LimitOfMoneyException("Limit Less than 0");
        }

        LimitOfMoneyTransaction = newLimit;
    }

    public void TopUpBalance(double money) {
        if (money == 0) {
            throw new NullPointerException();
        }
        if (AmountOfMoney < 0) {
            AmountOfMoney += money - (money * CredintInterest);
            return;
        }

        AmountOfMoney += money;
    }

    public void TransferToAccount(IBankAccount account, double money) throws VerificationException, MinAmountOfMoneyException {
        if (money == 0 || account == null) {
            throw new NullPointerException();
        }
        this.WithdrawMoney(money);
        account.TopUpBalance(money);
    }
}
