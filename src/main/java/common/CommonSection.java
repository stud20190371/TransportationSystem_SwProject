package common;

import authentication.Authenticator;
import database.SystemDatabase;
import user.*;

public class CommonSection {
    public static User authenticatedUser;
    public static Authenticator authenticator = new Authenticator();
    public static InputsValidator inputsValidator = new InputsValidator();
    public static SystemDatabase sysDatabase = SystemDatabase.createInstance();
}
