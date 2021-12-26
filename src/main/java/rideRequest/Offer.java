package rideRequest;

import com.fasterxml.jackson.annotation.JsonIgnore;

import interfaces.Offeror;

public class Offer {
    private String id;
    private Offeror offeror;
    private float price;
    private static int offersCount = 0;

    public Offer(Offeror offeror, float price){
        this.id = ("offer"+(++offersCount));
        this.offeror = offeror;
        this.price = price;
    }

    public String getId(){
        return this.id;
    }

    public String getOfferorName(){
        return offeror.getOfferorName();
    }

    @JsonIgnore

    public Offeror getOfferor(){
        return this.offeror;
    }

    public float getPrice(){
        return this.price;
    } 

    public void setPrice(float price){
        this.price = price;
    }

    @Override
    public String toString() {
        return 
            "\n{\n" +
                " id: " + id + "\n" + 
                " offeror: " + offeror.getOfferorName() + "\n" + 
                " price: " + price + "\n" +
            "}";
    }
}
