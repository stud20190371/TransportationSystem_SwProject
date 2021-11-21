package exceptions;

public class UsernameExistException extends Exception {
    public UsernameExistException(String msg){
        super(msg);
    }
}
