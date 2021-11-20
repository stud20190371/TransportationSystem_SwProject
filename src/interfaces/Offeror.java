package interfaces;

import rideRequest.Offer;
import rideRequest.RideRequest;

public interface Offeror {
    void offer(RideRequest request, Offer offer);
    void deleteOffer(RideRequest request, Offer offer);
    void updateOffer(RideRequest request, Offer offer, float newPrice);
}
