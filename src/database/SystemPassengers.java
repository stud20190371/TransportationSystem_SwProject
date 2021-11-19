package database;

import java.util.ArrayList;

import user.Passenger;

public class SystemPassengers {
    private ArrayList<Passenger> passengers;
    private static SystemPassengers sysPassengersInstance = null;

    private SystemPassengers(){}

    public static SystemPassengers createInstance(){
        if(sysPassengersInstance == null){
            sysPassengersInstance = new SystemPassengers();
        }

        return sysPassengersInstance;
    }
    
    public void addPassenger(Passenger passenger){
        this.passengers.add(passenger);
    }
}
