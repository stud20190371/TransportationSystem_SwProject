package interfaces;

import java.util.ArrayList;
import java.util.Date;

import rideRequest.RideRequest;

public interface RideRequester {
    void requestRide(String source, String dest, int passengerNum) throws Exception ;
    String getRequesterName();
    String getRequesterId();
    Date getRequesterBirthdate();
    RideRequest getCurrentRequest();
    void finishCurrentRequest();
    ArrayList<RideRequest> getRideRequestsHistory();
}
