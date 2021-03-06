package user;

import java.text.DecimalFormat;
import java.util.ArrayList;

import interfaces.*;
import rating.Rate;
import rideRequest.*;

public class Driver extends User implements Verifiable, Rateable, Offeror, Suspendable, Notifiable{
    private String drivingLicence;
    private String nationalID;
    private boolean verified;
    private boolean suspensionState = false;
    private ArrayList<String> favoriteAreas;
    private ArrayList<Rate> rates;
    private ArrayList<String> notifications;
    private static int driversCount = 0;
    
    public Driver(String username, String mobileNum, String email, String password, String drivingLicence, String nationalID){
        super(("driver"+(++driversCount)), username, mobileNum, email, password);
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

    public ArrayList<Rate> getRatings(){ return this.rates; }

    public void setNationID(String id){
        this.nationalID = id;
    }

    public void addArea(String area){
        this.favoriteAreas.add(area);
    }

    public int searchArea(String areaName){
        return this.favoriteAreas.indexOf(areaName);
    }

    @Override
    public void addRate(Rate rate) {
        this.rates.add(rate);
    }
    
    @Override
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
    public String getRateableName() {
        return super.getUserInfo().getUsername();
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
    public void changeSuspensionState(boolean state) {
        this.suspensionState = state;
    }

    @Override
    public boolean isSuspended() {
        return this.suspensionState;
    }

    @Override
    public void offer(RideRequest request, float price) {
        Offer offer = new Offer(this, price);
        request.addOffer(offer);

        super.sysDatabase.notifyPassenger(((Passenger)request.getRequester()), offer);
    }

    @Override
    public String getOfferorName() {
        return super.getUserInfo().getUsername();
    }

    @Override
    public void addNotification(String notification) {
        this.notifications.add(notification);
    }

    @Override
    public ArrayList<String> getNotifications() {
        return this.notifications;
    }
    
    public ArrayList<String> getFavoriteAreas(){
    	return this.favoriteAreas;
    }
    
    @Override
    public String toString() {
        return 
            "\nusername: " + super.getUserInfo().getUsername() + "\n" + 
            (super.getUserInfo().getEmail() != null ? ("email: " + super.getUserInfo().getEmail() + "\n") : "") +
            "mobile number: " + super.getUserInfo().getMobileNumber() + "\n" + 
            "national id: " + nationalID + "\n" + 
            "driving license: " + drivingLicence + "\n" +
            (rates.size() > 0 ? (ratingsAvg() + "\n") : "");
    }
}
