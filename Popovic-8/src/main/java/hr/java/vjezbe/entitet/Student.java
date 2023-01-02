package hr.java.vjezbe.entitet;

import java.time.LocalDate;

/**
 * Klasa koja predstavlja studenta neke obrazovne ustanove, nasljeduje osobu
 */
public class Student extends Osoba{
    private String jmbag;
    private LocalDate datumRodjenja;
    private Ocjena ocjenaPismeno;
    private Ocjena ocjenaObrana;

    public Student(Long id, String ime, String prezime, String jmbag, LocalDate datumRodjenja, Ocjena ocjenaPismeno, Ocjena ocjenaObrana) {
        super(id, ime, prezime);
        this.jmbag = jmbag;
        this.datumRodjenja = datumRodjenja;
        this.ocjenaPismeno = ocjenaPismeno;
        this.ocjenaObrana = ocjenaObrana;
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

    public Ocjena getOcjenaPismeno() {
        return ocjenaPismeno;
    }

    public void setOcjenaPismeno(Ocjena ocjenaPismeno) {
        this.ocjenaPismeno = ocjenaPismeno;
    }

    public Ocjena getOcjenaObrana() {
        return ocjenaObrana;
    }

    public void setOcjenaObrana(Ocjena ocjenaObrana) {
        this.ocjenaObrana = ocjenaObrana;
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
