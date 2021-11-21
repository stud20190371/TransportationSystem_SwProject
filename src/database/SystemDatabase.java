package database;

import java.util.ArrayList;

import interfaces.Notifiable;
import interfaces.Notifier;
import interfaces.RideRequester;
import rideRequest.RideRequest;
import user.Admin;
import user.Driver;
import user.Passenger;

public class SystemDatabase implements Notifier{
    private SystemAdmin systemAdmin;
    private SystemDrivers systemDrivers;
    private SystemPassengers systemPassengers;
    private SystemRideRequests systemRideRequests;

    private static SystemDatabase sysDatabaseInstance = null;

    private SystemDatabase(){
        this.systemAdmin = SystemAdmin.createInstance();
        this.systemDrivers = SystemDrivers.createInstance();
        this.systemPassengers = SystemPassengers.createInstance();
        this.systemRideRequests = SystemRideRequests.createInstance();
    }

    public static SystemDatabase createInstance(){
        if(sysDatabaseInstance == null){
            sysDatabaseInstance = new SystemDatabase();
        }

        return sysDatabaseInstance;
    }

    public SystemAdmin systemAdmin(){
        return systemAdmin;
    }

    public SystemDrivers systemDrivers(){
        return systemDrivers;
    }

    public SystemPassengers systemPassengers(){
        return systemPassengers;
    }

    public SystemRideRequests systemRideRequests(){
        return systemRideRequests;
    }
    
    public Admin getSystemAdmin(){
        return this.systemAdmin.getAdmin();
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

    public ArrayList<RideRequest> getRequesterRequests(RideRequester requester){
        ArrayList<RideRequest> requests = new ArrayList<>();

        for(RideRequest request: systemRideRequests.getRideRequests()){
            if(request.matchRequester(requester)){
                requests.add(request);
            }
        }

        return requests;
    }

    public void notifyDrivers(RideRequest request){
        for(Driver driver: systemDrivers.getDrivers()){
            if(driver.searchArea(request.getSourceName()) != -1){
                String notification = "\nThere's a ride request by (" + request.getRequesterName() + ")\n" + 
                            "Source: " + request.getSourceName() + " and Destination: " + request.getDestName() + "\n";
                            
                notify(driver, notification);
            }
        }
    }

    @Override
    public void notify(Notifiable notifiable, String notification) {
        notifiable.addNotification(notification);
    }
}
