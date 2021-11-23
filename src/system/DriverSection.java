package system;

import java.util.ArrayList;

import rideRequest.RideRequest;
import user.*;

public class DriverSection {
    static void displayDriverBoard(){
        CommonSection.clearConsole();
        CommonSection.clearScanner();
        CommonSection.isInvalidChoose = true;
        System.out.println("1- Add favorite area");
        System.out.println("2- Display notifications (" + ((Driver) CommonSection.authenticatedUser).getNotifications().size() + ")");
        System.out.println("3- Display ride requests");
        System.out.println("4- Logout");
        System.out.println("5- Exit");
        
        while(CommonSection.isInvalidChoose){
            CommonSection.isInvalidChoose = false;
            CommonSection.userChoose = CommonSection.scan.nextInt();

            switch(CommonSection.userChoose){
                case 1 -> addFavoriteArea();
                case 2 -> CommonSection.displayNotification(((Driver) CommonSection.authenticatedUser));
                case 3 -> displayRideRequests();
                case 4 -> AuthSection.performLogout();
                case 5 -> System.exit(0);
                default -> {
                    System.out.println("Invalid Choose!");
                    CommonSection.isInvalidChoose = true; 
                }
            }
        }
    }
    
    private static void addFavoriteArea() {
        CommonSection.clearConsole();
        CommonSection.clearScanner();

        String area = ""; 
        while(true){
            System.out.print("\nEnter area: ");
            area = CommonSection.scan.nextLine();  
            
            if(area.trim().equals("")){
                System.out.println("Area can't be empty!");
            }else{
                break;
            }
        }

        ((Driver) CommonSection.authenticatedUser).addArea(area);
        displayDriverBoard();
    }

    private static void backToDriverBoard(){
        System.out.println("\n1- back");

        while(CommonSection.isInvalidChoose){
            CommonSection.isInvalidChoose = false;
            CommonSection.userChoose = CommonSection.scan.nextInt();

            switch(CommonSection.userChoose){
                case 1 -> displayDriverBoard();
                default -> {
                    System.out.println("Invalid Choose!");
                    CommonSection.isInvalidChoose = true; 
                }
            }
        }
    }

    private static void displayRideRequests(){
        CommonSection.clearConsole();
        CommonSection.clearScanner();
        CommonSection.isInvalidChoose = true;
        
        ArrayList<String> favoriteArea = ((Driver) CommonSection.authenticatedUser).getFavoriteAreas();
        ArrayList<RideRequest> allRequests = CommonSection.sysDatabase.getSystemRideRequests();
        ArrayList<RideRequest> filteredRequests = new ArrayList<RideRequest>();
        
        for(RideRequest request: allRequests) {
            if(favoriteArea.contains(request.getSourceName())) {
                filteredRequests.add(request) ;
            }
        }
        
        if(filteredRequests.size() > 0){
            for(int i = 0 ; i < filteredRequests.size() ; i++) {
                System.out.println("(" + (i+1) + ")");
                System.out.println(filteredRequests.get(i));
            }

            System.out.println("\n1- Make offer");
            System.out.println("2- Back");

            while(CommonSection.isInvalidChoose){
                CommonSection.isInvalidChoose = false;
                CommonSection.userChoose = CommonSection.scan.nextInt();
    
                switch(CommonSection.userChoose){
                    case 1-> {
                        boolean isInvalidRequestNum = true;

                        while(isInvalidRequestNum){
                            isInvalidRequestNum = false;
                            System.out.print("Enter ride request number: ");
                            int requestNum = CommonSection.scan.nextInt();

                            if(requestNum >= 1 && requestNum <= filteredRequests.size()){
                                makeOffer(filteredRequests.get(requestNum-1));
                            }else{
                                System.out.println("Invalid ride request number");
                                isInvalidRequestNum = true;
                            }
                        }
                    }
                    case 2 -> displayDriverBoard();
                    default -> {
                        System.out.println("Invalid Choose!");
                        CommonSection.isInvalidChoose = true; 
                    }
                }
            }
        }else{
            System.out.println("There're no ride requests!");
            backToDriverBoard();
        }
    }
    
    private static void makeOffer(RideRequest request){
        CommonSection.clearConsole();
        CommonSection.clearScanner();      
        boolean isInvalidPrice = true;

        while(isInvalidPrice){
            isInvalidPrice = false;
            System.out.print("Enter price: ");
            float price = CommonSection.scan.nextFloat();

            if(price > 0){
                ((Driver) CommonSection.authenticatedUser).offer(request, price);
            }else{
                System.out.println("Invalid price!");
                isInvalidPrice = true;
            }
        }

        displayRideRequests();
    }
}