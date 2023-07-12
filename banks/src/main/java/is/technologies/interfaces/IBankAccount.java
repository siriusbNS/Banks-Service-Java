package is.technologies.interfaces;

import is.technologies.models.Client;
import is.technologies.tools.DateException;
import is.technologies.tools.LimitOfMoneyException;
import is.technologies.tools.MinAmountOfMoneyException;
import is.technologies.tools.VerificationException;

import java.math.BigDecimal;

public interface IBankAccount {
    void WithdrawMoney(double money) throws MinAmountOfMoneyException, VerificationException, DateException;
    void TopUpBalance(double money) throws MinAmountOfMoneyException;
    void ChangeLimitOfMoneyTransaction(double newLimit) throws LimitOfMoneyException;
    Client getClient();
}
