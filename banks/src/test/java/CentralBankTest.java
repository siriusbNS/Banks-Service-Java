import is.technologies.interfaces.ICentralBank;
import is.technologies.service.CentralBank;
import is.technologies.tools.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CentralBankTest {
    @Test
    void NotifyClientsTest() throws LimitOfMoneyException, CreditInterestException, InterestOnBalanceException, MinAmountOfMoneyException, PassportNumberException, NegativeAmountOfMoneyException {
        ICentralBank centralBank = new CentralBank();
        var bank1 = centralBank.AddBank(1.10, 3.65, -10000, 10000, "Alpha");
        var bank2 = centralBank.AddBank(1.10, 3.65, -100000, 100000, "Tink");
        var client1 = centralBank.AddClientFullVersion(bank1, "Vlad", "Kuv", "Kron", 1222000000);
        var account = centralBank.AddDebitAccount(bank1, client1, 100000);
        centralBank.Subscribe(bank1, client1);
        centralBank.ChangeInterestOnBalance(bank1, 3.98);
        Assertions.assertTrue(centralBank.GetBanks().contains(bank1));
    }
}
