package is.technologies.entities.commands;

import is.technologies.interfaces.IBankAccount;
import is.technologies.interfaces.ICommand;
import is.technologies.tools.DateException;
import is.technologies.tools.MinAmountOfMoneyException;
import is.technologies.tools.VerificationException;

public class WithDrawCommand implements ICommand
        {
public WithDrawCommand(IBankAccount bankAccount, double money, int id)
        {
        if(bankAccount == null)
            throw new NullPointerException();
        BankAcc = bankAccount;
        Money = money;
        TransactionChecker = false;
        Id = id;
        }

private boolean TransactionChecker;
private int Id;
private IBankAccount BankAcc;
private double Money;
public void Execute() throws DateException, VerificationException, MinAmountOfMoneyException {
        TransactionChecker = false;
        BankAcc.WithdrawMoney(Money);
        }

public void Undo() throws MinAmountOfMoneyException {
        if (TransactionChecker == false)
        throw new NullPointerException();
        BankAcc.TopUpBalance(Money);
        }

public int GetId()
        {
        return Id;
        }
        }