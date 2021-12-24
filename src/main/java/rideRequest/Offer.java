package rideRequest;

import interfaces.Offeror;

public class Offer {
    private String id;
    private Offeror offeror;
    private float price;
    private static int offersCount = 0;

    public Offer(Offeror offeror, float price){
        this.id = ("request"+(++offersCount));
        this.offeror = offeror;
        this.price = price;
    }

    public String getId(){
        return this.id;
    }

    public String getOfferorName(){
        return offeror.getOfferorName();
    }

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
                "\nofferor: " + offeror.getOfferorName() + "\n" + 
                "price: " + price + "\n"; 
    }
}
