package is.technologies.builders;
import is.technologies.models.Client;
import is.technologies.tools.PassportNumberException;

/**
 * The ClientBuilder class is responsible for building a client object with the provided information.
 */
public class ClientBuilder {

    private String Name;
    private String Surname;
    private String Address;
    private int NumberOfPassport;

    /**
     * Constructs a new ClientBuilder object with default values.
     */
    public ClientBuilder() {
        Name = null;
        Surname = null;
        Address = null;
        NumberOfPassport = 0;
    }

    /**
     * Sets the name and surname of the client.
     *
     * @param name The name of the client.
     * @param surname The surname of the client.
     * @return This ClientBuilder object.
     * @throws NullPointerException If either name or surname is null.
     */
    public ClientBuilder AddNameAndSurname(String name, String surname) throws NullPointerException {
        if(name == null || surname == null) {
            throw new NullPointerException();
        }
        Name = name;
        Surname = surname;
        return this;
    }

    /**
     * Sets the address of the client.
     *
     * @param address The address of the client.
     * @return This ClientBuilder object.
     * @throws NullPointerException If address is null.
     */
    public ClientBuilder AddAdress(String address) throws NullPointerException {
        if(address == null) {
            throw new NullPointerException();
        }
        Address = address;
        return this;
    }

    /**
     * Sets the passport number of the client.
     *
     * @param passport The passport number of the client.
     * @return This ClientBuilder object.
     * @throws NullPointerException If passport is 0.
     */
    public ClientBuilder AddPassport(int passport) throws NullPointerException {
        if(passport == 0) {
            throw new NullPointerException();
        }
        NumberOfPassport = passport;
        return this;
    }

    /**
     * Builds a new Client object with the provided information.
     *
     * @return A new Client object with the provided information.
     * @throws PassportNumberException If the passport number is not valid.
     */
    public Client Build() throws PassportNumberException {
        if(Name == null || Surname == null) {
            throw new NullPointerException();
        }
        return new Client(Name, Surname, Address, NumberOfPassport);
    }
}
