package interfaces;

import rideRequest.RideRequest;

public interface RideRequester {
    void requestRide(String source, String dest);
    void deleteRequest(RideRequest request);
}
