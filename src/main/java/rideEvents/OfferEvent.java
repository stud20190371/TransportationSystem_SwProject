package rideEvents;

import rideRequest.Offer;

public class OfferEvent extends RideEvent{
    private Offer offer;

    public OfferEvent(Offer offer){
        super("Captain put a price to the ride");
        this.offer = offer;
    }

    public Offer getOffer(){
        return this.offer;
    }

    @Override
    public void displayEvent() {
        System.out.println("Event Name: " + this.getEventName());
        System.out.println("Event Date: " + this.getEventDate());
        System.out.println("Captain Name: " + this.offer.getOfferorName());
        System.out.println("Price: " + this.offer.getPrice());
    }   
}
