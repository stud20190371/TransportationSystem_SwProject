package rideRequest;

import java.util.ArrayList;

import interfaces.RideRequester;

public class RideRequest {
    private String id;
    private RideRequester requester;
    private String source;
    private String dest;
    private ArrayList<Offer> offers;

    private static int rideRequestsCount = 0;

    public RideRequest(RideRequester requester, String source, String dest){
        this.id = ("request"+(++rideRequestsCount));
        this.requester = requester;
        this.source = source;
        this.dest = dest;
        this.offers = new ArrayList<>();
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
    
    public boolean matchRequester(RideRequester requester){
        return (this.requester.getRequesterId() == requester.getRequesterId());
    }

    public void addOffer(Offer offer){
        this.offers.add(offer);
    }

    public void deleteOffer(Offer offer){
        this.offers.remove(offer);
    }

    public void updateOffer(Offer offer, float newPrice){
    }

    public ArrayList<Offer> getOffers(){
        return this.offers;
    }

    @Override
    public String toString() {
        return "\n{\n" +
                " id: " + id + "\n" + 
                " requester: " + requester.getRequesterName() + "\n" + 
                " source: " + source + "\n" + 
                " dest: " + dest + "\n" + 
                " offers: " + offers + "\n" + 
                "}\n";
    }
}
