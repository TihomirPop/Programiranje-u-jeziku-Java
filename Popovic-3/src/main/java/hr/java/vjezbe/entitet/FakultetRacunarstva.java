package hr.java.vjezbe.entitet;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.iznimke.PostojiViseNajmladjihStudenataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.MathContext;

public class FakultetRacunarstva extends ObrazovnaUstanova implements Diplomski{
    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    public FakultetRacunarstva(String naziv, Predmet[] predmeti, Profesor[] profesori, Student[] studenti, Ispit[] ispiti) {
        super(naziv, predmeti, profesori, studenti, ispiti);
    }

    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, int diplomskiRadPismeno, int diplomskiRadObrana){
        try {
            BigDecimal konacnaOcjena = odrediProsjekOcjenaNaIspitima(ispiti);
            konacnaOcjena = konacnaOcjena.multiply(BigDecimal.valueOf(3));
            konacnaOcjena = konacnaOcjena.add(BigDecimal.valueOf(diplomskiRadPismeno)).add(BigDecimal.valueOf(diplomskiRadObrana));
            konacnaOcjena = konacnaOcjena.divide(BigDecimal.valueOf(5));
            return konacnaOcjena;
        } catch (NemoguceOdreditiProsjekStudentaException e){
            logger.warn("Student " + ispiti[0].getStudent().getIme() + " " + ispiti[0].getStudent().getPrezime() + " zbog negativne ocjene na jednom od predmeta ima prosjek 'nedovoljan (1)'!", e);
            System.out.println("Student " + ispiti[0].getStudent().getIme() + " " + ispiti[0].getStudent().getPrezime() + " zbog negativne ocjene na jednom od predmeta ima prosjek 'nedovoljan (1)'!");
            return BigDecimal.ONE;
        }
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
    public Student odrediStudentaZaRektorovuNagradu() {
        Student najbolji = getStudenti()[0];
        BigDecimal prosjekNajboljeg;
        try {
            prosjekNajboljeg = odrediProsjekOcjenaNaIspitima(filtrirajIspitePoStudentu(getIspiti(), najbolji));
        } catch (NemoguceOdreditiProsjekStudentaException e) {
            logger.warn("Student " + najbolji.getIme() + " " + najbolji.getPrezime() + " zbog negativne ocjene na jednom od predmeta ima prosjek 'nedovoljan (1)'!", e);
            prosjekNajboljeg = BigDecimal.ONE;
        }

        for (Student student : getStudenti()) {
            BigDecimal prosjekStudenta;
            try {
                prosjekStudenta = odrediProsjekOcjenaNaIspitima(filtrirajIspitePoStudentu(getIspiti(), student));
            } catch (NemoguceOdreditiProsjekStudentaException e) {
                logger.warn("Student " + student.getIme() + " " + student.getPrezime() + " zbog negativne ocjene na jednom od predmeta ima prosjek 'nedovoljan (1)'!", e);
                prosjekStudenta = BigDecimal.ONE;
            }
            if (prosjekStudenta.compareTo(prosjekNajboljeg) == 1) {
                najbolji = student;
                prosjekNajboljeg = prosjekStudenta;
            }
            else if (prosjekStudenta.compareTo(prosjekNajboljeg) == 0){
                if (student.getDatumRodjenja().compareTo(najbolji.getDatumRodjenja()) == -1){
                    najbolji = student;
                    prosjekNajboljeg = prosjekStudenta;
                } else if (student.getDatumRodjenja().compareTo(najbolji.getDatumRodjenja()) == 0) {
                    String najmladjiStudenti = najbolji.getIme() + " " + najbolji.getPrezime() + ", " + student.getIme() + " " + student.getPrezime();

                    System.out.println("Postoji vise najmladih studenata: " + najmladjiStudenti);
                    logger.error("Postoji vise najmladih studenata: " + najmladjiStudenti);

                    throw new PostojiViseNajmladjihStudenataException(najmladjiStudenti);
                }
            }
        }

        return najbolji;
    }
}
