package user;

import java.util.ArrayList;

import interfaces.Suspendable;
import interfaces.Suspender;
import interfaces.Verifiable;
import interfaces.Verifier;

public class Admin extends User implements Verifier, Suspender {
    private ArrayList<Verifiable> verificationRequests;
    private static int adminsCount = 0;
    
    public Admin(String username, String mobileNum, String email, String password){
        super(("admin"+(++adminsCount)), username, mobileNum, email, password);
        this.verificationRequests = new ArrayList<>();
    }

    public void addVerificationRequest(Verifiable verifiable){
        this.verificationRequests.add(verifiable);
    }

    @Override
    public void verify(Verifiable verifiable) {
        verifiable.changeVerificationState(true);
    }

    @Override
    public void suspend(Suspendable suspendable) {
        suspendable.changeSuspensionState(true);
    }
}
