package system;

import java.util.ArrayList;

import interfaces.*;
import rating.Rate;
import rideRequest.*;
import user.*;

public class PassengerSection {
    static void displayPassengerBoard(){
        CommonSection.clearConsole();
        CommonSection.clearScanner();
        CommonSection.isInvalidChoose = true;
        System.out.println("1- Request a ride");
        System.out.println("2- Display notifications (" + ((Passenger) CommonSection.authenticatedUser).getNotifications().size() + ")");
        System.out.println("3- Display drivers (rate and get avg rating)");
        System.out.println("4- Display my ride requests");
        System.out.println("5- Logout");
        System.out.println("6- Exit");

        while(CommonSection.isInvalidChoose){
            CommonSection.isInvalidChoose = false;
            CommonSection.userChoose = CommonSection.scan.nextInt();

            switch(CommonSection.userChoose){
                case 1 -> requestRide();
                case 2 -> CommonSection.displayNotification(((Passenger) CommonSection.authenticatedUser));
                case 3 -> displayDriversAndRate(((Passenger) CommonSection.authenticatedUser));
                case 4 -> displayRequesterRequests(((Passenger) CommonSection.authenticatedUser));
                case 5 -> AuthSection.performLogout();
                case 6 -> System.exit(0);
                default -> {
                    System.out.println("Invalid Choose!");
                    CommonSection.isInvalidChoose = true; 
                }
            }
        }
    }

    private static void requestRide(){
        CommonSection.clearConsole();
        CommonSection.clearScanner();

        String source, dest;
        while(true){
            System.out.print("\nEnter source: ");
            source = CommonSection.scan.nextLine();  
            
            if(source.trim().equals("")){
                System.out.println("Source can't be empty!");
            }else{
                break;
            }
        }

        while(true){
            System.out.print("\nEnter destination: ");
            dest = CommonSection.scan.nextLine(); 
            
            if(dest.trim().equals("")){
                System.out.println("Destination can't be empty!");
            }else{
                break;
            }
        }

        

        ((Passenger) CommonSection.authenticatedUser).requestRide(source, dest);
        displayPassengerBoard();
    }

    private static void rateDriver(Rater rater, Rateable ratable){
        CommonSection.clearScanner();      
        boolean isInvalidRate = true;

        while(isInvalidRate){
            isInvalidRate = false;
            System.out.print("Enter rate between 1 and 5: ");
            float rateNum = CommonSection.scan.nextFloat();

            if(rateNum >= 1 && rateNum <= 5){
                Rate rate = new Rate(rater, ratable, rateNum);
                rater.rate(ratable, rate);
            }else{
                System.out.println("Invalid rate");
                isInvalidRate = true;
            }
        }

        displayDriversAndRate(rater);

        CommonSection.isInvalidChoose = false;
        CommonSection.userChoose = CommonSection.scan.nextInt();
    }

    private static void getDriverAvgRating(Rater rater, Rateable ratable){
        System.out.println(ratable.ratingsAvg());

        CommonSection.clearScanner();
        CommonSection.isInvalidChoose = true;

        System.out.println("\n1- Back");

        while(CommonSection.isInvalidChoose){
            CommonSection.isInvalidChoose = false;
            CommonSection.userChoose = CommonSection.scan.nextInt();

            switch(CommonSection.userChoose){
                case 1 -> displayDriversAndRate(rater);
                default -> {
                    System.out.println("Invalid Choose!");
                    CommonSection.isInvalidChoose = true; 
                }
            }
        }
    }

    private static void backToPassengerBoard(){
        System.out.println("\n1- back");

        while(CommonSection.isInvalidChoose){
            CommonSection.isInvalidChoose = false;
            CommonSection.userChoose = CommonSection.scan.nextInt();

            switch(CommonSection.userChoose){
                case 1 -> displayPassengerBoard();
                default -> {
                    System.out.println("Invalid Choose!");
                    CommonSection.isInvalidChoose = true; 
                }
            }
        }
    }

    private static void displayDriversAndRate(Rater rater){
        CommonSection.clearConsole();
        CommonSection.clearScanner();
        CommonSection.isInvalidChoose = true;

        ArrayList<Driver> drivers = CommonSection.sysDatabase.getSystemDrivers();

        if(drivers.size() > 0){
            for(int i=0; i<drivers.size(); i++){
                System.out.println("(" + (i+1) + ")");
                System.out.println(drivers.get(i));
            }

            System.out.println("\n1- Rate a driver");
            System.out.println("2- Get a driver's avg rating");
            System.out.println("3- back");

            while(CommonSection.isInvalidChoose){
                CommonSection.isInvalidChoose = false;
                CommonSection.userChoose = CommonSection.scan.nextInt();
    
                switch(CommonSection.userChoose){
                    case 1, 2 -> {
                        boolean isInvalidDriverNum = true;

                        while(isInvalidDriverNum){
                            isInvalidDriverNum = false;
                            System.out.print("Enter driver number: ");
                            int driverNum = CommonSection.scan.nextInt();

                            if(driverNum >= 1 && driverNum <= drivers.size()){
                                Rateable driver = drivers.get(driverNum-1);

                                if(CommonSection.userChoose == 1){
                                    rateDriver(rater, driver);
                                }else if(CommonSection.userChoose == 2){
                                    getDriverAvgRating(rater, driver);
                                }
                            }else{
                                System.out.println("Invalid driver number");
                                isInvalidDriverNum = true;
                            }
                        }
                    }
                    case 3 -> displayPassengerBoard();
                    default -> {
                        System.out.println("Invalid Choose!");
                        CommonSection.isInvalidChoose = true; 
                    }
                }
            }
        }else{
            System.out.println("There're no drivers!");
            backToPassengerBoard();
        }
    }

    private static void displayRequestOffer(RideRequester requester, RideRequest request){
        CommonSection.clearConsole();
        CommonSection.clearScanner();
        CommonSection.isInvalidChoose = true;
        
        ArrayList<Offer> offers = request.getOffers();

        if(offers.size() > 0){
            for(int i=0; i<offers.size(); i++){
                System.out.println("(" + (i+1) + ")");
                System.out.println(offers.get(i));
            }
        }else{
            System.out.println("There're no offers!");
        }

        System.out.println("\n1- back");

        while(CommonSection.isInvalidChoose){
            CommonSection.isInvalidChoose = false;
            CommonSection.userChoose = CommonSection.scan.nextInt();

            switch(CommonSection.userChoose){
                case 1 -> displayRequesterRequests(requester);
                default -> {
                    System.out.println("Invalid Choose!");
                    CommonSection.isInvalidChoose = true; 
                }
            }
        }
    }

    private static void displayRequesterRequests(RideRequester requester){
        CommonSection.clearConsole();
        CommonSection.clearScanner();
        CommonSection.isInvalidChoose = true;

        ArrayList<RideRequest> requests = CommonSection.sysDatabase.getRequesterRequests(requester);

        if(requests.size() > 0){
            for(int i=0; i<requests.size(); i++){
                System.out.println("(" + (i+1) + ")");
                System.out.println(requests.get(i));
            }

            System.out.println("\n1- Get offers of a request");
            System.out.println("2- Back");

            while(CommonSection.isInvalidChoose){
                CommonSection.isInvalidChoose = false;
                CommonSection.userChoose = CommonSection.scan.nextInt();

                switch(CommonSection.userChoose){
                    case 1 -> {
                        boolean isInvalidRequestNum = true;

                        while(isInvalidRequestNum){
                            isInvalidRequestNum = false;
                            System.out.print("Enter request number: ");
                            int requestNum = CommonSection.scan.nextInt();

                            if(requestNum >= 1 && requestNum <= requests.size()){
                                displayRequestOffer(requester, requests.get(requestNum-1));
                            }else{
                                System.out.println("Invalid request number");
                                isInvalidRequestNum = true;
                            }
                        }
                    }
                    case 2 -> displayPassengerBoard();
                    default -> {
                        System.out.println("Invalid Choose!");
                        CommonSection.isInvalidChoose = true; 
                    }
                }
            }

        }else{
            System.out.println("There're no requests!");
            backToPassengerBoard();
        }
    }
    
}
