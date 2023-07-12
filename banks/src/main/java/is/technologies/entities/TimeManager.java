package is.technologies.entities;

import is.technologies.interfaces.IObservable;
import is.technologies.interfaces.IObserver;
import is.technologies.tools.InterestOnBalanceException;
import is.technologies.tools.MinAmountOfMoneyException;

import java.time.LocalDate;

public class TimeManager implements IObservable
        {
public TimeManager()
        {
        DateTime = LocalDate.now();
        }

private LocalDate DateTime;
private IObserver Observer;

public void AddObserver(IObserver observer)
        {
        Observer = observer;
        }

public void RemoveObserever(IObserver observer)
        {
        Observer = null;
        }

public void NotifyObservers() throws InterestOnBalanceException, MinAmountOfMoneyException {
        Observer.Update(DateTime);
        }

public void PlusDays(int value) throws InterestOnBalanceException, MinAmountOfMoneyException {
        DateTime.plusDays(value);
        Observer.Update(DateTime);
        }

public void PlusMonths(int value) throws InterestOnBalanceException, MinAmountOfMoneyException {
        DateTime = DateTime.plusMonths(value);
        Observer.Update(DateTime);
        }

public void PlusYears(int value) throws InterestOnBalanceException, MinAmountOfMoneyException {
        DateTime.plusYears(value);
        Observer.Update(DateTime);
        }
        }
