package user;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import interfaces.*;
import rating.Rate;
import rideEvents.OfferEvent;
import rideRequest.*;

public class Driver extends User implements Verifiable, Rateable, Offeror, Suspendable, Notifiable{
    private String drivingLicence;
    private String nationalID;
    private boolean verified;
    private boolean suspensionState = false;

    private ArrayList<String> favoriteAreas;
    private ArrayList<Rate> rates;
    private ArrayList<String> notifications; 
    private RideRequest currentRide;
    
    private static int driversCount = 0;
    
    public Driver(String username, String mobileNum, String email, String password, Date birthdate, String drivingLicence, String nationalID){
        super(("driver"+(++driversCount)), username, mobileNum, email, password, birthdate);
        this.drivingLicence = drivingLicence;
        this.nationalID = nationalID;
        this.favoriteAreas = new ArrayList<>();
        this.rates = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    public String getDrivingLicense(){
        return this.drivingLicence;
    }

    public void setDrivingLicense(String license){
        this.drivingLicence = license;
    }

    public String getNationID(){
        return this.nationalID;
    }

    @JsonIgnore
    public ArrayList<Rate> getRatings(){ 
        return this.rates; 
    }

    public void setNationID(String id){
        this.nationalID = id;
    }

    public void addArea(String area){
        this.favoriteAreas.add(area.toLowerCase());
    }

    public int searchArea(String areaName){
        return this.favoriteAreas.indexOf(areaName.toLowerCase());
    }

    @JsonIgnore 
    public ArrayList<String> getFavoriteAreas(){
    	return this.favoriteAreas;
    }

    @JsonIgnore 
    public ArrayList<RideRequest> getMatchedRideRequests(){
        return super.sysDatabase.getMatchedRideRequestsForDriver(this);
    }

    @Override
    public void changeVerificationState(boolean state) {
        this.verified = state;
    }

    @Override
    public boolean isVerified() {
        return this.verified;
    }

    @Override
    @JsonIgnore 
    public String getVerifiableId(){
        return super.getUserInfo().getId();
    }

    @Override
    public void addRate(Rate rate) {
        this.rates.add(rate);
    }
    
    @Override
    @JsonIgnore
    public String ratingsAvg() {
        float ratingsAvg = 0;

        if(this.rates.size() <= 0){
            return "There're no ratings";
        }

        for(Rate rate: this.rates){
            ratingsAvg += rate.getRate();
        }

        return "Average ratings: " + (new DecimalFormat("#.##")).format(((float) ratingsAvg/this.rates.size())) + " (" + this.rates.size() + ")";
    }

    @Override
    @JsonIgnore 
    public String getRateableName() {
        return super.getUserInfo().getUsername();
    }

    @Override
    public void offer(RideRequest request, float price) throws Exception {
        Offer offer = new Offer(this, price);
        request.addEvent(new OfferEvent(offer));

        super.sysDatabase.notifyPassenger(((Passenger)request.getRequester()), offer);
    }

    @Override
    @JsonIgnore 
    public String getOfferorName() {
        return super.getUserInfo().getUsername();
    }

    @Override
    public void handleRequest(RideRequest request) {
        if(this.currentRide == null){
            this.addNotification("Passenger (" + request.getRequesterName() + ") accept your offer");
            this.currentRide = request;
        }
    }

    @Override
    public void stopHandlingRequest() {
        this.currentRide = null;
    }

    @Override
    @JsonIgnore 
    public boolean isHandlingRide() {
        return this.currentRide != null;
    }

    @Override
    @JsonIgnore
    public RideRequest currentRide() {
        return this.currentRide;
    }

    @Override
    public void changeSuspensionState(boolean state) {
        this.suspensionState = state;
    }

    @Override
    public boolean isSuspended() {
        return this.suspensionState;
    }

    @Override
    @JsonIgnore 
    public String getSuspendableId(){
        return super.getUserInfo().getId();   
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
    
    @Override
    public String toString() {
        return 
            "\n{\n" + 
                " id: " + super.getUserInfo().getId() + "\n" + 
                " username: " + super.getUserInfo().getUsername() + "\n" + 
                (super.getUserInfo().getEmail() != null ? (" email: " + super.getUserInfo().getEmail() + "\n") : "") +
                " mobile number: " + super.getUserInfo().getMobileNumber() + "\n" + 
                " birthdate: " + super.getUserInfo().getBirtdate() + "\n" +
                " national id: " + nationalID + "\n" + 
                " driving license: " + drivingLicence + "\n" +
            "}";
    }
}
