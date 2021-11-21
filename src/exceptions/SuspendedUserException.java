package exceptions;

public class SuspendedUserException extends Exception{
    public SuspendedUserException(String msg){
        super(msg);
    }
}
