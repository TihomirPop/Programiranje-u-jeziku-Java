package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.math.MathContext;

public class FakultetRacunarstva extends ObrazovnaUstanova implements Diplomski{

    public FakultetRacunarstva(String naziv, Predmet[] predmeti, Profesor[] profesori, Student[] studenti, Ispit[] ispiti) {
        super(naziv, predmeti, profesori, studenti, ispiti);
    }

    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, int diplomskiRadPismeno, int diplomskiRadObrana){
        BigDecimal konacnaOcjena = odrediProsjekOcjenaNaIspitima(ispiti);
        konacnaOcjena = konacnaOcjena.multiply(BigDecimal.valueOf(3));
        konacnaOcjena = konacnaOcjena.add(BigDecimal.valueOf(diplomskiRadPismeno)).add(BigDecimal.valueOf(diplomskiRadObrana));
        konacnaOcjena = konacnaOcjena.divide(BigDecimal.valueOf(5));
        return konacnaOcjena;
    }

    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(int godina){
        Student najuspjesniji = getStudenti()[0];
        int najvisePetica = 0;
        for(Ispit ispit: filtrirajIspitePoStudentu(getIspiti(), najuspjesniji))
            if(ispit.getOcjena().equals(5) && (ispit.getDatumIVrijeme().getYear() == godina))
                najvisePetica++;


        for(Student student: getStudenti()){
            Ispit[] ispiti = filtrirajIspitePoStudentu(getIspiti(), student);
            int n = 0;
            for(Ispit ispit: ispiti)
                if(ispit.getOcjena().equals(5) && (ispit.getDatumIVrijeme().getYear() == godina))
                    n++;

            if(n > najvisePetica){
                najvisePetica = n;
                najuspjesniji = student;
            }
        }

        return najuspjesniji;
    }

    @Override
    public Student odrediStudentaZaRektorovuNagradu(){
        Student najbolji = getStudenti()[0];

        for(Student student: getStudenti()){
            if(odrediProsjekOcjenaNaIspitima(filtrirajIspitePoStudentu(getIspiti(), student)).compareTo(odrediProsjekOcjenaNaIspitima(filtrirajIspitePoStudentu(getIspiti(), najbolji))) == 1)
                najbolji = student;
            else if(odrediProsjekOcjenaNaIspitima(filtrirajIspitePoStudentu(getIspiti(), student)).compareTo(odrediProsjekOcjenaNaIspitima(filtrirajIspitePoStudentu(getIspiti(), najbolji))) == 0)
                if(student.getDatumRodjenja().compareTo(najbolji.getDatumRodjenja()) == -1)
                    najbolji = student;
        }

        return najbolji;
    }
}
