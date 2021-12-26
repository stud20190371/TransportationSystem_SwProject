package user;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import interfaces.Notifiable;
import interfaces.Offerable;
import interfaces.Rateable;
import interfaces.Rater;
import interfaces.RideRequester;
import interfaces.Suspendable;
import rating.Rate;
import rideEvents.AcceptOfferEvent;
import rideRequest.Offer;
import rideRequest.RideRequest;

public class Passenger extends User implements Suspendable, Rater, RideRequester, Offerable, Notifiable{
    private boolean suspensionState = false;
    private ArrayList<String> notifications;
    private RideRequest currentRequest;
    private ArrayList<RideRequest> requestsHistory;

    private static int passengersCount = 0;


    public Passenger(String username, String mobileNum, String email, String password, Date birthdate){
        super(("passenger"+(++passengersCount)), username, mobileNum, email, password, birthdate);
        this.notifications = new ArrayList<>();
        this.requestsHistory = new ArrayList<>();
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
    @JsonIgnore
    public String getSuspendableId(){
        return super.getUserInfo().getId();   
    }

    @Override
    public void rate(Rateable rateable, Rate rate) {
        rateable.addRate(rate);
    }

    @Override
    @JsonIgnore
    public String getRatingsAvg(Rateable rateable) {
        return rateable.ratingsAvg();
    }

    @Override
    @JsonIgnore
    public String getRaterName() {
        return super.getUserInfo().getUsername();
    }

    @Override
    public void requestRide(String source, String dest, int passengerNum) throws Exception {
        if(currentRequest != null)
        {
            throw new Exception("You already have a ride request");
        }

        RideRequest request = new RideRequest(
            this,
            source,
            dest,
            passengerNum
        );

        super.sysDatabase.addRideRequest(request);
        super.sysDatabase.notifyDrivers(request);

        this.currentRequest = request;
    }

    @Override
    @JsonIgnore
    public String getRequesterName() {
        return super.getUserInfo().getUsername();
    }

    @Override
    @JsonIgnore
    public String getRequesterId() {
        return super.getUserInfo().getId();
    }

    @Override
    @JsonIgnore
    public Date getRequesterBirthdate() {
        return super.getUserInfo().getBirtdate();
    }

    @Override
    @JsonIgnore
    public RideRequest getCurrentRequest() {
        return this.currentRequest;
    }

    @Override
    public void finishCurrentRequest() {
        if(this.currentRequest != null){
            this.requestsHistory.add(this.currentRequest);
            this.currentRequest = null;
        }
    }

    @Override
    @JsonIgnore
    public ArrayList<RideRequest> getRideRequestsHistory() {
        return this.requestsHistory;
    }

    @Override
    public void acceptOffer(Offer offer) throws Exception {
        this.currentRequest.addEvent(new AcceptOfferEvent(this, offer));
    }

    @Override
    @JsonIgnore
    public String getOfferableName() {
        return super.getUserInfo().getUsername();
    } 

    @Override
    public void addNotification(String notification) {
        this.notifications.add(notification);
    }

    @Override
    @JsonIgnore
    public ArrayList<String> getNotifications() {
        return this.notifications;
    }
}
