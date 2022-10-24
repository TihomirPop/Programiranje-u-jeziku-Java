package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;

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

    private int studentIndex(Student student){
        int index = -1;

        for(int i = 0; i < getStudenti().length; i++)
            if(getStudenti()[i] == student){
                index = i;
                break;
            }

        return index;
    }

    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(int godina){
        Student najuspjesniji = null;

        for(Ispit ispit: getIspiti())
            if(ispit.getDatumIVrijeme().getYear() == godina){
                najuspjesniji = ispit.getStudent();
                break;
            }

        for(Ispit ispit: getIspiti()){
            if(ispit.getDatumIVrijeme().getYear() == godina){
                if(najuspjesniji == ispit.getStudent())
                    continue;

                Ispit[] studentoviIspiti = filtrirajIspitePoStudentu(getIspiti(), ispit.getStudent());
                Ispit[] najuspjesnijiIspiti = filtrirajIspitePoStudentu(getIspiti(), najuspjesniji);

                if(odrediProsjekOcjenaNaIspitima(studentoviIspiti).compareTo(odrediProsjekOcjenaNaIspitima(najuspjesnijiIspiti)) == 1)
                    najuspjesniji = ispit.getStudent();
                else if(odrediProsjekOcjenaNaIspitima(studentoviIspiti).compareTo(odrediProsjekOcjenaNaIspitima(najuspjesnijiIspiti)) == 0)
                    if(studentIndex(ispit.getStudent()) > studentIndex(najuspjesniji))
                        najuspjesniji = ispit.getStudent();
            }
        }

        return najuspjesniji;
    }
}
