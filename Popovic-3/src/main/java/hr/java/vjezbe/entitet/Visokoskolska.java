package hr.java.vjezbe.entitet;

import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

import java.math.BigDecimal;

public interface Visokoskolska {
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, int ocjenaPismeno, int ocjenaObrana);

    default public BigDecimal odrediProsjekOcjenaNaIspitima(Ispit[] ispiti) throws NemoguceOdreditiProsjekStudentaException{
        BigDecimal prosjek = BigDecimal.ZERO;

        for(Ispit ispit: ispiti){

            if (ispit.getOcjena() == 1)
                throw(new NemoguceOdreditiProsjekStudentaException("Student " + ispit.getStudent().getIme() + " " + ispit.getStudent().getPrezime() + " je ocjenjen negativnom ocjenom iz predmeta " + ispit.getPredmet().getNaziv()));
            prosjek = prosjek.add(BigDecimal.valueOf(ispit.getOcjena()));
        }

        prosjek = prosjek.divide(BigDecimal.valueOf(ispiti.length));

        return prosjek;
    }

    private Ispit[] filtrirajPolozeneIspite(Ispit[] ispiti){
        int n = 0;
        for(Ispit ispit: ispiti)
            if(ispit.getOcjena() > 1)
                n++;

        Ispit[] polozeni = new Ispit[n];

        n = 0;
        for(Ispit ispit: ispiti)
            if(ispit.getOcjena() > 1) {
                polozeni[n] = ispit;
                n++;
            }

        return polozeni;
    }

    default public Ispit[] filtrirajIspitePoStudentu(Ispit[] ispiti, Student student){
        int n = 0;
        for(Ispit ispit: ispiti)
            if(ispit.getStudent().getJmbag().equals(student.getJmbag()))
                n++;

        Ispit[] ispitiStudenta = new Ispit[n];

        n = 0;
        for(Ispit ispit: ispiti)
            if(ispit.getStudent().getJmbag().equals(student.getJmbag())){
                ispitiStudenta[n] = ispit;
                n++;
            }

        return ispitiStudenta;
    }
}
