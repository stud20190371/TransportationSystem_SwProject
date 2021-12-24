package rideRequest;

import java.util.ArrayList;
import java.util.Date;

import discounts.Discount;
import enums.RideRequestStatus;
import interfaces.RideRequester;
import rideEvents.AcceptOfferEvent;
import rideEvents.ArriveToDestinationEvent;
import rideEvents.ArriveToSourceEvent;
import rideEvents.OfferEvent;
import rideEvents.RideEvent;

public class RideRequest {
    private String id;
    private RideRequester requester;
    private String source;
    private String dest; 
    private int passengersNum;
    private Date rideDate;
    private RideRequestStatus status;
    
    private ArrayList<RideEvent> offerEvents;
    private RideEvent acceptedOfferEvent;
    private RideEvent arriveSourceEvent;
    private RideEvent arriveDestEvent;

    private float ridePrice;
    private ArrayList<Discount> discounts;

    private static int rideRequestsCount = 0;

    public RideRequest(RideRequester requester, String source, String dest, int passengersNum) throws Exception {
        this.id = ("request"+(++rideRequestsCount));
        this.status = RideRequestStatus.SENT;
        this.requester = requester;
        this.source = source;
        this.dest = dest;
        this.passengersNum = passengersNum;
        this.rideDate = new Date();

        this.offerEvents = new ArrayList<>();
        this.discounts = new ArrayList<>();
    }

    public String getId(){
        return this.id;
    }

    public RideRequester getRequester(){
        return this.requester;
    }
    
    public String getSourceName(){
        return this.source;
    }

    public String getDestName(){
        return this.dest;
    }

    public String getRequesterName(){
        return requester.getRequesterName();
    }
    
    public int getPassengersNum(){
        return this.passengersNum;
    }

    public Date getRideDate(){
        return this.rideDate;
    }

    public boolean matchRequester(RideRequester requester){
        return (this.requester.getRequesterId() == requester.getRequesterId());
    }

    public void addEvent(RideEvent event) throws Exception {
        if(status.equals(RideRequestStatus.FINISHED))
        {
            throw new Exception("This Ride is already finished");
        }


        if(event instanceof OfferEvent)
        {
            if(!status.equals(RideRequestStatus.SENT))
            {
                throw new Exception("This Ride is already taken");

            }

            offerEvents.add(event);
        }
        else if(event instanceof AcceptOfferEvent)
        {
            if(!status.equals(RideRequestStatus.SENT))
            {
                throw new Exception("You have already accepted an offer");

            }

            acceptedOfferEvent = event;
            ((AcceptOfferEvent)acceptedOfferEvent).getAcceptedOffer().getOfferor().handleRequest(this);
            status = RideRequestStatus.WAITING;
        }
        else if(event instanceof ArriveToSourceEvent)
        {
            if(!status.equals(RideRequestStatus.WAITING))
            {
                throw new Exception("You can not start the ride");

            }

            arriveSourceEvent = event;
            status = RideRequestStatus.RUNNING;
            ridePrice = ((AcceptOfferEvent)arriveSourceEvent).getAcceptedOffer().getPrice();
            applyDiscounts();
        }
        else if(event instanceof ArriveToDestinationEvent)
        {
            if(!status.equals(RideRequestStatus.RUNNING))
            {
                throw new Exception("You can not finish the ride");

            }

            arriveDestEvent = event;
            this.requester.finishCurrentRequest();
            ((AcceptOfferEvent)arriveDestEvent).getAcceptedOffer().getOfferor().stopHandlingRequest();
            status = RideRequestStatus.FINISHED;
        }
    }

    public ArrayList<RideEvent> getEvents(){
        ArrayList<RideEvent> rideEvents = new ArrayList<>();
        
        rideEvents.addAll(offerEvents);

        if(acceptedOfferEvent != null){
            rideEvents.add(acceptedOfferEvent);
        }

        if(arriveSourceEvent != null){
            rideEvents.add(arriveSourceEvent);
        }

        if(arriveDestEvent != null){
            rideEvents.add(arriveDestEvent);
        }

        return rideEvents;
    }

    public void addDiscount(Discount discount){
        this.discounts.add(discount);
    }

    private void applyDiscounts(){
        for(Discount dis: this.discounts){
            this.ridePrice = dis.applyDiscount(this.ridePrice);
        }
    }

    @Override
    public String toString() {
        return 
                "\nrequester: " + requester.getRequesterName() + "\n" + 
                "source: " + source + "\n" + 
                "dest: " + dest + "\n";
    }
}
