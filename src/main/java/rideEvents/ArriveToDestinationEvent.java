package rideEvents;

import interfaces.Offerable;
import interfaces.Offeror;

public class ArriveToDestinationEvent extends RideEvent{
    private Offeror captain;
    private Offerable passenger;

    public ArriveToDestinationEvent(Offeror captain, Offerable passenger){
        super("Captain arrived to passenger destination");
        this.captain = captain;
        this.passenger = passenger;
    }

    @Override
    public void displayEvent() {
        System.out.println("Event Name: " + this.getEventName());
        System.out.println("Event Date: " + this.getEventDate());
        System.out.println("Captain Name: " + this.captain.getOfferorName());
        System.out.println("Passenger Name: " + this.passenger.getOfferableName());
    }
}
