package util;

public class GestionException extends Exception {

    public GestionException(String message) {
        super(message);
    }

    public GestionException(String message, Throwable cause) {
        super(message, cause);
    }
}