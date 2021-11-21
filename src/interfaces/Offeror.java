package interfaces;

import rideRequest.Offer;
import rideRequest.RideRequest;

public interface Offeror {
    void offer(RideRequest request, Offer offer);
    String getOfferorName();
}
