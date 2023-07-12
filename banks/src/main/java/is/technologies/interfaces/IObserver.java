package is.technologies.interfaces;

import is.technologies.tools.InterestOnBalanceException;
import is.technologies.tools.MinAmountOfMoneyException;

public interface IObserver {
    void Update(Object obj) throws InterestOnBalanceException, MinAmountOfMoneyException;

}
