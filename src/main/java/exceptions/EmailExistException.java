package exceptions;

public class EmailExistException extends Exception {
    public EmailExistException(String msg){
        super(msg);
    }
}
