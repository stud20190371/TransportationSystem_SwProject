package interfaces;

public interface RideRequester {
    void requestRide(String source, String dest);
    String getRequesterName();
    String getRequesterId();
}
