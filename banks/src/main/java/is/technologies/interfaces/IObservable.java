package is.technologies.interfaces;

import is.technologies.tools.InterestOnBalanceException;
import is.technologies.tools.MinAmountOfMoneyException;

public interface IObservable {
    void AddObserver(IObserver observer);
    void RemoveObserever(IObserver observer);
    void NotifyObservers() throws InterestOnBalanceException, MinAmountOfMoneyException;
}
