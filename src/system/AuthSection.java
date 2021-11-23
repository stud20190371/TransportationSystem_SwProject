package system;

import java.util.HashMap;

import authentication.*;
import enums.UserType;
import exceptions.*;
import user.*;


public class AuthSection {
    static void displayAuthMenu(boolean clr){
        CommonSection.clearAll(clr);

        System.out.println("1- Signup");
        System.out.println("2- Login");
        System.out.println("3- Exit");

        while(CommonSection.isInvalidChoose){
            CommonSection.getUserChoose();

            switch(CommonSection.userChoose){
                case 1 -> displaySignupMenu();
                case 2 -> displayLoginMenu();
                case 3 -> System.exit(0);
                default -> CommonSection.switchDefaultStatement();
            }
        }
    }

    static void displaySignupMenu(){
        CommonSection.clearAll(true);

        System.out.println("1- Signup as a driver");
        System.out.println("2- Signup as a passenger");
        System.out.println("3- Back");
        System.out.println("4- Exit");

        while(CommonSection.isInvalidChoose){
            CommonSection.getUserChoose();

            switch(CommonSection.userChoose){
                case 1 -> {
                    CommonSection.authenticator.setAuthValidator(new DriverAuthValidator());
                    performSignup(UserType.DRIVER);
                }
                case 2 -> {
                    CommonSection.authenticator.setAuthValidator(new PassengerAuthValidator());
                    performSignup(UserType.PASSENGER);
                }
                case 3 -> displayAuthMenu(true);
                case 4 -> System.exit(0);
                default -> CommonSection.switchDefaultStatement();
            }
        }
    }

    static void displayLoginMenu(){
        CommonSection.clearAll(true);

        System.out.println("1- Login as an admin");
        System.out.println("2- Login as a driver");
        System.out.println("3- Login as a passenger");
        System.out.println("4- Back");
        System.out.println("5- Exit");

        while(CommonSection.isInvalidChoose){
            CommonSection.getUserChoose();

            switch(CommonSection.userChoose){
                case 1 -> {
                    CommonSection.authenticator.setAuthValidator(new AdminAuthValidator());
                    performLogin(UserType.ADMIN);
                }
                case 2 -> {
                    CommonSection.authenticator.setAuthValidator(new DriverAuthValidator());
                    performLogin(UserType.DRIVER);
                }
                case 3 -> {
                    CommonSection.authenticator.setAuthValidator(new PassengerAuthValidator());
                    performLogin(UserType.PASSENGER);
                }
                case 4 -> displayAuthMenu(true);
                case 5 -> System.exit(0);
                default -> CommonSection.switchDefaultStatement();
            }
        }
    }

    static void displayUsernameField(HashMap<String, String> userInfo){
        try{
            CommonSection.clearScanner();
            System.out.println("Enter your username:");
            String userInput = CommonSection.scan.nextLine().trim();
            CommonSection.inputsValidator.checkValidation("username", userInput);
            userInfo.put("username", userInput);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            displayUsernameField(userInfo);
        }
    }

    static void displayMobileNumField(HashMap<String, String> userInfo){
        try{
            CommonSection.clearScanner();
            System.out.println("Enter your mobile number:");
            String userInput = CommonSection.scan.nextLine().trim();
            CommonSection.inputsValidator.checkValidation("mobile_number", userInput);
            userInfo.put("mobile_number", userInput);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            displayMobileNumField(userInfo);
        }
    }

    static void displayEmailField(HashMap<String, String> userInfo){
        try{
            CommonSection.clearScanner();
            System.out.println("Enter your email(optional):");
            String userInput = CommonSection.scan.nextLine().trim();
            CommonSection.inputsValidator.checkValidation("email", userInput);
            userInfo.put("email", userInput);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            displayEmailField(userInfo);
        }
    }

    static void displayPasswordField(HashMap<String, String> userInfo){
        try{
            CommonSection.clearScanner();
            System.out.println("Enter your password:");
            String userInput = CommonSection.scan.nextLine().trim();
            CommonSection.inputsValidator.checkValidation("password", userInput);
            userInfo.put("password", userInput);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            displayPasswordField(userInfo);
        }
    }

    static void displayNationalIDField(HashMap<String, String> userInfo){
        try{
            CommonSection.clearScanner();
            System.out.println("Enter your national id:");
            String userInput = CommonSection.scan.nextLine().trim();
            CommonSection.inputsValidator.checkValidation("national_id", userInput);
            userInfo.put("national_id", userInput);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            displayNationalIDField(userInfo);
        }
    }

    static void displayLicenseField(HashMap<String, String> userInfo){
        try{
            CommonSection.clearScanner();
            System.out.println("Enter your driving licence:");
            String userInput = CommonSection.scan.nextLine().trim();
            CommonSection.inputsValidator.checkValidation("driving_licence", userInput);
            userInfo.put("driving_licence", userInput);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            displayLicenseField(userInfo);
        }
    }
    
    static HashMap<String, String> displaySignupFields(UserType userType){
        HashMap<String, String> userInfo = new HashMap<>();

        displayUsernameField(userInfo);
        displayMobileNumField(userInfo);

        if(userType.equals(UserType.DRIVER)){
            displayNationalIDField(userInfo);
            displayLicenseField(userInfo);
        }

        displayEmailField(userInfo);
        displayPasswordField(userInfo);

        return userInfo;
    }

    static HashMap<String, String> displayLoginFields(){
        HashMap<String, String> userInfo = new HashMap<>();

        displayUsernameField(userInfo);
        displayPasswordField(userInfo);
        
        return userInfo;
    }

    static void displayErrorMsg(String msg){
        CommonSection.clearConsole();
        System.out.println(msg);
        displayAuthMenu(false);
    }
    
    static void reinputField(String fieldName, HashMap<String, String> userInfo){
        if(fieldName.equals("username")){
            displayUsernameField(userInfo);
        }else if(fieldName.equals("mobile_number")){
            displayMobileNumField(userInfo);
        }else if(fieldName.equals("email")){
            displayEmailField(userInfo);
        }else if(fieldName.equals("password")){
            displayPasswordField(userInfo);
        }else if(fieldName.equals("national_id")){
            displayNationalIDField(userInfo);
        }else if(fieldName.equals("driving_licence")){
            displayLicenseField(userInfo);
        }
    }

    static void displayInvalidCredentialsError(String msg, String fieldName, HashMap<String, String> userInfo){
        System.out.println(msg);
        CommonSection.clearAll(false);
        System.out.println("1- Reinput Field");
        System.out.println("2- Back home screen");
        
        while(CommonSection.isInvalidChoose){
            CommonSection.getUserChoose();

            switch(CommonSection.userChoose){
                case 1 -> reinputField(fieldName, userInfo);
                case 2 -> displayAuthMenu(true);
                default -> CommonSection.switchDefaultStatement();
            }
        }
    }

    static void performSignup(UserType userType){
        CommonSection.clearConsole();

        HashMap<String, String> userInfo = displaySignupFields(userType);
        
        while(true){
            try{
                User user = CommonSection.authenticator.signup(userInfo);
                CommonSection.authenticatedUser = user;
    
                if(user instanceof Driver){
                    DriverSection.displayDriverBoard();
                }else if(user instanceof Passenger){
                    PassengerSection.displayPassengerBoard();
                }

            }catch (NotVerifiedUserException ex){
                displayErrorMsg(ex.getMessage());
            }catch (UsernameExistException ex){
                displayInvalidCredentialsError(ex.getMessage(), "username", userInfo);
            }catch (EmailExistException ex){
                displayInvalidCredentialsError(ex.getMessage(), "email", userInfo);
            }catch (MobileNumberExistException ex){
                displayInvalidCredentialsError(ex.getMessage(), "mobile_number", userInfo);
            }catch(Exception ex){
                displayErrorMsg(ex.getMessage() + ", please try again");
            }

            break;
        }
    }
    
    static void performLogin(UserType userType){
        CommonSection.clearConsole();

        HashMap<String, String> userInfo = displayLoginFields();

        while(true){
            try{
                User user = CommonSection.authenticator.login(userInfo.get("username"), userInfo.get("password"));
                CommonSection.authenticatedUser = user;
    
                if(user instanceof Admin){
                    AdminSection.displayAdminBoard();
                }else if(user instanceof Driver){
                    DriverSection.displayDriverBoard();
                }else if(user instanceof Passenger){
                    PassengerSection.displayPassengerBoard();
                }

            }catch (NotVerifiedUserException ex){
                displayErrorMsg(ex.getMessage());
            }catch (SuspendedUserException ex){
                displayErrorMsg(ex.getMessage());
            }catch (UsernameNotExistException ex){
                displayInvalidCredentialsError(ex.getMessage(), "username", userInfo);
            }catch (IncorrectPasswordException ex){
                displayInvalidCredentialsError(ex.getMessage(), "password", userInfo);
            }catch(Exception ex){
                displayErrorMsg(ex.getMessage() + ", please try again");
            }
            
            break;
        }
    }

    static void performLogout(){
        CommonSection.authenticatedUser = null;
        CommonSection.authenticator.logout();
        displayAuthMenu(true);
    }
}
