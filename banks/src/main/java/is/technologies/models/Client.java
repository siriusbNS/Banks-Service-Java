package is.technologies.models;

import com.sun.jdi.connect.Connector;
import is.technologies.entities.Bank;
import is.technologies.interfaces.IObserver;
import is.technologies.tools.PassportNumberException;

import java.math.BigDecimal;

public class Client implements IObserver {
    private int _passportNumberMin = 1000000000;
    public Client(String name,String surname, String adress, int numberOfPassport) throws PassportNumberException
    {
        if(name == null || surname == null)
        {
            throw new NullPointerException("Uncorrect Name or Surname");
        }
        Name = name;
        Surname = surname;
        VerificationPassed = false;
        if ((adress != null) || (numberOfPassport >= 1000000000))
        {
            VerificationPassed = true;
        }
        if (numberOfPassport < _passportNumberMin && numberOfPassport != 0)
            throw new PassportNumberException("Passport is not correct");
        Adress = adress;
        NumberOfPassport = numberOfPassport;

    }
    private String Name;
    private String Surname;
    private String Adress;
    private int NumberOfPassport;
    private boolean VerificationPassed;

    public String getAdress() {
        return Adress;
    }

    public String getName() {
        return Name;
    }

    public boolean isVerificationPassed() {
        return VerificationPassed;
    }

    public int getNumberOfPassport() {
        return NumberOfPassport;
    }

    public String getSurname() {
        return Surname;
    }

    public void AddPassport(int passport) throws PassportNumberException {
        if (passport < _passportNumberMin)
            throw new PassportNumberException("Passport is not correct");
        NumberOfPassport = passport;
        VerificationPassed = true;
    }
    public void AddAdress(String adress)
    {
        if(adress == null) {
        throw new NullPointerException();
        }
        Adress = adress;
        VerificationPassed = true;
    }
    public void Update(Object obj)
    {

        if(obj == null)
        {
            throw new NullPointerException();
        }
        Bank bankInf = (Bank)obj;
        System.out.println("В банке " + bankInf.getNameOfBank() + " процентная ставка : " + bankInf.getInterestOnBalance() + "; кредитный процент : " + bankInf.getCreditInterest() + "; лимит на перевод в небезопасных аккаунтах : "+bankInf.getLimitOfMoneyTransaction());
    }
}
