package is.technologies.banksConsole;

import is.technologies.tools.*;

import java.io.IOException;

public interface IBanksConsole {
    void printListOfOperations();
    int getOutNumberOfTransaction() throws IOException;
    void execute() throws LimitOfMoneyException, CreditInterestException, InterestOnBalanceException, MinAmountOfMoneyException, PassportNumberException, NegativeAmountOfMoneyException, DateException, VerificationException, IOException;
    void addBankToSystem() throws LimitOfMoneyException, CreditInterestException, InterestOnBalanceException, MinAmountOfMoneyException;
}
