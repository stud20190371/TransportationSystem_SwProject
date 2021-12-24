package exceptions;

public class UsernameNotExistException extends Exception{
    public UsernameNotExistException(String msg){
        super(msg);
    }
}
