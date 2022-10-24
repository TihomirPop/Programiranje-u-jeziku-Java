package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.math.MathContext;

public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska{

    public VeleucilisteJave(String naziv, Predmet[] predmeti, Profesor[] profesori, Student[] studenti, Ispit[] ispiti) {
        super(naziv, predmeti, profesori, studenti, ispiti);
    }

    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, int ocjenaPismeno, int ocjenaObrana){
        BigDecimal konacnaOcjena = odrediProsjekOcjenaNaIspitima(ispiti);
        konacnaOcjena.multiply(BigDecimal.valueOf(2));
        konacnaOcjena.add(BigDecimal.valueOf(ocjenaPismeno)).add(BigDecimal.valueOf(ocjenaObrana));
        konacnaOcjena.divide(BigDecimal.valueOf(4));
        return konacnaOcjena.round(new MathContext(1));
    }

    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(int godina){
        Student najuspjesniji = getStudenti()[0];
        BigDecimal najveciProsjek = odrediProsjekOcjenaNaIspitima(filtrirajIspitePoStudentu(getIspiti(), najuspjesniji));

        for(Student student: getStudenti()){
            BigDecimal prosjek = odrediProsjekOcjenaNaIspitima(filtrirajIspitePoStudentu(getIspiti(), student));
            if(prosjek.compareTo(najveciProsjek) >= 0){
                najuspjesniji = student;
                najveciProsjek = prosjek;
            }
        }

        return najuspjesniji;
    }
}
