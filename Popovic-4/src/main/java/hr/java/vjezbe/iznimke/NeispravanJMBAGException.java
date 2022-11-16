package hr.java.vjezbe.iznimke;

import hr.java.vjezbe.glavna.Glavna;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Neoznacena iznimka koja se baca kada se unese krivi JMBG
 */

public class NeispravanJMBAGException extends RuntimeException{
    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    /**
     * default konstruktor
     */
    public NeispravanJMBAGException() {
    }

    /**
     * konstruktor sa porukom
     * @param message - poruka iznimke
     */
    public NeispravanJMBAGException(String message) {
        super(message);
        logger.error(message, this);
    }

    /**
     * konstruktor sa porukom i uzrokom iznimke
     * @param message - poruka iznimke
     * @param cause - uzrok iznimke
     */
    public NeispravanJMBAGException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * konstruktor sa uzrokom iznimke
     * @param cause - uzrok iznmke
     */
    public NeispravanJMBAGException(Throwable cause) {
        super(cause);
    }
}
