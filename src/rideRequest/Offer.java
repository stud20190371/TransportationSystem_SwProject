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

    public String getOfferorName(){
        return offeror.getOfferorName();
    }

    public float getPrice(){
        return this.price;
    } 

    public void setPrice(float price){
        this.price = price;
    }

    @Override
    public String toString() {
        return "\n{\n" +
                " id: " + id + "\n" +
                " offeror: " + offeror.getOfferorName() + "\n" + 
                " price: " + price + "\n" + 
                "}\n";
    }
}
