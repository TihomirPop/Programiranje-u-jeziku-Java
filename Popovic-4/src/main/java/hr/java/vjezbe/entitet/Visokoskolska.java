package hr.java.vjezbe.entitet;

import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * interface za visokoskolske obrazovne ustanve
 */
public interface Visokoskolska {

    /**
     * Racuna konacnu ocjenu studenta
     * @param ispiti - array ispita studenta
     * @param ocjenaPismeno - ocjena iz pismenog dijala zavrsnog rada
     * @param ocjenaObrana - ocjena iz obrane zavrsnog rada
     * @return - konacna ocjena
     */
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> ispiti, Ocjena ocjenaPismeno, Ocjena ocjenaObrana);

    /**
     * Racuna prosjek ocjena na ispitima
     * @param ispiti - array ispita studenta
     * @return - prosjek ocjena
     * @throws NemoguceOdreditiProsjekStudentaException - iznimka koja se baca kada je ocjena barem jednog ispita 1
     */
    default public BigDecimal odrediProsjekOcjenaNaIspitima(List<Ispit> ispiti) throws NemoguceOdreditiProsjekStudentaException{
        BigDecimal prosjek = BigDecimal.ZERO;

        for(Ispit ispit: ispiti){

            if (ispit.getOcjena() == Ocjena.NEDOVOLJAN)
                throw(new NemoguceOdreditiProsjekStudentaException("Student " + ispit.getStudent().getIme() + " " + ispit.getStudent().getPrezime() + " je ocjenjen negativnom ocjenom iz predmeta " + ispit.getPredmet().getNaziv()));
            prosjek = prosjek.add(ispit.getOcjena().getBigDecimal());
        }

        prosjek = prosjek.divide(BigDecimal.valueOf(ispiti.size()));

        return prosjek;
    }

    /**
     * metoda vraca ispite gdje je ocjena veca od 1
     * @param ispiti - array ispita
     * @return - array polozenih ispita
     */
    private List<Ispit> filtrirajPolozeneIspite(List<Ispit> ispiti){
        int n = 0;
        for(Ispit ispit: ispiti)
            if(ispit.getOcjena().getInt() > 1)
                n++;

        List<Ispit> polozeni = new ArrayList<>();

        for(Ispit ispit: ispiti)
            if(ispit.getOcjena().getInt() > 1)
                polozeni.add(ispit);

        return polozeni;
    }

    /**
     * metoda vraca ispite koje je pisao zadani student
     * @param ispiti - array svih ispita
     * @param student - student cije ispite zelimo dobiti
     * @return - array ispita koje je pisao zadani student
     */
    default public List<Ispit> filtrirajIspitePoStudentu(List<Ispit> ispiti, Student student){
        int n = 0;
        for(Ispit ispit: ispiti)
            if(ispit.getStudent().equals(student))
                n++;

        List<Ispit> ispitiStudenta = new ArrayList<>();

        for(Ispit ispit: ispiti)
            if(ispit.getStudent().equals(student))
                ispitiStudenta.add(ispit);

        return ispitiStudenta;
    }
}
