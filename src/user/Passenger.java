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
    public String getRatingsAvg(Rateable rateable) {
        return rateable.ratingsAvg();
    }

    @Override
    public void requestRide(String source, String dest) {
        RideRequest request = new RideRequest(
            this,
            source,
            dest
        );

        super.sysDatabase.systemRideRequests().addRequest(request);
        super.sysDatabase.notifyDrivers(request);
    }

    @Override
    public String getRequesterName() {
        return super.getUserInfo().getUsername();
    }

    @Override
    public String getRequesterId() {
        return super.getUserInfo().getId();
    }

    @Override
    public void addNotification(String notification) {
        this.notifications.add(notification);
    }

    @Override
    public ArrayList<String> getNotifications() {
        return this.notifications;
    }
}
