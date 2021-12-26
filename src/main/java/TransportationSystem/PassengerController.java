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

import authentication.PassengerAuthValidator;
import common.CommonSection;
import enums.AuthFieldName;
import interfaces.Rater;
import rating.Rate;
import rideRequest.Offer;
import rideRequest.RideRequest;
import user.Driver;
import user.Passenger;

class PassengerSignupInfo{
    public String username;
    public String mobileNumber;
    public String email;
    public String password;
    public String birthdate;
}

@RestController
@RequestMapping("/passenger")
public class PassengerController {
    private void checkAuthentication() throws Exception {
        if(!CommonSection.authenticator.isUserAuthenticated() || CommonSection.authenticatedUser == null){
            throw new Exception("You aren't authenticated!");
        }

        if(!(CommonSection.authenticatedUser instanceof Passenger)){
            throw new Exception("You aren't a passenger!");
        }
    }

    @PostMapping("/signup")
    public void signup(@RequestBody PassengerSignupInfo info) throws Exception{
        CommonSection.authenticator.setAuthValidator(new PassengerAuthValidator());

        CommonSection.inputsValidator.checkValidation(AuthFieldName.USERNAME, info.username);
        CommonSection.inputsValidator.checkValidation(AuthFieldName.MOBILE_NUMBER, info.mobileNumber);
        CommonSection.inputsValidator.checkValidation(AuthFieldName.EMAIL, info.email);
        CommonSection.inputsValidator.checkValidation(AuthFieldName.PASSWORD, info.password);
        CommonSection.inputsValidator.checkValidation(AuthFieldName.BIRTHDATE, info.birthdate);

        HashMap<AuthFieldName, String> userInfo = new HashMap<>();
        userInfo.put(AuthFieldName.USERNAME, info.username);
        userInfo.put(AuthFieldName.MOBILE_NUMBER, info.mobileNumber);
        userInfo.put(AuthFieldName.EMAIL, info.email);
        userInfo.put(AuthFieldName.PASSWORD, info.password);
        userInfo.put(AuthFieldName.BIRTHDATE, info.birthdate);

        CommonSection.authenticatedUser = CommonSection.authenticator.signup(userInfo);
    }

    @PostMapping("/login")
    public void login(@RequestParam String username, @RequestParam String password) throws Exception{
        CommonSection.authenticator.setAuthValidator(new PassengerAuthValidator());

        CommonSection.authenticatedUser = CommonSection.authenticator.login(username, password);
    }

    @PostMapping("/request-ride")
    public void requestRide(@RequestParam String source, @RequestParam String dest, @RequestParam int passengersNum) throws Exception{
        checkAuthentication();

        ((Passenger)CommonSection.authenticatedUser).requestRide(source, dest, passengersNum);
    }

    public RideRequest getCurrentRequest() throws Exception{
        checkAuthentication();

        return ((Passenger)CommonSection.authenticatedUser).getCurrentRequest();
    }

    @GetMapping("/current-ride-request")
    public RideRequest currentRequest() throws Exception{
        RideRequest currentRequest = getCurrentRequest();

        return currentRequest;
    }

    public ArrayList<Offer> getCurrentRequestOffers() throws Exception{
        RideRequest currentRequest = getCurrentRequest();

        if(currentRequest == null){
            throw new Exception("You don't have a ride request");
        }

        return currentRequest.getOffers();
    }

    @GetMapping("/current-ride-request/offers")
    public ArrayList<Offer> currentRequestOffers() throws Exception{
        ArrayList<Offer> offers = getCurrentRequestOffers();

        return offers;
    }

    @PostMapping("/current-ride-request/offers/{id}/accept")
    public void acceptOffer(@PathVariable String id) throws Exception{
        ArrayList<Offer> currentRideRequestOffers = getCurrentRequestOffers();

        Offer offer = null;

        for(Offer o: currentRideRequestOffers){
            if(o.getId().equals(id)){
                offer = o;
                break;
            }
        }

        if(offer == null){
            throw new Exception("There's no offer with id = " + id);
        }

        ((Passenger)CommonSection.authenticatedUser).acceptOffer(offer);
    }

    @GetMapping("/drivers")
    public ArrayList<Driver> drivers() throws Exception{
        checkAuthentication();

        ArrayList<Driver> drivers = CommonSection.sysDatabase.getSystemDrivers();

        return drivers;
    }

    @GetMapping("/drivers/{id}/avg-ratings")
    public String driverAvgRatings(@PathVariable String id) throws Exception{
        checkAuthentication();

        Driver driver = CommonSection.sysDatabase.getDriver(id);

        if(driver == null){
            throw new Exception("There's no driver with id = " + id);
        }

        return driver.ratingsAvg();
    }

    @PostMapping("/drivers/{id}/rate")
    public void rateDriver(@PathVariable String id, @RequestParam float rating) throws Exception{
        checkAuthentication();

        Driver driver = CommonSection.sysDatabase.getDriver(id);

        if(driver == null){
            throw new Exception("There's no driver with id = " + id);
        }

        Rater rater = ((Passenger)CommonSection.authenticatedUser);

        Rate rate = new Rate(rater, driver, rating);

        rater.rate(driver, rate);
    }

    @GetMapping("/notifications")
    public ArrayList<String> notifications() throws Exception{
        checkAuthentication();
        
        return ((Passenger)CommonSection.authenticatedUser).getNotifications();
    }

    @PostMapping("/logout")
    public void logout() throws Exception{
        checkAuthentication();

        CommonSection.authenticatedUser = null;
        CommonSection.authenticator.logout();
    }
}
