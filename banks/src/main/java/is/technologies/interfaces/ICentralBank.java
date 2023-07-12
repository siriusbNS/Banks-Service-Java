package is.technologies.interfaces;

import is.technologies.entities.Bank;
import is.technologies.entities.commands.TransferMoneyCommand;
import is.technologies.entities.commands.UpToBalanceCommand;
import is.technologies.entities.commands.WithDrawCommand;
import is.technologies.models.Client;
import is.technologies.tools.*;

import java.util.ArrayList;
import java.util.List;

public interface ICentralBank {
    Bank AddBank(double creditInterest, double interestOnBalance, double minOfNegativeAmountOfMoney, double limitOfMoneyTransaction, String nameOfBank) throws LimitOfMoneyException, CreditInterestException, InterestOnBalanceException, MinAmountOfMoneyException;

    TransferMoneyCommand MoneyTransfer(Bank bankFrom, Bank bankTo, IBankAccount bankAccountFrom, IBankAccount bankAccountTo, double money) throws DateException, VerificationException, MinAmountOfMoneyException;
    void TimeMangaerPlusDays(int value) throws InterestOnBalanceException, MinAmountOfMoneyException;
    void TimeMangaerPlusMonths(int value) throws InterestOnBalanceException, MinAmountOfMoneyException;
    List<Bank> GetBanks();
    void TimeMangaerPlusYears(int value) throws InterestOnBalanceException, MinAmountOfMoneyException;
    Client AddClientBase(Bank bank, String name, String surname) throws PassportNumberException;
    void AddPasspotToClient(Bank bank, int passport, Client client) throws PassportNumberException;
    void AddAdressToClient(Bank bank, String adress, Client client);
    Client AddClientFullVersion(Bank bank, String name, String surname, String adress, int passport) throws PassportNumberException;
    IBankAccount AddCreditAccount(Bank bank, Client client, double firstContribution) throws LimitOfMoneyException, CreditInterestException, NegativeAmountOfMoneyException, MinAmountOfMoneyException;
    void ChangeCredintInterest(Bank bank, double newCredintInterest) throws CreditInterestException;
    void ChangeInterestOnBalance(Bank bank, double newInterestOnBalance) throws InterestOnBalanceException;
    void ChangeLimitOfMoneyTransaction(Bank bank, double newLimit);
    IBankAccount AddDebitAccount(Bank bank, Client client, double firstContribution) throws LimitOfMoneyException, NegativeAmountOfMoneyException, InterestOnBalanceException;
    IBankAccount AddDepositeAccount(Bank bank, Client client, double firstContribution, int accountTerm) throws LimitOfMoneyException, NegativeAmountOfMoneyException, InterestOnBalanceException;
    void Subscribe(Bank bank, IObserver observer);
    void Unsubscribe(Bank bank, IObserver observer);
    UpToBalanceCommand UpToBalance(Bank bank, IBankAccount bankAccount, double money) throws DateException, VerificationException, MinAmountOfMoneyException;
    WithDrawCommand WithDrawMoney(Bank bank, IBankAccount bankAccount, double money) throws DateException, VerificationException, MinAmountOfMoneyException;
    void CancelTransaction(int id) throws DateException, VerificationException, MinAmountOfMoneyException;
    void Update(Object obj) throws InterestOnBalanceException, MinAmountOfMoneyException;
    ArrayList<String> GetNamesOfBanks();
    Bank FindBank(String name);
    ArrayList<String> GetClients(Bank bank);
    ArrayList<String> GetNamesClients(Bank bank);
    ArrayList<String> GetSurnamesClients(Bank bank);
    Client FindClient(String bankName, String name, String surname);
    Client ClientFind(String bankName, String fullName);
    List<IBankAccount> FindAccounts(Bank bank, Client client);
}
