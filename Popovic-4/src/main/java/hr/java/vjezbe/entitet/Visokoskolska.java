package hr.java.vjezbe.entitet;

import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

import java.math.BigDecimal;

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
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, int ocjenaPismeno, int ocjenaObrana);

    /**
     * Racuna prosjek ocjena na ispitima
     * @param ispiti - array ispita studenta
     * @return - prosjek ocjena
     * @throws NemoguceOdreditiProsjekStudentaException - iznimka koja se baca kada je ocjena barem jednog ispita 1
     */
    default public BigDecimal odrediProsjekOcjenaNaIspitima(Ispit[] ispiti) throws NemoguceOdreditiProsjekStudentaException{
        BigDecimal prosjek = BigDecimal.ZERO;

        for(Ispit ispit: ispiti){

            if (ispit.getOcjena() == Ocjena.NEDOVOLJAN)
                throw(new NemoguceOdreditiProsjekStudentaException("Student " + ispit.getStudent().getIme() + " " + ispit.getStudent().getPrezime() + " je ocjenjen negativnom ocjenom iz predmeta " + ispit.getPredmet().getNaziv()));
            prosjek = prosjek.add(ispit.getOcjena().getBigDecimal());
        }

        prosjek = prosjek.divide(BigDecimal.valueOf(ispiti.length));

        return prosjek;
    }

    /**
     * metoda vraca ispite gdje je ocjena veca od 1
     * @param ispiti - array ispita
     * @return - array polozenih ispita
     */
    private Ispit[] filtrirajPolozeneIspite(Ispit[] ispiti){
        int n = 0;
        for(Ispit ispit: ispiti)
            if(ispit.getOcjena().getInt() > 1)
                n++;

        Ispit[] polozeni = new Ispit[n];

        n = 0;
        for(Ispit ispit: ispiti)
            if(ispit.getOcjena().getInt() > 1) {
                polozeni[n] = ispit;
                n++;
            }

        return polozeni;
    }

    /**
     * metoda vraca ispite koje je pisao zadani student
     * @param ispiti - array svih ispita
     * @param student - student cije ispite zelimo dobiti
     * @return - array ispita koje je pisao zadani student
     */
    default public Ispit[] filtrirajIspitePoStudentu(Ispit[] ispiti, Student student){
        int n = 0;
        for(Ispit ispit: ispiti)
            if(ispit.getStudent().equals(student))
                n++;

        Ispit[] ispitiStudenta = new Ispit[n];

        n = 0;
        for(Ispit ispit: ispiti)
            if(ispit.getStudent().equals(student)){
                ispitiStudenta[n] = ispit;
                n++;
            }

        return ispitiStudenta;
    }
}
