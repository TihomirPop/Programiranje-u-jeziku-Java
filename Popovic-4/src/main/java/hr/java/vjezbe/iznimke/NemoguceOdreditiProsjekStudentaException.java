package hr.java.vjezbe.iznimke;

/**
 * Oznaceni exception koji se baca kada je barem jedna studentova ocjena negativna
 */
public class NemoguceOdreditiProsjekStudentaException extends Exception{
    public NemoguceOdreditiProsjekStudentaException() {
    }

    public NemoguceOdreditiProsjekStudentaException(String message) {
        super(message);
    }

    public NemoguceOdreditiProsjekStudentaException(String message, Throwable cause) {
        super(message, cause);
    }

    public NemoguceOdreditiProsjekStudentaException(Throwable cause) {
        super(cause);
    }
}
