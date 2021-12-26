package database;

import java.util.ArrayList;

import rideRequest.RideRequest;

public class SystemRideRequests {
    private ArrayList<RideRequest> rideRequests;
    private static SystemRideRequests sysRideRequestsInstance = null;

    private SystemRideRequests(){
        this.rideRequests = new ArrayList<>();
    }

    public static SystemRideRequests createInstance(){
        if(sysRideRequestsInstance == null){
            sysRideRequestsInstance = new SystemRideRequests();
        }

        return sysRideRequestsInstance;
    }

    public ArrayList<RideRequest> getRideRequests(){
        return this.rideRequests;
    }

    public RideRequest getRideRequest(String id){
        for(RideRequest req: rideRequests){
            if(req.getId().equals(id)){
                return req;
            }
        }

        return null;
    }

    public void addRequest(RideRequest request){
        this.rideRequests.add(request);
    }

}
