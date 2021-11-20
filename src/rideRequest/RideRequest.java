package rideRequest;

import java.util.ArrayList;

import interfaces.RideRequester;

public class RideRequest {
    RideRequester requester;
    String source;
    String dest;
    ArrayList<Offer> offers;

    RideRequest(RideRequester requester, String source, String dest){
        this.requester = requester;
        this.source = source;
        this.dest = dest;
        this.offers = new ArrayList<>();
    }

    public void addOffer(Offer offer){
        this.offers.add(offer);
    }

    public void deleteOffer(Offer offer){
        this.offers.remove(offer);
    }

    public void updateOffer(Offer offer, float newPrice){
    }
}
