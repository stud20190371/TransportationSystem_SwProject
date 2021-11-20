package rideRequest;

import interfaces.Offeror;

public class Offer {
    Offeror offeror;
    float price;

    Offer(Offeror offeror, float price){
        this.offeror = offeror;
        this.price = price;
    }

    public float getPrice(){
        return this.price;
    } 

    public void setPrice(float price){
        this.price = price;
    }
}
