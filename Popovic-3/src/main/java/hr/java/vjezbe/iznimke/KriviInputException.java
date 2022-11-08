package hr.java.vjezbe.iznimke;

/**
 * Generic exception za sve slucaje kada korisnit unese krivu vrijednost
 */
public class KriviInputException extends Exception{
    public KriviInputException() {
    }

    public KriviInputException(String message) {
        super(message);
    }

    public KriviInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public KriviInputException(Throwable cause) {
        super(cause);
    }

    public KriviInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
