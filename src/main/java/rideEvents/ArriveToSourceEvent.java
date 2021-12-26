package rideEvents;

import interfaces.Offerable;
import interfaces.Offeror;

public class ArriveToSourceEvent extends RideEvent {
    private Offeror captain;
    private Offerable passenger;

    public ArriveToSourceEvent(Offeror captain, Offerable passenger){
        super("Captain arrived to passenger location");
        this.captain = captain;
        this.passenger = passenger;
    }

    public String getCaptainName(){
        return this.captain.getOfferorName();
    }

    public String getPassengerName(){
        return this.passenger.getOfferableName();
    }

    @Override
    public String toString() {
        return 
            "\n{\n" + 
                " event name: " + this.getEventName() + "\n" + 
                " event date: " + this.getEventDate() + "\n" + 
                " captain name: " + this.captain.getOfferorName() + "\n" + 
                " passenger name: " + this.passenger.getOfferableName() + "\n" + 
            "}";
    }
}
