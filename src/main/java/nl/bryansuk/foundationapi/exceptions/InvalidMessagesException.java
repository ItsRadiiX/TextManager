package nl.bryansuk.foundationapi.exceptions;

public class InvalidMessagesException extends RuntimeException {

    public InvalidMessagesException(String message){
        super(message);
    }

    public InvalidMessagesException(String message, Throwable cause) {
        super(message, cause);
    }
}
