package hr.java.vjezbe.entitet;

/**
 * Interface koji nasljeduje Visokoskolska, namjenjen je za diplomske obrazovne ustanove
 */
public interface Diplomski extends Visokoskolska{

    /**
     * metoda vraca studenta koji ce dobiti rektorovu nagradu
     * @return - student koji ce dobiti rektorovu nagradu
     */
    public Student odrediStudentaZaRektorovuNagradu();
}
