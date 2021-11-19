package database;

import interfaces.Notifiable;
import interfaces.Notifier;

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

    @Override
    public void notify(Notifiable notifiable, String notification) {
        notifiable.addNotification(notification);
    }
}
