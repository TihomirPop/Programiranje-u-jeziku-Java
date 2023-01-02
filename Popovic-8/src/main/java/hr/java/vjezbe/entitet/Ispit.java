package hr.java.vjezbe.entitet;

import java.time.LocalDateTime;

/**
 * Klasa koja predstavlja ispit neke obrazovne ustanove
 */
public final class Ispit extends Entitet implements Online{
    private Predmet predmet;
    private Student student;
    private Ocjena ocjena;
    private LocalDateTime datumIVrijeme;

    private Dvorana dvorana;
    public Ispit(Long id, Predmet predmet, Student student, Ocjena ocjena, LocalDateTime datumIVrijeme, Dvorana dvorana) {
        super(id);
        this.predmet = predmet;
        this.student = student;
        this.ocjena = ocjena;
        this.datumIVrijeme = datumIVrijeme;
        this.dvorana = dvorana;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Ocjena getOcjena() {
        return ocjena;
    }

    public void setOcjena(Ocjena ocjena) {
        this.ocjena = ocjena;
    }

    public LocalDateTime getDatumIVrijeme() {
        return datumIVrijeme;
    }

    public void setDatumIVrijeme(LocalDateTime datumIVrijeme) {
        this.datumIVrijeme = datumIVrijeme;
    }
}
