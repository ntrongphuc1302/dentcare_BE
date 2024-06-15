package online.be.exception;

public class InvalidRoleException extends RuntimeException{
    public InvalidRoleException(String message) {
        super(message);
    }
}
