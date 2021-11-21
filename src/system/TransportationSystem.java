package system;

import java.util.HashMap;
import java.util.Scanner;

import authentication.AdminAuthValidator;
import authentication.Authenticator;
import authentication.DriverAuthValidator;
import authentication.PassengerAuthValidator;
import database.SystemDatabase;
import enums.UserType;
import user.*;

public class TransportationSystem {
    private static User currentUser;
    private static Authenticator authenticator = new Authenticator();; 
    private static InputsValidator inputsValidator = new InputsValidator();
    private static SystemDatabase sysDatabase = SystemDatabase.createInstance();

    private static Scanner scan = new Scanner(System.in);
    private static boolean isInvalidChoose = true;
    private static int userChoose = 0;


    private static void clearConsole(){
        System.out.println("\033[H\033[2J");
        System.out.flush();
        System.out.println("--------------------------------------------------");
        System.out.println("Welcome to FCI transportation app!\n");
    }

    private static void clearScanner(){
        scan = null;
        scan = new Scanner(System.in);
    }
    
    private static void displayAuthMenu(boolean clr){
        if(clr){
            clearConsole();
        }

        clearScanner();
        isInvalidChoose = true;
        System.out.println("1- Signup");
        System.out.println("2- Login");
        System.out.println("3- Exit");

        while(isInvalidChoose){
            isInvalidChoose = false;
            userChoose = scan.nextInt();

            switch(userChoose){
                case 1 -> displaySignupMenu();
                case 2 -> displayLoginMenu();
                case 3 -> System.exit(0);
                default -> {
                    System.out.println("Invalid Choose!");
                    isInvalidChoose = true; 
                }
            }
        }
    }

    private static void displaySignupMenu(){
        clearConsole();
        clearScanner();
        isInvalidChoose = true;
        System.out.println("1- Signup as a driver");
        System.out.println("2- Signup as a passenger");
        System.out.println("3- Back");
        System.out.println("4- Exit");

        while(isInvalidChoose){
            isInvalidChoose = false;
            userChoose = scan.nextInt();

            switch(userChoose){
                case 1 -> {
                    authenticator.setAuthValidator(new DriverAuthValidator());
                    performSignup(UserType.DRIVER, true);
                }
                case 2 -> {
                    authenticator.setAuthValidator(new PassengerAuthValidator());
                    performSignup(UserType.PASSENGER, true);
                }
                case 3 -> displayAuthMenu(true);
                case 4 -> System.exit(0);
                default -> {
                    System.out.println("Invalid Choose!");
                    isInvalidChoose = true; 
                }
            }
        }
    }

    private static void displayLoginMenu(){
        clearConsole();
        clearScanner();
        isInvalidChoose = true;
        System.out.println("1- Login as an admin");
        System.out.println("2- Login as a driver");
        System.out.println("3- Login as a passenger");
        System.out.println("4- Back");
        System.out.println("5- Exit");

        while(isInvalidChoose){
            isInvalidChoose = false;
            userChoose = scan.nextInt();

            switch(userChoose){
                case 1 -> {
                    authenticator.setAuthValidator(new AdminAuthValidator());
                    performLogin(UserType.ADMIN, true);
                }
                case 2 -> {
                    authenticator.setAuthValidator(new DriverAuthValidator());
                    performLogin(UserType.DRIVER, true);
                }
                case 3 -> {
                    authenticator.setAuthValidator(new PassengerAuthValidator());
                    performLogin(UserType.PASSENGER, true);
                }
                case 4 -> displayAuthMenu(true);
                case 5 -> System.exit(0);
                default -> {
                    System.out.println("Invalid Choose!");
                    isInvalidChoose = true; 
                }
            }
        }
    }

    private static HashMap<String, String> displaySignupFields(UserType userType){
        String[] requiredFields = {};

        if(userType.equals(UserType.DRIVER)){
            String[] driversRequiredFields = {"username", "mobile number", "national id", "driving licence", "email", "password"};
            requiredFields = driversRequiredFields;
        }else if(userType.equals(UserType.PASSENGER)){
            String[] passengerRequiredFields = {"username", "mobile number", "email", "password"};
            requiredFields = passengerRequiredFields;
        }

        HashMap<String, String> userInfo = new HashMap<>();
        String userInput = "";

        for(int i=0; i<requiredFields.length; i++){
            clearScanner();
            String fieldName = "";

            try{
                fieldName = requiredFields[i];
                System.out.println("Enter your " + fieldName + (fieldName == "email" ? " (optional)": "") + ":");
                userInput = scan.nextLine();
                fieldName = fieldName.replace(" ", "_");
                inputsValidator.checkValidation(fieldName, userInput);
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
                i--;
                continue;
            }

            userInfo.put(fieldName, userInput);
        }
        
        return userInfo;
    }

    private static HashMap<String, String> displayLoginFields(){
        String[] requiredFields = {"username", "password"};

        HashMap<String, String> userInfo = new HashMap<>();
        String userInput = "";

        for(int i=0; i<requiredFields.length; i++){
            clearScanner();
            String fieldName = requiredFields[i];
            System.out.println("Enter your " + fieldName + ":");
            userInput = scan.nextLine();

            if(userInput.trim() == ""){
                System.out.println("This field can be empty!");
                i--;
                continue;
            }

            userInfo.put(fieldName.replace(" ", "_"), userInput);
        }
        
        return userInfo;
    }

    private static void displayVerificationState(){
        clearConsole();
        System.out.println("You are not verified!");
        displayAuthMenu(false);
    }

    private static void displaySuspensionState(){
        clearConsole();
        System.out.println("You are suspended!");
        displayAuthMenu(false);
    }
    
    private static void performSignup(UserType userType, boolean clr){
        if(clr){
            clearConsole();
        }

        HashMap<String, String> userInfo = displaySignupFields(userType);
        
        try{
            User user = authenticator.signup(userInfo);

            if(user instanceof Driver){
                if(!((Driver)user).isVerified()){
                    displayVerificationState();
                    return;
                }else if(((Driver)user).isSuspended()){
                    displaySuspensionState();
                    return;
                }else{
                    displayDriverBoard();
                }
            }else if(user instanceof Passenger){
                if(((Passenger)user).isSuspended()){
                    displaySuspensionState();
                    return;
                }else{
                    displayPassengerBoard();
                }
            }
        }catch(Exception ex){
            clearConsole();
            System.out.println(ex.getMessage() + ", please try again");
            displayAuthMenu(false);
            return;
        }
    }
    
    private static void performLogin(UserType userType, boolean clr){
        if(clr){
            clearConsole();
        }

        HashMap<String, String> userInfo = displayLoginFields();

        try{
            User user = authenticator.login(
                userInfo.get("username"), 
                userInfo.get("password")
            );

            if(user instanceof Admin){
                displayAdminBoard();
            }else if(user instanceof Driver){
                if(!((Driver)user).isVerified()){
                    displayVerificationState();
                    return;
                }else if(((Driver)user).isSuspended()){
                    displaySuspensionState();
                    return;
                }else{
                    displayDriverBoard();
                }
            }else if(user instanceof Passenger){
                if(((Passenger)user).isSuspended()){
                    displaySuspensionState();
                    return;
                }else{
                    displayPassengerBoard();
                }
            }
        }catch(Exception ex){
            clearConsole();
            System.out.println(ex.getMessage() + ", please try again");
            displayAuthMenu(false);
            return;
        }
    }

    private static void displayAdminBoard(){
        clearConsole();
        System.out.println("1- Verify new drivers");
        System.out.println("2- Suspend users");
        System.out.println("3- Logout");
        System.out.println("4- Exit");
    }

    private static void displayDriverBoard(){
        clearConsole();
        System.out.println("1- Add favorite area");
        System.out.println("2- Display notifications");
        System.out.println("3- Display ride requests");
        System.out.println("4- Logout");
        System.out.println("5- Exit");
    }

    private static void displayPassengerBoard(){
        clearConsole();
        System.out.println("1- Request a ride");
        System.out.println("2- Display notifications");
        System.out.println("3- Display drivers");
        System.out.println("4- Logout");
        System.out.println("5- Exit");
    }
    public static void main(String[] args) {
        Admin systemAdmin = new Admin(
            "fciAdmin",
            "12345678910",
            null,
            "12345678"
        );

        sysDatabase.systemAdmin().setAdmin(systemAdmin);

        displayAuthMenu(true);
    }
}
