package user;

import java.util.ArrayList;
import java.util.Date;

import interfaces.Suspendable;
import interfaces.Suspender;
import interfaces.Verifiable;
import interfaces.Verifier;

public class Admin extends User implements Verifier, Suspender {
    private ArrayList<Verifiable> verificationRequests;
    private static int adminsCount = 0;
    
    public Admin(String username, String mobileNum, String email, String password, Date birthdate){
        super(("admin"+(++adminsCount)), username, mobileNum, email, password, birthdate);
        this.verificationRequests = new ArrayList<>();
    }

    public void addFeaturedDestinationArea(String area){
        this.sysDatabase.addFeaturedDestinationArea(area);
    }

    public void addPublicHoliday(Date date){
        this.sysDatabase.addPublicHoliday(date);
    }

    public void addVerificationRequest(Verifiable verifiable){
        this.verificationRequests.add(verifiable);
    }

    public ArrayList<Verifiable> getVerificationRequests(){
        return this.verificationRequests;
    }

    @Override
    public void verify(Verifiable verifiable) {
        verifiable.changeVerificationState(true);
    }

    @Override
    public void suspend(Suspendable suspendable) {
        suspendable.changeSuspensionState(true);
    }

    @Override
    public void unsuspend(Suspendable suspendable) {
        suspendable.changeSuspensionState(false);
    }
}
