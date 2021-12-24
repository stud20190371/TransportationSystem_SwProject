package database;

import java.util.ArrayList;

import user.Driver;

public class SystemDrivers {
    private ArrayList<Driver> drivers;
    private static SystemDrivers sysDriversInstance = null;

    private SystemDrivers(){
        drivers = new ArrayList<>();
    }

    public static SystemDrivers createInstance(){
        if(sysDriversInstance == null){
            sysDriversInstance = new SystemDrivers();
        }

        return sysDriversInstance;
    }

    public ArrayList<Driver> getDrivers(){
        return this.drivers;
    }
    
    public void addDriver(Driver driver){
        this.drivers.add(driver);
    }
}
