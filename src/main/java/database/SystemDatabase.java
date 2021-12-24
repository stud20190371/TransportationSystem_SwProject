package database;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import discounts.Discount;
import interfaces.*;
import rideRequest.*;
import user.*;

public class SystemDatabase implements Notifier{
    private SystemAdmin systemAdmin;
    private SystemDrivers systemDrivers;
    private SystemPassengers systemPassengers;
    private SystemRideRequests systemRideRequests;
    private ArrayList<String> featuredDestinationAreas;
    private ArrayList<Date> publicHolidays;

    private static SystemDatabase sysDatabaseInstance = null;

    private SystemDatabase(){
        this.systemAdmin = SystemAdmin.createInstance();
        this.systemDrivers = SystemDrivers.createInstance();
        this.systemPassengers = SystemPassengers.createInstance();
        this.systemRideRequests = SystemRideRequests.createInstance();
        this.featuredDestinationAreas = new ArrayList<>();
        this.publicHolidays = new ArrayList<>();
    }

    private void addDiscountsToRideRequest(RideRequest request) throws Exception {
        if(request.getRequester().getRideRequestsHistory().size() == 0){
            request.addDiscount(new Discount("First Ride", 10));
        }

        if(this.isFeaturedDestinationArea(request.getDestName())){
            request.addDiscount(new Discount("Featured Destination Area", 10));
        }

        if(request.getPassengersNum() == 2){
            request.addDiscount(new Discount("Ride Contains Two Passengers", 5));
        }

        if(this.isPublicHoliday(request.getRideDate())){
            request.addDiscount(new Discount("Public Holiday", 5));
        }

        if(this.isTodayUserBirthdate(request.getRequester().getRequesterBirthdate())){
            request.addDiscount(new Discount("Passenger Birthdate", 10));
        }
    }

    private boolean isFeaturedDestinationArea(String area){
        return this.featuredDestinationAreas.contains(area.toLowerCase());
    }

    private boolean isPublicHoliday(Date date){
        LocalDate ld1 = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        for(Date d: publicHolidays){
            LocalDate ld2 = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
           
            if((ld1.getMonthValue() == ld2.getMonthValue()) && (ld1.getDayOfMonth() == ld2.getDayOfMonth())){
                return true;
            } 
        }

        return false;
    }

    private boolean isTodayUserBirthdate(Date userBirthdate){
        LocalDate today = LocalDate.now();
        LocalDate birthdate = userBirthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return (today.getMonthValue() == birthdate.getMonthValue()) && (today.getDayOfMonth() == birthdate.getDayOfMonth());
    }

    public static SystemDatabase createInstance(){
        if(sysDatabaseInstance == null){
            sysDatabaseInstance = new SystemDatabase();
        }

        return sysDatabaseInstance;
    }
    
    public Admin getSystemAdmin(){
        return this.systemAdmin.getAdmin();
    }

    public void setSystemAdmin(Admin admin){
        this.systemAdmin.setAdmin(admin);
    }

    public ArrayList<Driver> getSystemDrivers(){
        return this.systemDrivers.getDrivers();
    }

    public ArrayList<Passenger> getSystemPassengers(){
        return this.systemPassengers.getPassengers();
    }

    public ArrayList<RideRequest> getSystemRideRequests(){
        return this.systemRideRequests.getRideRequests();
    }

    public void addFeaturedDestinationArea(String area){
        if(!this.isFeaturedDestinationArea(area)){
            this.featuredDestinationAreas.add(area.toLowerCase());
        }
    }

    public void addPublicHoliday(Date holiday){
        if(!this.isPublicHoliday(holiday)){
            this.publicHolidays.add(holiday);
        }
    }

    public void addRideRequest(RideRequest request) throws Exception {
        addDiscountsToRideRequest(request);
        this.systemRideRequests.addRequest(request);
    }

    // public ArrayList<RideRequest> getRequesterRequests(RideRequester requester){
    //     ArrayList<RideRequest> requests = new ArrayList<>();

    //     for(RideRequest request: systemRideRequests.getRideRequests()){
    //         if(request.matchRequester(requester)){
    //             requests.add(request);
    //         }
    //     }

    //     return requests;
    // }

    public void notifyDrivers(RideRequest request){
        for(Driver driver: systemDrivers.getDrivers()){
            if((driver.searchArea(request.getSourceName()) != -1) && !driver.isHandlingRide()){
                String notification = "\nThere's a ride request by (" + request.getRequesterName() + ")\n" + 
                            "Source: " + request.getSourceName() + " and Destination: " + request.getDestName() + "\n";
                            
                notify(driver, notification);
            }
        }
    }

    public void notifyPassenger(Passenger passenger, Offer offer){
        String notification = "\nThere's an offer by (" + offer.getOfferorName() + ")\n" + 
                            "Price: " + offer.getPrice() + "\n";

        notify(passenger, notification);
    }


    @Override
    public void notify(Notifiable notifiable, String notification) {
        notifiable.addNotification(notification);
    }

    

    
}
