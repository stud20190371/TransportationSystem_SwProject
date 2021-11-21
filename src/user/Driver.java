package user;

import java.util.ArrayList;
import java.util.List;

import interfaces.Notifiable;
import interfaces.Offeror;
import interfaces.Rateable;
import interfaces.Suspendable;
import interfaces.Verifiable;
import rating.Rate;
import rideRequest.Offer;
import rideRequest.RideRequest;

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

    public void setNationID(String id){
        this.nationalID = id;
    }

    public void addArea(String area){
        this.favoriteAreas.add(area);
    }

    public void addAreas(List<String> areas){
        for(String area: areas){
            this.addArea(area);
        }
    }

    public void removeArea(String area){
        this.favoriteAreas.remove(area);
    }

    @Override
    public void addRate(Rate rate) {
        this.rates.add(rate);
    }
    
    @Override
    public void removeRate(Rate rate) {
        
    }

    @Override
    public void updateRate(Rate rate, float newRate) {
        
    }

    @Override
    public float ratingsAvg() {
        float ratingsAvg = 0;

        for(Rate rate: this.rates){
            ratingsAvg += rate.getRate();
        }

        return (float) ratingsAvg/this.rates.size();
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
    public void offer(RideRequest request, Offer offer) {
        
    }

    @Override
    public void deleteOffer(RideRequest request, Offer offer) {
        
    }

    @Override
    public void updateOffer(RideRequest request, Offer offer, float newPrice) {
        
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
