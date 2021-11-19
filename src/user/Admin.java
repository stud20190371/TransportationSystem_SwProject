package user;

import java.util.ArrayList;

import interfaces.Suspendable;
import interfaces.Suspender;
import interfaces.Verifiable;
import interfaces.Verifier;

public class Admin extends User implements Verifier, Suspender {
    private ArrayList<Verifiable> verificationRequests;
    
    Admin(String id, String username, String mobileNum, String email, String password){
        super(id, username, mobileNum, email, password);

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