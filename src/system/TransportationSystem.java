package system;

import authentication.Authenticator;
import database.SystemDatabase;

public class TransportationSystem {
    private Authenticator authenticator; 
    private InputsValidator inputsValidator;
    private SystemDatabase sysDatabase;

    TransportationSystem(){
        this.authenticator = new Authenticator();
        this.inputsValidator = new InputsValidator();
        this.sysDatabase = SystemDatabase.createInstance();
    }

    public static void main(String[] args) {
        System.out.println("\n--------------------------------------------------");
        System.out.println("Welcome to FCI transportation app!");
    }
}
