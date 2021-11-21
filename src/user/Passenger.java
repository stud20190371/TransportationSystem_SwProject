package user;

import java.util.ArrayList;

import interfaces.Notifiable;
import interfaces.Rateable;
import interfaces.Rater;
import interfaces.RideRequester;
import interfaces.Suspendable;
import rating.Rate;
import rideRequest.RideRequest;

public class Passenger extends User implements Suspendable, Rater, RideRequester, Notifiable{
    private boolean suspensionState = false;
    private ArrayList<String> notifications;
    private static int passengersCount = 0;


    public Passenger(String username, String mobileNum, String email, String password){
        super(("passenger"+(++passengersCount)), username, mobileNum, email, password);
        this.notifications = new ArrayList<>();
    }

    @Override
    public boolean isSuspended() {
        return this.suspensionState;
    }

    @Override
    public void changeSuspensionState(boolean state) {
        this.suspensionState = state;
    }

    @Override
    public void rate(Rateable rateable, Rate rate) {
        rateable.addRate(rate);
    }

    @Override
    public void deleteRate(Rateable rateable, Rate rate) {
        rateable.removeRate(rate);
    }

    @Override
    public void updateRate(Rateable rateable, Rate rate, float newRate) {
        rateable.updateRate(rate, newRate);
    }

    @Override
    public float getRatingsAvg(Rateable rateable) {
        return rateable.ratingsAvg();
    }

    @Override
    public void requestRide(String source, String dest) {

    }

    @Override
    public void deleteRequest(RideRequest request) {
        
    }

    @Override
    public void addNotification(String notification) {
        this.notifications.add(notification);
    }

    @Override
    public void deleteNotification(String notification) {
        this.notifications.remove(notification);
    }
}
