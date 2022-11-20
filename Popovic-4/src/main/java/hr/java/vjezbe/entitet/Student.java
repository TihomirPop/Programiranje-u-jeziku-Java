package hr.java.vjezbe.entitet;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Klasa koja predstavlja studenta neke obrazovne ustanove, nasljeduje osobu
 */
public class Student extends Osoba{
    private String jmbag;
    private LocalDate datumRodjenja;

    public Student(String ime, String prezime, String jmbag, LocalDate datumRodjenja) {
        super(ime, prezime);
        this.jmbag = jmbag;
        this.datumRodjenja = datumRodjenja;
    }

    public String getJmbag() {
        return jmbag;
    }

    public void setJmbag(String jmbag) {
        this.jmbag = jmbag;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;

        if (getJmbag() != null ? !getJmbag().equals(student.getJmbag()) : student.getJmbag() != null) return false;
        return getDatumRodjenja() != null ? getDatumRodjenja().equals(student.getDatumRodjenja()) : student.getDatumRodjenja() == null;
    }

    @Override
    public int hashCode() {
        int result = getJmbag() != null ? getJmbag().hashCode() : 0;
        result = 31 * result + (getDatumRodjenja() != null ? getDatumRodjenja().hashCode() : 0);
        return result;
    }
}
