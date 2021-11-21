package system;

import java.util.ArrayList;
import java.util.Scanner;

import authentication.Authenticator;
import database.SystemDatabase;
import interfaces.Notifiable;
import user.*;

public class CommonSection {
    static User authenticatedUser;
    static Authenticator authenticator = new Authenticator();; 
    static InputsValidator inputsValidator = new InputsValidator();
    static SystemDatabase sysDatabase = SystemDatabase.createInstance();

    static Scanner scan = new Scanner(System.in);
    static boolean isInvalidChoose = true;
    static int userChoose = 0;

    static void clearConsole(){
        System.out.println("\033[H\033[2J");
        System.out.flush();
        System.out.println("--------------------------------------------------");
        System.out.println("Welcome to FCI transportation app!\n");
    }

    static void clearScanner(){
        scan = null;
        scan = new Scanner(System.in);
    }

    static void displayNotification(Notifiable notifiable){
        clearConsole();

        ArrayList<String> notifications = notifiable.getNotifications();

        if(notifications.size() > 0){
            for(int i=0; i<notifications.size(); i++){
                System.out.println("(" + (i+1) + ")");
                System.out.println(notifications.get(i));
            }
        }else{
            System.out.println("There're no notifications");
        }

        clearScanner();
        isInvalidChoose = true;

        System.out.println("\n1- Back");

        while(isInvalidChoose){
            isInvalidChoose = false;
            userChoose = scan.nextInt();

            switch(userChoose){
                case 1 -> {
                    if(notifiable instanceof Driver){
                        DriverSection.displayDriverBoard();
                    }else if(notifiable instanceof Passenger){
                        PassengerSection.displayPassengerBoard();
                    }
                }
                default -> {
                    System.out.println("Invalid Choose!");
                    isInvalidChoose = true; 
                }
            }
        }
    }
}
