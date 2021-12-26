package TransportationSystem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import authentication.AdminAuthValidator;
import common.CommonSection;
import interfaces.Suspendable;
import interfaces.Verifiable;
import rideEvents.RideEvent;
import rideRequest.RideRequest;
import user.Admin;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private void checkAuthentication() throws Exception {
        if(!CommonSection.authenticator.isUserAuthenticated() || CommonSection.authenticatedUser == null){
            throw new Exception("You aren't authenticated!");
        }

        if(!(CommonSection.authenticatedUser instanceof Admin)){
            throw new Exception("You aren't an admin!");
        }
    }

    @PostMapping("/login")
    public void login(@RequestParam String username, @RequestParam String password) throws Exception{
        CommonSection.authenticator.setAuthValidator(new AdminAuthValidator());

        CommonSection.authenticatedUser = CommonSection.authenticator.login(username, password);
    } 

    public ArrayList<Verifiable> getPendingDrivers() throws Exception{
        checkAuthentication();

        return ((Admin)CommonSection.authenticatedUser).getVerificationRequests();
    }

    @GetMapping("/pending-drivers")
    public ArrayList<Verifiable> pendingDrivers() throws Exception{
        ArrayList<Verifiable> pendingDrivers = getPendingDrivers();

        return pendingDrivers;
    }

    @PostMapping("/pending-drivers/{id}/verify")
    public void verify(@PathVariable String id) throws Exception{
        ArrayList<Verifiable> pendingDrivers = getPendingDrivers();

        Verifiable verifiable = null;

        for(Verifiable v: pendingDrivers){
            if(v.getVerifiableId().equals(id)){
                verifiable = v;
                break;
            }
        }

        if(verifiable == null){
            throw new Exception("There's no pending driver with id = " + id);
        }

        ((Admin)CommonSection.authenticatedUser).verify(verifiable);
    }

    public ArrayList<Suspendable> getUnsuspendedUsers() throws Exception{
        checkAuthentication();

        ArrayList<Suspendable> unsuspendedUsers = new ArrayList<>();

        for(Suspendable driver: CommonSection.sysDatabase.getSystemDrivers()){
            if(!driver.isSuspended()){
                unsuspendedUsers.add(driver);
            }
        }

        for(Suspendable passenger: CommonSection.sysDatabase.getSystemPassengers()){
            if(!passenger.isSuspended()){
                unsuspendedUsers.add(passenger);
            }
        }

        return unsuspendedUsers;
    }

    @GetMapping("/unsuspended-users")
    public ArrayList<Suspendable> unsuspendedUsers() throws Exception{
        ArrayList<Suspendable> unsuspendedUsers = getUnsuspendedUsers();

        return unsuspendedUsers;
    }

    @PostMapping("/unsuspended-users/{id}/suspend")
    public void suspendUser(@PathVariable String id) throws Exception{
        ArrayList<Suspendable> unsuspendedUsers = getUnsuspendedUsers();

        Suspendable user = null;

        for(Suspendable s: unsuspendedUsers){
            if(s.getSuspendableId().equals(id)){
                user = s;
                break;
            }
        }

        if(user == null){
            throw new Exception("There's no unsuspended user with id = " + id);
        }

        ((Admin)CommonSection.authenticatedUser).suspend(user);
    }

    public ArrayList<Suspendable> getSuspendedUsers() throws Exception{
        checkAuthentication();

        ArrayList<Suspendable> suspendedUsers = new ArrayList<>();

        for(Suspendable driver: CommonSection.sysDatabase.getSystemDrivers()){
            if(driver.isSuspended()){
                suspendedUsers.add(driver);
            }
        }

        for(Suspendable passenger: CommonSection.sysDatabase.getSystemPassengers()){
            if(passenger.isSuspended()){
                suspendedUsers.add(passenger);
            }
        }

        return suspendedUsers;
    }

    @GetMapping("/suspended-users")
    public ArrayList<Suspendable> suspendedUsers() throws Exception{
        ArrayList<Suspendable> suspendedUsers = getSuspendedUsers();

        return suspendedUsers;
    }

    @PostMapping("/suspended-users/{id}/unsuspend")
    public void unsuspendUser(@PathVariable String id) throws Exception{
        ArrayList<Suspendable> suspendedUsers = getSuspendedUsers();

        Suspendable user = null;

        for(Suspendable s: suspendedUsers){
            if(s.getSuspendableId().equals(id)){
                user = s;
                break;
            }
        }

        if(user == null){
            throw new Exception("There's no suspended user with id = " + id);
        }

        ((Admin)CommonSection.authenticatedUser).unsuspend(user);
    }

    @GetMapping("/featured-destination-areas")
    public ArrayList<String> featuredDesAreas() throws Exception{
        checkAuthentication();

        return CommonSection.sysDatabase.getFeaturedDestinationAreas();
    }

    @PostMapping("/featured-destination-areas/add")
    public void addFeaturedDesAreas(@RequestParam String area) throws Exception{
        checkAuthentication();

        ((Admin)CommonSection.authenticatedUser).addFeaturedDestinationArea(area);
    }

    @GetMapping("/public-holidays")
    public ArrayList<Date> publicHolidays() throws Exception{
        checkAuthentication();

        return CommonSection.sysDatabase.getPublicHolidays();
    }

    @PostMapping("/public-holidays/add")
    public void addPublicHoliday(@RequestParam String date) throws Exception{
        checkAuthentication();

        Date holiday = new SimpleDateFormat("dd/MM/yyyy").parse(date.replaceAll("\"", ""));

        ((Admin)CommonSection.authenticatedUser).addPublicHoliday(holiday);
    }

    @GetMapping("/ride-requests")
    public ArrayList<RideRequest> rideRequests() throws Exception{
        checkAuthentication();

        ArrayList<RideRequest> rideRequests = CommonSection.sysDatabase.getSystemRideRequests();

        return rideRequests;
    }

    @GetMapping("/ride-requests/{id}/events")
    public ArrayList<RideEvent> rideRequestEvents(@PathVariable String id) throws Exception{
        checkAuthentication();

        RideRequest req = CommonSection.sysDatabase.getRideRequest(id);

        if(req == null){
            throw new Exception("There's no ride request with id = " + id);
        }

        ArrayList<RideEvent> rideEvents = req.getEvents();

        return rideEvents;
    }

    @PostMapping("/logout")
    public void logout() throws Exception{
        checkAuthentication();

        CommonSection.authenticatedUser = null;
        CommonSection.authenticator.logout();
    }
}
