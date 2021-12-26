package TransportationSystem;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import authentication.DriverAuthValidator;
import common.CommonSection;
import enums.AuthFieldName;
import interfaces.Offerable;
import interfaces.Offeror;
import rating.Rate;
import rideEvents.ArriveToDestinationEvent;
import rideEvents.ArriveToSourceEvent;
import rideRequest.RideRequest;
import user.Driver;
import user.Passenger;

class DriverSignupInfo{
    public String username;
    public String mobileNumber;
    public String email;
    public String password;
    public String birthdate;
    public String drivingLicence;
    public String nationalID;
}

@RestController
@RequestMapping("/driver")
public class DriverController {
    private void checkAuthentication() throws Exception {
        if(!CommonSection.authenticator.isUserAuthenticated() || CommonSection.authenticatedUser == null){
            throw new Exception("You aren't authenticated!");
        }

        if(!(CommonSection.authenticatedUser instanceof Driver)){
            throw new Exception("You aren't a driver!");
        }
    }

    @PostMapping("/signup")
    public void signup(@RequestBody DriverSignupInfo info) throws Exception{
        CommonSection.authenticator.setAuthValidator(new DriverAuthValidator());

        CommonSection.inputsValidator.checkValidation(AuthFieldName.USERNAME, info.username);
        CommonSection.inputsValidator.checkValidation(AuthFieldName.MOBILE_NUMBER, info.mobileNumber);
        CommonSection.inputsValidator.checkValidation(AuthFieldName.EMAIL, info.email);
        CommonSection.inputsValidator.checkValidation(AuthFieldName.PASSWORD, info.password);
        CommonSection.inputsValidator.checkValidation(AuthFieldName.BIRTHDATE, info.birthdate);
        CommonSection.inputsValidator.checkValidation(AuthFieldName.DRIVING_LICENCE, info.drivingLicence);
        CommonSection.inputsValidator.checkValidation(AuthFieldName.NATIONAL_ID, info.nationalID);

        HashMap<AuthFieldName, String> userInfo = new HashMap<>();
        userInfo.put(AuthFieldName.USERNAME, info.username);
        userInfo.put(AuthFieldName.MOBILE_NUMBER, info.mobileNumber);
        userInfo.put(AuthFieldName.EMAIL, info.email);
        userInfo.put(AuthFieldName.PASSWORD, info.password);
        userInfo.put(AuthFieldName.BIRTHDATE, info.birthdate);
        userInfo.put(AuthFieldName.DRIVING_LICENCE, info.drivingLicence);
        userInfo.put(AuthFieldName.NATIONAL_ID, info.nationalID);

        CommonSection.authenticatedUser = CommonSection.authenticator.signup(userInfo);
    }

    @PostMapping("/login")
    public void login(@RequestParam String username, @RequestParam String password) throws Exception{
        CommonSection.authenticator.setAuthValidator(new DriverAuthValidator());

        CommonSection.authenticatedUser = CommonSection.authenticator.login(username, password);
    }

    @GetMapping("/favorite-areas")
    public ArrayList<String> favoriteAreas() throws Exception{
        checkAuthentication();
        
        return ((Driver)CommonSection.authenticatedUser).getFavoriteAreas();
    }

    @PostMapping("/favorite-areas/add")
    public void addFavoriteAreas(@RequestParam String area) throws Exception{
        checkAuthentication();
        
        ((Driver)CommonSection.authenticatedUser).addArea(area);;
    }

    public ArrayList<RideRequest> getRideRequests() throws Exception{
        checkAuthentication();

        return ((Driver)CommonSection.authenticatedUser).getMatchedRideRequests();
    }

    @GetMapping("/ride-requests")
    public ArrayList<RideRequest> rideRequests() throws Exception{
        ArrayList<RideRequest> rideRequests = getRideRequests();

        return rideRequests;
    }

    @PostMapping("/ride-requests/{id}/offer")
    public void offerRide(@PathVariable String id, @RequestParam float price) throws Exception{
        ArrayList<RideRequest> matchedRideRequests = getRideRequests();

        RideRequest request = null;

        for(RideRequest req: matchedRideRequests){
            if(req.getId().equals(id)){
                request = req;
                break;
            }
        }

        if(request == null){
            throw new Exception("There's no matched ride request with id = " + id);
        }

        ((Driver)CommonSection.authenticatedUser).offer(request, price);
    }   

    @GetMapping("/ratings")
    public ArrayList<Rate> ratings() throws Exception{
        checkAuthentication();

        ArrayList<Rate> ratings = ((Driver)CommonSection.authenticatedUser).getRatings();

        return ratings;
    }

    public RideRequest getCurrentRide() throws Exception{
        checkAuthentication();

        return ((Driver)CommonSection.authenticatedUser).currentRide();
    }

    @GetMapping("/current-ride")
    public RideRequest currentRide() throws Exception{
        RideRequest currentRide = getCurrentRide();

        return currentRide;
    }

    @PostMapping("/current-ride/arrived-to-source")
    public void arrivedSource() throws Exception{
        RideRequest currentRide = getCurrentRide();

        if(currentRide == null){
            throw new Exception("You don't handle a ride now");
        }

        Offeror captain = ((Driver)CommonSection.authenticatedUser);
        Offerable passenger = (Passenger)currentRide.getRequester();

        currentRide.addEvent(new ArriveToSourceEvent(captain, passenger));
    }

    @PostMapping("/current-ride/arrived-to-destination")
    public void arrivedDest() throws Exception{
        RideRequest currentRide = getCurrentRide();

        if(currentRide == null){
            throw new Exception("You don't handle a ride now");
        }

        Offeror captain = ((Driver)CommonSection.authenticatedUser);
        Offerable passenger = (Passenger)currentRide.getRequester();

        currentRide.addEvent(new ArriveToDestinationEvent(captain, passenger));
    }

    @GetMapping("/notifications")
    public ArrayList<String> notifications() throws Exception{
        checkAuthentication();
        
        return ((Driver)CommonSection.authenticatedUser).getNotifications();
    }

    @PostMapping("/logout")
    public void logout() throws Exception{
        checkAuthentication();

        CommonSection.authenticatedUser = null;
        CommonSection.authenticator.logout();
    }
}
