package is.technologies.interfaces;

import is.technologies.tools.DateException;
import is.technologies.tools.MinAmountOfMoneyException;
import is.technologies.tools.VerificationException;

public interface ICommand {
    void Execute() throws DateException, VerificationException, MinAmountOfMoneyException;
    void Undo() throws DateException, VerificationException, MinAmountOfMoneyException;
    int GetId();
}
