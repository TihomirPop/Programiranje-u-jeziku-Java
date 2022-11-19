package hr.java.vjezbe.entitet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Apstraktna klasa koja predstavlja obrazovnu ustanovu
 */
public abstract class ObrazovnaUstanova {
    private String naziv;
    private List<Predmet> predmeti;
    private List<Profesor> profesori;
    private List<Student> studenti;
    private List<Ispit> ispiti;

    public ObrazovnaUstanova(String naziv, List<Predmet> predmeti, List<Profesor> profesori, List<Student> studenti, List<Ispit> ispiti) {
        this.naziv = naziv;
        this.predmeti = predmeti;
        this.profesori = profesori;
        this.studenti = studenti;
        this.ispiti = ispiti;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public List<Predmet> getPredmeti() {
        return predmeti;
    }

    public void setPredmeti(List<Predmet> predmeti) {
        this.predmeti = predmeti;
    }

    public List<Profesor> getProfesori() {
        return profesori;
    }

    public void setProfesori(List<Profesor> profesori) {
        this.profesori = profesori;
    }

    public List<Student> getStudenti() {
        return studenti;
    }

    public void setStudenti(List<Student> studenti) {
        this.studenti = studenti;
    }

    public List<Ispit> getIspiti() {
        return ispiti;
    }

    public void setIspiti(List<Ispit> ispiti) {
        this.ispiti = ispiti;
    }

    /**
     * apstraktna metoda koja vraca najuspjesnijeg studenta na godini
     * @param godina - godina na kojoj se trazi najuspjesniji student
     * @return - najuspjesniji student
     */
    public abstract Student odrediNajuspjesnijegStudentaNaGodini(int godina);

    /**
     * filtrira pozitivne studente
     * @return - array pozitivnih studenata
     */

    public Set<Student> filtrirajPozitivneStudente(){
        Set<Student> pozitivniStudenti = new HashSet<>();
        for(Student student: studenti)
            pozitivniStudenti.add(student);

        for(Ispit ispit: ispiti)
            if(ispit.getOcjena() == Ocjena.NEDOVOLJAN)
                pozitivniStudenti.remove(ispit.getStudent());

        return pozitivniStudenti;
    }
}
