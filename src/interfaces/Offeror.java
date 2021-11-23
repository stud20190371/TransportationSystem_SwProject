package interfaces;

import rideRequest.RideRequest;

public interface Offeror {
    void offer(RideRequest request, float price);
    String getOfferorName();
}
