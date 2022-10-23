package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public interface Visokoskolska {
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, int ocjenaPismeno, int ocjenaObrana);

    default public BigDecimal odrediProsjekOcjenaNaIspitima(Ispit[] ispiti){
        BigDecimal prosjek = new BigDecimal("0");
        for(Ispit ispit: ispiti)
            prosjek.add(BigDecimal.valueOf(ispit.getOcjena()));

        prosjek.divide(BigDecimal.valueOf(ispiti.length));

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
