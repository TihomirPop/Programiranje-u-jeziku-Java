package hr.java.vjezbe.entitet;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.iznimke.PostojiViseNajmladjihStudenataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

/**
 * Klasa koja predstavlja fakultet racunarstva, nasljeduje ObrazovnaUstanova i implementira Diplomski
 */
public class FakultetRacunarstva extends ObrazovnaUstanova implements Diplomski{
    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    public FakultetRacunarstva(String naziv, List<Predmet> predmeti, List<Profesor> profesori, List<Student> studenti, List<Ispit> ispiti) {
        super(naziv, predmeti, profesori, studenti, ispiti);
    }

    /**
     * Racuna konacnu ocjenu studenta tako da pomnozi prosjek sa 3, doda ocjene iz pismenog djela i obrane diplomskog rada i podjeli sa 5
     * Ako metoda ulovi NemoguceOdreditiProsjekStudentaException, to znaci da je barem jedan ispit negativan i da je student negativan
     * @param ispiti - array ispita studenta
     * @param diplomskiRadPismeno - ocjena iz pismenog dijala zavrsnog rada
     * @param diplomskiRadObrana - ocjena iz obrane zavrsnog rada
     * @return - konacna ocjena studenta
     */
    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> ispiti, Ocjena diplomskiRadPismeno, Ocjena diplomskiRadObrana){
        try {
            BigDecimal konacnaOcjena = odrediProsjekOcjenaNaIspitima(ispiti);
            konacnaOcjena = konacnaOcjena.multiply(BigDecimal.valueOf(3));
            konacnaOcjena = konacnaOcjena.add(diplomskiRadPismeno.getBigDecimal()).add(diplomskiRadObrana.getBigDecimal());
            konacnaOcjena = konacnaOcjena.divide(BigDecimal.valueOf(5));
            return konacnaOcjena;
        } catch (NemoguceOdreditiProsjekStudentaException e){
            logger.warn("Student " + ispiti.get(0).getStudent().getIme() + " " + ispiti.get(0).getStudent().getPrezime() + " zbog negativne ocjene na jednom od predmeta ima prosjek 'nedovoljan (1)'!", e);
            System.out.println("Student " + ispiti.get(0).getStudent().getIme() + " " + ispiti.get(0).getStudent().getPrezime() + " zbog negativne ocjene na jednom od predmeta ima prosjek 'nedovoljan (1)'!");
            return BigDecimal.ONE;
        }
    }

    /**
     * Vraca najuspjesnijeg studenta na godini
     * Najuspjesniji student je onaj sa najvise petica
     * Ako vise studenata ima najvise petica onda se uzima onaj koji je prvi po redu u Student arrayu
     * @param godina - godina na kojoj se trazi najuspjesniji student
     * @return - najuspjesniji student
     */
    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(int godina){
        Student najuspjesniji = getStudenti().get(0);
        int najvisePetica = 0;
        for(Ispit ispit: filtrirajIspitePoStudentu(getIspiti(), najuspjesniji))
            if(ispit.getOcjena().equals(5) && (ispit.getDatumIVrijeme().getYear() == godina))
                najvisePetica++;


        for(Student student: getStudenti()){
            List<Ispit> ispiti = filtrirajIspitePoStudentu(getIspiti(), student);
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

    /**
     * Vraca studenta koji ce dobiti rektorovu nagradu
     * To je student sa najvecim prosjekom
     * Ako vise studenata ima najveci prosjek onda se uzima onaj koji je najmladi
     * Ako su najmladi studenti rodeni na isti datum onda se baca PostojiViseNajmladjihStudenataException
     * @return - student koji ce dobiti rektorovu nagradu
     */
    @Override
    public Student odrediStudentaZaRektorovuNagradu() {
        Student najbolji = getStudenti().get(0);
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
            else if ((prosjekStudenta.compareTo(prosjekNajboljeg) == 0) && (najbolji != student)){
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
