package is.technologies.entities.commands;

import is.technologies.interfaces.IBankAccount;
import is.technologies.interfaces.ICommand;
import is.technologies.tools.DateException;
import is.technologies.tools.MinAmountOfMoneyException;
import is.technologies.tools.VerificationException;

public class TransferMoneyCommand implements ICommand
        {
public TransferMoneyCommand(IBankAccount bankAccountFrom, IBankAccount bankAccountTo, double money, int id)
        {
        if (bankAccountFrom == null || bankAccountTo == null)
            throw new NullPointerException();
        BankAccountFrom = bankAccountFrom;
        BankAccountTo = bankAccountTo;
        Money = money;
        TransactionChecker = false;
        Id = id;
        }

private IBankAccount BankAccountFrom;
private IBankAccount BankAccountTo;
private boolean TransactionChecker;
private int Id;
private double Money;
public void Execute() throws DateException, VerificationException, MinAmountOfMoneyException {
        BankAccountFrom.WithdrawMoney(Money);
        BankAccountTo.TopUpBalance(Money);
        TransactionChecker = true;
        }

public void Undo() throws DateException, VerificationException, MinAmountOfMoneyException {
        if (!TransactionChecker)
        {
        throw new NullPointerException();
        }

        BankAccountTo.WithdrawMoney(Money);
        BankAccountFrom.TopUpBalance(Money);
        }

public int GetId()
        {
        return Id;
        }
        }