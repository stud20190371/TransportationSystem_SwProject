package interfaces;

import rideRequest.Offer;

public interface Offerable {
    void acceptOffer(Offer offer) throws Exception;
    String getOfferableName();
}
