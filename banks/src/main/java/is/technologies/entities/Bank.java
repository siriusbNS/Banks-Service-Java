package is.technologies.entities;

import is.technologies.builders.ClientBuilder;
import is.technologies.entities.bankAccounts.CreditAccount;
import is.technologies.entities.bankAccounts.DebitAccount;
import is.technologies.entities.bankAccounts.DepositAccount;
import is.technologies.interfaces.IBankAccount;
import is.technologies.interfaces.IObservable;
import is.technologies.interfaces.IObserver;
import is.technologies.models.Client;
import is.technologies.tools.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Bank implements IObservable {
    public Bank(double creditInterest, double interestOnBalance, double minOfNegativeAmountOfMoney, double limitOfMoneyTransaction, String nameOfBank) throws InterestOnBalanceException, CreditInterestException, MinAmountOfMoneyException, LimitOfMoneyException {
        if (creditInterest == 0 || interestOnBalance == 0) {
            throw new NullPointerException();
        }
        if (creditInterest <= 0) {
            throw new CreditInterestException("Вы нас разорите...");
        }

        if (interestOnBalance <= 0) {
            throw new InterestOnBalanceException("Вы их разорите...");
        }

        if (minOfNegativeAmountOfMoney >= 0) {
            throw new MinAmountOfMoneyException("Money is less than 0");
        }

        if (limitOfMoneyTransaction <= 0) {
            throw new LimitOfMoneyException("Limit is less than 0");
        }

        ListOfBankAccounts = new ArrayList<IBankAccount>();
        ListOfObservers = new ArrayList<IObserver>();
        ListOfClients = new ArrayList<Client>();
        CreditInterest = creditInterest;
        InterestOnBalance = interestOnBalance;
        MinOfNegativeAmountOfMoney = minOfNegativeAmountOfMoney;
        NameOfBank = nameOfBank;
        LimitOfMoneyTransaction = limitOfMoneyTransaction;
    }

    private final String NameOfBank;
    private double LimitOfMoneyTransaction;
    private final double MinOfNegativeAmountOfMoney;
    private double CreditInterest;
    private double InterestOnBalance;
    private ArrayList<IBankAccount> ListOfBankAccounts;
    private ArrayList<Client> ListOfClients;
    private ArrayList<IObserver> ListOfObservers;

    public String getNameOfBank() {
        return NameOfBank;
    }

    public double getInterestOnBalance() {
        return InterestOnBalance;
    }

    public double getCreditInterest() {
        return CreditInterest;
    }

    public double getLimitOfMoneyTransaction() {
        return LimitOfMoneyTransaction;
    }

    public List<IBankAccount> GetAccounts() {
    return  Collections.unmodifiableList(ListOfBankAccounts);
    }
    public List<Client> GetClients() {
    return  Collections.unmodifiableList(ListOfClients);
    }

    public Client AddClientBase(String name, String surname) throws PassportNumberException {
        if(name == null || surname == null)
        {
                throw new NullPointerException();
        }
        Client client = new ClientBuilder().AddNameAndSurname(name, surname).Build();
        ListOfClients.add(client);
        return client;
        }

public void AddPasspotToClient(int passport, Client client) throws PassportNumberException {
                if(client == null || passport == 0)
                {
                        throw new NullPointerException();
                }
       var currentClient = ListOfClients.stream().filter(x->x == client).findFirst().orElse(null);
        if(currentClient == null)
        {
            throw new NullPointerException();
        }
        currentClient.AddPassport(passport);
        }

public void AddAdressToClient(String adress, Client client)
        {
                if(client == null || adress == null)
                {
                        throw new NullPointerException();
                }
        var currentClient = ListOfClients.stream().filter(x-> x == client).findFirst().orElse(null);

        if(currentClient == null)
            throw new NullPointerException();
        currentClient.AddAdress(adress);
        }

public Client AddClientFullVersion(String name, String surname, String adress, int passport) throws PassportNumberException {
                if(name == null || surname == null || adress == null || passport == 0)
                {
                        throw new NullPointerException();
                }
        Client client = new ClientBuilder().AddNameAndSurname(name, surname).AddAdress(adress).AddPassport(passport).Build();
        ListOfClients.add(client);
        return client;
        }

public Client FindClient(String name, String surname)
        {
                if(name == null || surname == null)
                {
                        throw new NullPointerException();
                }
        var currentClient = ListOfClients.stream().filter(x -> Objects.equals(x.getName(), name) && Objects.equals(x.getSurname(), surname)).findFirst().orElse(null);
        if(currentClient == null)
            throw new NullPointerException();

        return currentClient;
        }

public IBankAccount AddCreditAccount(Client client, double firstContribution) throws LimitOfMoneyException, CreditInterestException, NegativeAmountOfMoneyException, MinAmountOfMoneyException {
                if(client == null || firstContribution == 0)
                {
                        throw new NullPointerException();
                }
        var currentClient = ListOfClients.stream().filter(x-> x == client).findFirst().orElse(null);
        if(currentClient==null)
            throw new NullPointerException();

        IBankAccount bankAccount = new CreditAccount(client, firstContribution, CreditInterest, MinOfNegativeAmountOfMoney, LimitOfMoneyTransaction);
        ListOfBankAccounts.add(bankAccount);
        return bankAccount;
        }

public IBankAccount AddDebitAccount(Client client, double firstContribution) throws LimitOfMoneyException, NegativeAmountOfMoneyException, InterestOnBalanceException {
                if(client == null || firstContribution == 0)
                {
                        throw new NullPointerException();
                }
        var currentClient = ListOfClients.stream().filter(x-> x == client).findFirst().orElse(null);
        if(currentClient == null)
            throw new NullPointerException();

        IBankAccount bankAccount = new DebitAccount(client, firstContribution, InterestOnBalance, LimitOfMoneyTransaction);
        ListOfBankAccounts.add(bankAccount);
        return bankAccount;
        }

public IBankAccount AddDepositeAccount(Client client, double firstContribution, int accountTerm) throws LimitOfMoneyException, NegativeAmountOfMoneyException, InterestOnBalanceException {
                if(client == null || firstContribution == 0 || accountTerm == 0)
                {
                        throw new NullPointerException();
                }
        var currentClient = ListOfClients.stream().filter(x->x==client).findFirst().orElse(null);
        if(currentClient == null)
        {
                throw new NullPointerException();
        }

        IBankAccount bankAccount = new DepositAccount(currentClient, firstContribution, CreditInterest, accountTerm, LimitOfMoneyTransaction);
        ListOfBankAccounts.add(bankAccount);
        return bankAccount;
        }

public void ChangeCredintInterest(double newCredintInterest) throws CreditInterestException {
    CreditInterest = newCredintInterest;
    for (IBankAccount creditAccount : ListOfBankAccounts) {
        if (creditAccount instanceof CreditAccount) {
            ((CreditAccount) creditAccount).ChangeCreditInterest(newCredintInterest);
        }


    }
    NotifyObservers();
}

public void ChangeInterestOnBalance(double newInterestOnBalance) throws InterestOnBalanceException
{
                if( newInterestOnBalance == 0)
                {
                        throw new NullPointerException();
                }
        InterestOnBalance = newInterestOnBalance;
        for (var bankAccount : ListOfBankAccounts
        .stream().filter(x -> !(x instanceof CreditAccount)).toList())
        {
            if(bankAccount instanceof DepositAccount)
            {

                ((DepositAccount) bankAccount).ChangeInterest(newInterestOnBalance);
            }
            if(bankAccount instanceof DebitAccount)
            {
               ((DebitAccount) bankAccount).ChangeInterest(newInterestOnBalance);
            }
        }
    NotifyObservers();
}




public void ChangeLimitOfMoneyTransaction(double newLimit)
        {
            if( newLimit == 0)
            {
                throw new NullPointerException();
            }
        LimitOfMoneyTransaction = newLimit;
        ListOfBankAccounts.stream().forEach(x -> {
            try {
                x.ChangeLimitOfMoneyTransaction(newLimit);
            } catch (LimitOfMoneyException e) {
                throw new RuntimeException(e);
            }
        });
        NotifyObservers();
        }

public void WithdrawMoney(IBankAccount bankAccount, double money) throws DateException, VerificationException, MinAmountOfMoneyException {
            if(bankAccount == null || money == 0)
            {
                throw new NullPointerException();
            }
        var currentAcc = ListOfBankAccounts.stream().filter(x-> x == bankAccount).findFirst().orElse(null);
            if(currentAcc == null)
            {
                throw new NullPointerException();
            }

        currentAcc.WithdrawMoney(money);
        }

public void TopUpBalance(IBankAccount bankAccount, double money) throws MinAmountOfMoneyException {
            if(bankAccount == null || money == 0)
            {
                throw new NullPointerException();
            }
        var currentAcc = ListOfBankAccounts.stream().filter(x -> x == bankAccount).findFirst().orElse(null);
            if(currentAcc == null)
            {
                throw new NullPointerException();
            }

        currentAcc.TopUpBalance(money);
        }

public void AddObserver(IObserver observer)
        {
            if(observer == null)
            {
                throw new NullPointerException();
            }
        ListOfObservers.add(observer);
        }

public void RemoveObserever(IObserver observer)
        {
            if(observer == null)
            {
                throw new NullPointerException();
            }
        ListOfObservers.remove(observer);
        }

public void NotifyObservers()
        {
        ListOfObservers.forEach(x-> {
            try {
                x.Update(this);
            } catch (InterestOnBalanceException e) {
                throw new RuntimeException(e);
            } catch (MinAmountOfMoneyException e) {
                throw new RuntimeException(e);
            }
        });
        return;
        }

public ArrayList<String> GetFullNamesOfClients()
        {
        ArrayList<String> list = new ArrayList<String>();
        ListOfClients.forEach(x -> list.add(x.getName() + " " + x.getSurname()));
        return list;
        }

public ArrayList<String> GetNameOfClients()
        {
        ArrayList<String> list = new ArrayList<String>();
        ListOfClients.forEach(x -> list.add(x.getName()));
        return list;
        }

public ArrayList<String> GetSurnamesOfClients()
        {
        ArrayList<String> list = new ArrayList<String>();
        ListOfClients.forEach(x -> list.add(x.getSurname()));
        return list;
        }

public Client FindClient(String fullName)
        {
            if(fullName == null)
            {
                throw new NullPointerException();
            }
        var currentClient = ListOfClients.stream().filter(x-> (x.getName() + " " + x.getSurname()) == fullName).findFirst().orElse(null);
            if(currentClient == null)
            {
                throw new NullPointerException();
            };

        return currentClient;
        }
}