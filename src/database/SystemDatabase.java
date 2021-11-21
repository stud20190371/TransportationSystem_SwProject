package database;

import java.util.ArrayList;

import interfaces.Notifiable;
import interfaces.Notifier;
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

    @Override
    public void notify(Notifiable notifiable, String notification) {
        notifiable.addNotification(notification);
    }
}
