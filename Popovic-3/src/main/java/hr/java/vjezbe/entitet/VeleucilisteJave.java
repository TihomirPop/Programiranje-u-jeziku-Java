package hr.java.vjezbe.entitet;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Klasa koja predstavlja veleuciliste Jave, nasljeduje ObrazovnaUstanova i implementira Visokoskolska
 */
public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska{
    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
    public VeleucilisteJave(String naziv, Predmet[] predmeti, Profesor[] profesori, Student[] studenti, Ispit[] ispiti) {
        super(naziv, predmeti, profesori, studenti, ispiti);
    }

    /**
     * Racuna konacnu ocjenu studenta tako da pomnozi prosjek sa 2, doda ocjene iz pismenog djela i obrane zavrsnog rada i podjeli sa 4
     * Ako metoda ulovi NemoguceOdreditiProsjekStudentaException, to znaci da je barem jedan ispit negativan i da je student negativan
     * @param ispiti - array ispita studenta
     * @param ocjenaPismeno - ocjena iz pismenog dijala zavrsnog rada
     * @param ocjenaObrana - ocjena iz obrane zavrsnog rada
     * @return - konacna ocjena studenta
     */
    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, int ocjenaPismeno, int ocjenaObrana){
        try {
            BigDecimal konacnaOcjena = odrediProsjekOcjenaNaIspitima(ispiti);
            konacnaOcjena = konacnaOcjena.multiply(BigDecimal.valueOf(2));
            konacnaOcjena = konacnaOcjena.add(BigDecimal.valueOf(ocjenaPismeno)).add(BigDecimal.valueOf(ocjenaObrana));
            konacnaOcjena = konacnaOcjena.divide(BigDecimal.valueOf(4));
            return konacnaOcjena.round(new MathContext(1));
        } catch (NemoguceOdreditiProsjekStudentaException e) {
            logger.warn("Student " + ispiti[0].getStudent().getIme() + " " + ispiti[0].getStudent().getPrezime() + " zbog negativne ocjene na jednom od predmeta ima prosjek 'nedovoljan (1)'!", e);
            System.out.println("Student " + ispiti[0].getStudent().getIme() + " " + ispiti[0].getStudent().getPrezime() + " zbog negativne ocjene na jednom od predmeta ima prosjek 'nedovoljan (1)'!");
            return BigDecimal.ONE;
        }
    }

    /**
     * Vraca najuspjesnijeg studenta na godini
     * Najuspjesniji student je onaj sa najvecim prosjekom
     * Ako vise studenata ima najveci prosjek onda se uzima onaj koji je zadnji po redu u Student arrayu
     * @param godina - godina na kojoj se trazi najuspjesniji student
     * @return - najuspjesniji student
     */
    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(int godina) {
        Student najuspjesniji = getStudenti()[0];
        BigDecimal najveciProsjek;
        try {
            najveciProsjek = odrediProsjekOcjenaNaIspitima(filtrirajIspitePoStudentu(getIspiti(), najuspjesniji));
        } catch (NemoguceOdreditiProsjekStudentaException e) {
            logger.warn("Student " + najuspjesniji.getIme() + " " + najuspjesniji.getPrezime() + " zbog negativne ocjene na jednom od predmeta ima prosjek 'nedovoljan (1)'!", e);
            najveciProsjek = BigDecimal.ONE;
        }

        for (Student student : getStudenti()) {
            BigDecimal prosjek;
            try {
                prosjek = odrediProsjekOcjenaNaIspitima(filtrirajIspitePoStudentu(getIspiti(), student));
            } catch (NemoguceOdreditiProsjekStudentaException e) {
                logger.warn("Student " + student.getIme() + " " + student.getPrezime() + " zbog negativne ocjene na jednom od predmeta ima prosjek 'nedovoljan (1)'!", e);
                prosjek = BigDecimal.ONE;
            }
            if (prosjek.compareTo(najveciProsjek) >= 0) {
                najuspjesniji = student;
                najveciProsjek = prosjek;
            }
        }

        return najuspjesniji;
    }
}
