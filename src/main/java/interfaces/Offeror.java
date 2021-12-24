package interfaces;

import rideRequest.RideRequest;

public interface Offeror {
    void offer(RideRequest request, float price) throws Exception;
    String getOfferorName();
    void handleRequest(RideRequest request);
    void stopHandlingRequest();
    boolean isHandlingRide();
}
