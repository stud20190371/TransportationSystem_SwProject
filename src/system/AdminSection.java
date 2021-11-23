package system;

import user.Admin;

import java.util.ArrayList;

import enums.Board;
import interfaces.*;

public class AdminSection {
    static void displayAdminBoard(){
        CommonSection.clearAll(true);
        
        System.out.println("1- Verify new drivers");
        System.out.println("2- Suspend users");
        System.out.println("3- Suspend users");
        System.out.println("4- Logout");
        System.out.println("5- Exit");

        while(CommonSection.isInvalidChoose){
            CommonSection.getUserChoose();

            switch(CommonSection.userChoose){
                case 1 -> verifyDrivers();
                case 2 -> suspendUsers(true);
                case 3 -> suspendUsers(false);
                case 4 -> AuthSection.performLogout();
                case 5 -> System.exit(0);
                default -> CommonSection.switchDefaultStatement();
            }
        }
    }

    private static void verifyDrivers(){
        CommonSection.clearAll(true);

        ArrayList<Verifiable> verRequests = ((Admin) CommonSection.authenticatedUser).getVerificationRequests();

        if(verRequests.size() > 0){
            for(int i=0; i<verRequests.size(); i++){
                System.out.println("(" + (i+1) + ")");
                System.out.println(verRequests.get(i));
            }

            System.out.println("\n1- Verify a request");
            System.out.println("2- Back");

            while(CommonSection.isInvalidChoose){
                CommonSection.getUserChoose();

                switch(CommonSection.userChoose){
                    case 1 -> {
                        boolean isInvalidVerRequestNum = true;

                        while(isInvalidVerRequestNum){
                            isInvalidVerRequestNum = false;
                            System.out.print("Enter verification request number: ");
                            int verRequestNum = CommonSection.scan.nextInt();

                            if(verRequestNum >= 1 && verRequestNum <= verRequests.size()){
                                verRequests.get(verRequestNum-1).changeVerificationState(true);
                                verRequests.remove(verRequestNum-1);
                                verifyDrivers();
                            }else{
                                System.out.println("Invalid verification request number");
                                isInvalidVerRequestNum = true;
                            }
                        }
                    }
                    case 2 -> displayAdminBoard();
                    default -> CommonSection.switchDefaultStatement();
                }
            }
        }else{
            System.out.println("There're no verification requests!");
            CommonSection.backToBoard(Board.ADMIN_BOARD);
        }
    }

    private static void suspendUsers(boolean suspend){
        CommonSection.clearAll(true);

        ArrayList<Suspendable> users = new ArrayList<>();

        for(Suspendable driver: CommonSection.sysDatabase.getSystemDrivers()){
            if(
                (suspend && !driver.isSuspended()) || (!suspend && driver.isSuspended())
            ){
                users.add(driver);
            }
        }

        for(Suspendable passenger: CommonSection.sysDatabase.getSystemPassengers()){
            if(
                (suspend && !passenger.isSuspended()) || (!suspend && passenger.isSuspended())
            ){
                users.add(passenger);
            }
        }

        if(users.size() > 0){
            for(int i=0; i<users.size(); i++){
                System.out.println("(" + (i+1) + ")");
                System.out.println(users.get(i));
            }

            if(suspend){
                System.out.println("\n1- Suspend a user");
            }else{
                System.out.println("\n1- Unsuspend a user");
            }

            System.out.println("2- Back");

            while(CommonSection.isInvalidChoose){
                CommonSection.getUserChoose();

                switch(CommonSection.userChoose){
                    case 1 -> {
                        boolean isInvalidUserNum = true;

                        while(isInvalidUserNum){
                            isInvalidUserNum = false;
                            System.out.print("Enter user number: ");
                            int userNum = CommonSection.scan.nextInt();

                            if(userNum >= 1 && userNum <= users.size()){
                                users.get(userNum-1).changeSuspensionState(suspend);
                                suspendUsers(suspend);
                            }else{
                                System.out.println("Invalid user number");
                                isInvalidUserNum = true;
                            }
                        }
                    }
                    case 2 -> displayAdminBoard();
                    default -> CommonSection.switchDefaultStatement();
                }
            }
        }else{
            if(suspend){
                System.out.println("There're no users to suspend!");
            }else{
                System.out.println("There're no users to unsuspend!");
            }

            CommonSection.backToBoard(Board.ADMIN_BOARD);
        }
    }
}
