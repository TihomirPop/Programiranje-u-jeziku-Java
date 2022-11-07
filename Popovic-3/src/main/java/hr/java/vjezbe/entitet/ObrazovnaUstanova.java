package hr.java.vjezbe.entitet;

import java.util.HashSet;
import java.util.List;

public abstract class ObrazovnaUstanova {
    private String naziv;
    private Predmet[] predmeti;
    private Profesor[] profesori;
    private Student[] studenti;
    private Ispit[] ispiti;

    public ObrazovnaUstanova(String naziv, Predmet[] predmeti, Profesor[] profesori, Student[] studenti, Ispit[] ispiti) {
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

    public Predmet[] getPredmeti() {
        return predmeti;
    }

    public void setPredmeti(Predmet[] predmeti) {
        this.predmeti = predmeti;
    }

    public Profesor[] getProfesori() {
        return profesori;
    }

    public void setProfesori(Profesor[] profesori) {
        this.profesori = profesori;
    }

    public Student[] getStudenti() {
        return studenti;
    }

    public void setStudenti(Student[] studenti) {
        this.studenti = studenti;
    }

    public Ispit[] getIspiti() {
        return ispiti;
    }

    public void setIspiti(Ispit[] ispiti) {
        this.ispiti = ispiti;
    }

    public abstract Student odrediNajuspjesnijegStudentaNaGodini(int godina);

    public Student[] filtrirajPozitivneStudente(){
        HashSet<Student> pozitivniStudenti = new HashSet<>();
        for(Student student: studenti)
            pozitivniStudenti.add(student);

        for(Ispit ispit: ispiti)
            if(ispit.getOcjena() == 1)
                pozitivniStudenti.remove(ispit.getStudent());

        return pozitivniStudenti.toArray(new Student[0]);
    }
}
