package rideEvents;

import interfaces.Offerable;
import rideRequest.Offer;

public class AcceptOfferEvent extends RideEvent{
    private Offer acceptedOffer;
    private Offerable offerable;

    public AcceptOfferEvent(Offerable offerable, Offer acceptedOffer){
        super("Passenger accepts the captain price");
        this.offerable = offerable;
        this.acceptedOffer = acceptedOffer;
    }

    public Offer getAcceptedOffer(){
        return this.acceptedOffer;
    }

    @Override
    public void displayEvent() {
        System.out.println("Event Name: " + this.getEventName());
        System.out.println("Event Date: " + this.getEventDate());
        System.out.println("Passenger Name: " + this.offerable.getOfferableName());
    }
}
