package rideEvents;

import com.fasterxml.jackson.annotation.JsonIgnore;

import rideRequest.Offer;

public class OfferEvent extends RideEvent{
    private Offer offer;

    public OfferEvent(Offer offer){
        super("Captain put a price to the ride");
        this.offer = offer;
    }

    @JsonIgnore
    public Offer getOffer(){
        return this.offer;
    }

    public String getCaptainName(){
        return this.offer.getOfferorName();
    }

    public float getPrice(){
        return this.offer.getPrice();
    }

    @Override
    public String toString() {
        return 
            "\n{\n" + 
                " event name: " + this.getEventName() + "\n" + 
                " event date: " + this.getEventDate() + "\n" + 
                " captain name: " + this.offer.getOfferorName() + "\n" + 
                " price: " + this.offer.getPrice() + "\n" + 
            "}";
    }
}
