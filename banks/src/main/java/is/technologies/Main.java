package is.technologies;
import is.technologies.banksConsole.BanksConsole;
import is.technologies.banksConsole.IBanksConsole;
import is.technologies.tools.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, LimitOfMoneyException, PassportNumberException, CreditInterestException, DateException, VerificationException, InterestOnBalanceException, NegativeAmountOfMoneyException, MinAmountOfMoneyException {
        IBanksConsole banksConsole = new BanksConsole();
        banksConsole.execute();

    }
}