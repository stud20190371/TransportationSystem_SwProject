package interfaces;

public interface Verifiable {
    public void changeVerificationState(boolean state);
    public boolean isVerified();
    public String getVerifiableId();
}
