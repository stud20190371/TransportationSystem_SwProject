package rideEvents;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @JsonIgnore
    public Offer getAcceptedOffer(){
        return this.acceptedOffer;
    }

    public String getPassengerName(){
        return this.offerable.getOfferableName();
    }

    @Override
    public String toString() {
        return 
            "\n{\n" + 
                " event name: " + this.getEventName() + "\n" + 
                " event date: " + this.getEventDate() + "\n" + 
                " passenger name: " + this.offerable.getOfferableName() + "\n" + 
            "}";
    }
}
