package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.iznimke.KriviInputException;
import hr.java.vjezbe.sortiranje.StudentSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Glavna klasa sa metodom main
 */
public class Glavna {
    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    private static final int BROJ_PROFESORA = 2;
    private static final int BROJ_PREDMETA = 3;
    private static final int BROJ_STUDENATA = 2;
    private static final int BROJ_ISPITA = 2;

    /**
     * Pocetak programa
     * @param args - command line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        boolean kriviBrojUstanova;
        int brojUstanova = 0;
        do {
            System.out.print("Unesite broj obrazovnih ustanova: ");
            try {
                brojUstanova = input.nextInt();
                input.nextLine();
                if (brojUstanova < 1)
                    throw (new KriviInputException("Broj ustanova mora biti veci od 0"));
                kriviBrojUstanova = false;
            } catch (KriviInputException e) {
                logger.warn(e.getMessage(), e);
                System.out.println(e.getMessage());
                kriviBrojUstanova = true;
            } catch (InputMismatchException e) {
                logger.warn("Neispravan unos!", e);
                System.out.println("Neispravan unos!");
                input.nextLine();
                kriviBrojUstanova = true;
            }
        } while (kriviBrojUstanova);
        List<ObrazovnaUstanova> obrazovneUstanove = new ArrayList<>();

        List<Profesor> profesori = new ArrayList<>();
        List<Predmet> predmeti = new ArrayList<>();
        List<Student> studenti = new ArrayList<>();
        List<Ispit> ispiti = new ArrayList<>();
        Map<Profesor, List<Predmet>> nositelji = new HashMap<>();

        for (int i = 0; i < brojUstanova; i++) {
            profesori.clear();
            predmeti.clear();
            studenti.clear();
            ispiti.clear();
            nositelji.clear();

            System.out.println("Unesite podatke za " + (i + 1) + ". obrazovnu ustanovu:");

            profesori = ucitajProfesore(input);
            predmeti = ucitajPredmete(input, profesori, nositelji);
            studenti = ucitajStudente(input);
            ispiti = ucitajIspite(input, predmeti, studenti);

            for (Predmet predmet : predmeti)
                for (Ispit ispit : ispiti)
                    if (ispit.getPredmet() == predmet)
                        predmet.getStudenti().add(ispit.getStudent());

            for(Predmet predmet: predmeti){
                if(predmet.getStudenti().isEmpty())
                    System.out.println("Nema studenata upisanih na predmet '" + predmet.getNaziv() + "'.");
                else
                    for(Student student: predmet.getStudenti().stream().sorted(new StudentSorter()).toList())
                        System.out.println(student.getPrezime() + " " + student.getIme());
            }

            for (Ispit ispit : ispiti) {
                if (ispit.getOcjena() == Ocjena.ODLICAN) {
                    System.out.print("Student " + ispit.getStudent().getIme() + " " + ispit.getStudent().getPrezime() + " je ostvario ocjenu 'izvrstan' na predmetu '" + ispit.getPredmet().getNaziv() + "' \n");
                }
            }

            odabirUstanove(input, obrazovneUstanove, i, profesori, predmeti, studenti, ispiti);

        }
    }

    /**
     * Metoda za odabir ustanove, racunanje konacne ocjene studenta, trazenje najboljeg studenta i trazenje studenta za rektorovu nagradu
     * @param input - scanner - scanner s kojim se ucitavaju podatci
     * @param obrazovneUstanove - array obraznovnih ustanova
     * @param i - redni broj obrazovne ustanove za koju se unose podatci
     * @param profesori - array profesora te ustanove
     * @param predmeti - array predmeta te ustanove
     * @param studenti - array studenata te ustanove
     * @param ispiti - array ispita te ustanove
     */
    private static void odabirUstanove(Scanner input, List<ObrazovnaUstanova> obrazovneUstanove, int i, List<Profesor> profesori, List<Predmet> predmeti, List<Student> studenti, List<Ispit> ispiti) {
        System.out.print("Odaberite obrazovnu ustanovu za navedene podatke koju želite unijeti (1 - Veleučilište Jave, 2 - Fakultet računarstva): ");
        int odabirUstanove = input.nextInt();
        input.nextLine();

        System.out.print("Unesite naziv obrazovne ustanove: ");
        String nazivUstanove = input.nextLine();

        switch (odabirUstanove) {
            case 1 -> obrazovneUstanove.add(new VeleucilisteJave(nazivUstanove, predmeti, profesori, studenti, ispiti));
            case 2 -> obrazovneUstanove.add(new FakultetRacunarstva(nazivUstanove, predmeti, profesori, studenti, ispiti));
        }

        if(obrazovneUstanove.get(i) instanceof Visokoskolska visokoskolska){
            Set<Student> pozitivniStudenti = obrazovneUstanove.get(i).filtrirajPozitivneStudente();

            for (Student student : pozitivniStudenti) {
                boolean kriviZavrsni;
                do {
                    try {
                        System.out.print("Unesite ocjenu završnog rada za studenta: " + student.getIme() + " " + student.getPrezime() + ": ");
                        int intZavrsni = input.nextInt();
                        input.nextLine();

                        if(intZavrsni < 1 || intZavrsni > 5)
                            throw new KriviInputException("Ocjene iz zavrsnog rada moraju biti izmedu 1 i 5");

                        System.out.print("Unesite ocjenu obrane zavrsnog rada za studenta: " + student.getIme() + " " + student.getPrezime() + ": ");
                        int intObrana = input.nextInt();
                        input.nextLine();

                        if(intObrana < 1 || intObrana > 5)
                            throw new KriviInputException("Ocjene iz zavrsnog rada moraju biti izmedu 1 i 5");
                        kriviZavrsni = false;

                        System.out.println("Konačna ocjena studija studenta " + student.getIme() + " " + student.getPrezime() + " je " + visokoskolska.izracunajKonacnuOcjenuStudijaZaStudenta(visokoskolska.filtrirajIspitePoStudentu(obrazovneUstanove.get(i).getIspiti(), student), intToOcjena(intZavrsni), intToOcjena(intObrana)));
                    } catch (KriviInputException e) {
                        logger.warn(e.getMessage(), e);
                        System.out.println(e.getMessage());
                        kriviZavrsni = true;
                    } catch (InputMismatchException e) {
                        logger.warn("Neispravan unos!", e);
                        System.out.println("Neispravan unos!");
                        input.nextLine();
                        kriviZavrsni = true;
                    }
                }while (kriviZavrsni);
            }

            Student najboljiStudentVeleuciliste = obrazovneUstanove.get(i).odrediNajuspjesnijegStudentaNaGodini(2022);
            System.out.println("Najbolji student 2022. godine je " + najboljiStudentVeleuciliste.getIme() + " " + najboljiStudentVeleuciliste.getPrezime() + " JMBAG: " + najboljiStudentVeleuciliste.getJmbag());

            if(obrazovneUstanove.get(i) instanceof Diplomski diplomski) {
                Student rektorova = diplomski.odrediStudentaZaRektorovuNagradu();
                System.out.println("Student koji je osvojio rektorovu nagradu je " + rektorova.getIme() + " " + rektorova.getPrezime() + " JMBAG: " + rektorova.getJmbag());
            }

        }
    }

    /**
     * Ucitava profesore pomocu danog scannera
     * @param input - Scanner s kojim se ucitavaju podatci
     * @return - array ucitanih profesora
     */
    private static List<Profesor> ucitajProfesore(Scanner input){
        List<Profesor> profesori = new ArrayList<>();

        for(int i = 0; i < BROJ_PROFESORA; i++){
            System.out.print("Unesite " + (i + 1) + ". profesora: \n");

            System.out.print("Unesite sifru profesora: ");
            String sifra = input.nextLine();

            System.out.print("Unesite ime profesora: ");
            String ime = input.nextLine();

            System.out.print("Unesite prezime profesora: ");
            String prezime = input.nextLine();

            System.out.print("Unesite titulu profesora: ");
            String titula = input.nextLine();

            profesori.add(new Profesor.Builder(ime, prezime).saSifrom(sifra).saTitulom(titula).build());
        }
        return profesori;
    }

    /**
     * Ucitava predmete pomocu danog scannera i arraya profesora
     * @param input - Scanner s kojim se ucitavaju podatci
     * @param profesori - array dostupnih profesora
     * @return - array ucitanih predmeta
     */
    private static List<Predmet> ucitajPredmete(Scanner input, List<Profesor> profesori, Map<Profesor, List<Predmet>> nositelji){
        List<Predmet> predmeti = new ArrayList<>();

        boolean krivaKolicinaProfesora;
        boolean kriviBrojPredmeta;
        do {
            predmeti.clear();
            nositelji.clear();
            for (int i = 0; i < BROJ_PREDMETA; i++) {
                System.out.print("Unesite " + (i + 1) + ". predmet: \n");

                System.out.print("Unesite sifru predmeta: ");
                String sifra = input.nextLine();

                System.out.print("Unesite naziv predmeta: ");
                String naziv = input.nextLine();


                boolean kriviBrojECTSa;
                Integer ECTS = null;
                do {
                    System.out.print("Unesite broj ECTS bodova za predmet '" + naziv + "': ");
                    try {
                        ECTS = input.nextInt();
                        input.nextLine();
                        if (ECTS < 1)
                            throw (new KriviInputException("Broj ECTS mora biti veci od 0"));
                        kriviBrojECTSa = false;
                    } catch (KriviInputException e) {
                        logger.warn(e.getMessage(), e);
                        System.out.println(e.getMessage());
                        kriviBrojECTSa = true;
                    } catch (InputMismatchException e) {
                        logger.warn("Neispravan unos!", e);
                        System.out.println("Neispravan unos!");
                        input.nextLine();
                        kriviBrojECTSa = true;
                    }
                } while (kriviBrojECTSa);

                boolean kriviBrojProfesora;
                int brojProfesora = 0;
                do {
                    System.out.print("Odaberite profesora: \n");
                    for (int j = 0; j < BROJ_PROFESORA; j++) {
                        System.out.print((j + 1) + ". " + profesori.get(j).getIme() + " " + profesori.get(j).getPrezime() + "\n");
                    }
                    System.out.print("Odabir >> ");
                    try {
                        brojProfesora = input.nextInt();
                        input.nextLine();
                        if (brojProfesora < 1 || brojProfesora > BROJ_PROFESORA)
                            throw (new KriviInputException("Redni broj profesora mora biti izmedu 1 i " + BROJ_PROFESORA));
                        kriviBrojProfesora = false;
                    } catch (KriviInputException e) {
                        logger.warn(e.getMessage(), e);
                        System.out.println(e.getMessage());
                        kriviBrojProfesora = true;
                    } catch (InputMismatchException e) {
                        logger.warn("Neispravan unos!", e);
                        System.out.println("Neispravan unos!");
                        input.nextLine();
                        kriviBrojProfesora = true;
                    }
                } while (kriviBrojProfesora);
                Profesor profesor = profesori.get(brojProfesora - 1);

            /*
            boolean kriviBrojStudenata;

            int brojStudenata = 0;
            do {
                System.out.print("Unesite broj studenata za predmetu '" + naziv + "': ");
                try {
                    brojStudenata = input.nextInt();
                    input.nextLine();
                    if (brojStudenata < 1 || brojStudenata > BROJ_STUDENATA)
                        throw (new KriviInputException("Broj studenata mora biti izmedu 1 i " + BROJ_STUDENATA));
                    kriviBrojStudenata = false;
                } catch (KriviInputException e) {
                    logger.warn(e.getMessage(), e);
                    System.out.println(e.getMessage());
                    kriviBrojStudenata = true;
                } catch (InputMismatchException e) {
                    logger.warn("Neispravan unos!", e);
                    System.out.println("Neispravan unos!");
                    input.nextLine();
                    kriviBrojStudenata = true;
                }
            }while(kriviBrojStudenata);*/

                Set<Student> studenti = new HashSet<>();

                predmeti.add(new Predmet(sifra, naziv, ECTS, profesor, studenti));
            }


            for(Profesor profesor: profesori){
                List<Predmet> profesoroviPredmeti = new ArrayList<>();

                for(Predmet predmet: predmeti)
                    if(predmet.getNositelj() == profesor)
                        profesoroviPredmeti.add(predmet);

                nositelji.put(profesor, profesoroviPredmeti);
            }

            krivaKolicinaProfesora = false;
            kriviBrojPredmeta = true;
            for(List<Predmet> predmetList: nositelji.values()){
                if(predmetList.isEmpty()){
                    krivaKolicinaProfesora = true;
                    break;
                }
                if(predmetList.size() > 1)
                    kriviBrojPredmeta = false;
            }

        }while (krivaKolicinaProfesora || kriviBrojPredmeta);

        for(Profesor nositelj: nositelji.keySet()){
            System.out.println("Profesor " + nositelj.getIme() + " " + nositelj.getPrezime() + " predaje sljedeće predmete: ");
            int n = 1;
            for(Predmet predmet: nositelji.get(nositelj))
                System.out.println(n++ + ") " + predmet.getNaziv());
        }

        return predmeti;
    }

    /**
     * Ucitaj studente pomocu danog scannera
     * @param input - scanner s kojim se ucitavaju podatci
     * @return - array ucitanih studenata
     */
    private static List<Student> ucitajStudente(Scanner input){
        List<Student> studenti = new ArrayList<>();

        for(int i = 0; i < BROJ_STUDENATA; i++) {
            System.out.print("Unesite " + (i + 1) + ". studenta: \n");
            System.out.print("Unesite ime studenta: ");
            String ime = input.nextLine();

            System.out.print("Unesite prezime studenta: ");
            String prezime = input.nextLine();

            boolean kriviFormatDatuma;
            LocalDate datumRodenja = null;
            do {
                System.out.print("Unesite datum rodenja studenta " + ime + " " + prezime + " u formatu (dd.MM.yyyy.): ");
                try {
                    datumRodenja = LocalDate.parse(input.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy."));
                    kriviFormatDatuma = false;
                } catch (DateTimeParseException e) {
                    logger.warn("Krivi format datuma!", e);
                    System.out.println("Krivi format datuma!");
                    kriviFormatDatuma = true;
                }
            } while (kriviFormatDatuma);

            System.out.print("Unesite JMBAG studenta: " + ime + " " + prezime + ": ");
            String JMBAG = input.nextLine();

            studenti.add(new Student(ime, prezime, JMBAG, datumRodenja));
        }

        return studenti;
    }

    /**
     * Ucitava ispite pomocu scannera, arraya predmeta i arraya studenata
     * @param input - scanner s kojim se ucitavaju podatci
     * @param predmeti - array dostupnih predmeta
     * @param studenti - array dostupnih studenata
     * @return - array ucitanih ispita
     */
    private static List<Ispit> ucitajIspite(Scanner input, List<Predmet> predmeti, List<Student> studenti){
        List<Ispit> ispiti = new ArrayList<>();

        for(int i = 0; i < BROJ_ISPITA; i++) {
            boolean kriviBrojPredmeta;
            int brojPredmeta = 0;
            do {
                System.out.print("Unesite " + (i + 1) + ". ispitni rok: \n");
                System.out.print("Odaberi predmet: \n");
                for (int j = 0; j < predmeti.size(); j++) {
                    System.out.print((j + 1) + ". " + predmeti.get(j).getNaziv() + "\n");
                }
                System.out.print("Odabir >> ");
                try {
                    brojPredmeta = input.nextInt();
                    input.nextLine();
                    if (brojPredmeta < 1 || brojPredmeta > BROJ_PREDMETA)
                        throw (new KriviInputException("Redni broj predmeta mora biti izmedu 1 i " + BROJ_PREDMETA));
                    kriviBrojPredmeta = false;
                } catch (KriviInputException e) {
                    logger.warn(e.getMessage(), e);
                    System.out.println(e.getMessage());
                    kriviBrojPredmeta = true;
                } catch (InputMismatchException e) {
                    logger.warn("Neispravan unos!", e);
                    System.out.println("Neispravan unos!");
                    input.nextLine();
                    kriviBrojPredmeta = true;
                }
            } while (kriviBrojPredmeta);
            Predmet predmet = predmeti.get(brojPredmeta - 1);

            System.out.print("Unesite naziv dvorane: ");
            String nazivDvorane = input.nextLine();
            System.out.print("Unesite zgradu dvorane: ");
            String nazivZgrade = input.nextLine();

            boolean kriviBrojStudenta;
            int brojStudenta = 0;
            do {
                System.out.print("Odaberi studenta: \n");
                for (int j = 0; j < studenti.size(); j++) {
                    System.out.print((j + 1) + ". " + studenti.get(j).getIme() + " " + studenti.get(j).getPrezime() + "\n");
                }
                System.out.print("Odabir >> ");
                try {
                    brojStudenta = input.nextInt();
                    input.nextLine();
                    if (brojStudenta < 1 || brojStudenta > BROJ_STUDENATA)
                        throw (new KriviInputException("Redni broj studenta mora biti izmedu 1 i " + BROJ_STUDENATA));
                    kriviBrojStudenta = false;
                } catch (KriviInputException e) {
                    logger.warn(e.getMessage(), e);
                    System.out.println(e.getMessage());
                    kriviBrojStudenta = true;
                } catch (InputMismatchException e) {
                    logger.warn("Neispravan unos!", e);
                    System.out.println("Neispravan unos!");
                    input.nextLine();
                    kriviBrojStudenta = true;
                }
            } while (kriviBrojStudenta);
            Student student = studenti.get(brojStudenta - 1);

            boolean krivaOcjena;
            int intOcjena = 0;
            do {
                System.out.print("Unesite ocjenu na ispitu (1-5): ");
                try {
                    intOcjena = input.nextInt();
                    input.nextLine();
                    if (intOcjena < 1 || intOcjena > 5)
                        throw (new KriviInputException("Ocjena mora biti izmedu 1 i 5"));
                    krivaOcjena = false;
                } catch (KriviInputException e) {
                    logger.warn(e.getMessage(), e);
                    System.out.println(e.getMessage());
                    krivaOcjena = true;
                } catch (InputMismatchException e) {
                    logger.warn("Neispravan unos!", e);
                    System.out.println("Neispravan unos!");
                    input.nextLine();
                    krivaOcjena = true;
                }
            } while (krivaOcjena);
            Ocjena ocjena = intToOcjena(intOcjena);


            boolean kriviFormatDatuma;
            LocalDateTime datum = null;
            do {
                System.out.print("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyy.THH:mm): ");
                try {
                    datum = LocalDateTime.parse(input.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm"));
                    kriviFormatDatuma = false;
                } catch (DateTimeParseException e) {
                    logger.warn("Krivi format datuma!", e);
                    System.out.println("Krivi format datuma!");
                    kriviFormatDatuma = true;
                }
            } while (kriviFormatDatuma);

            ispiti.add(new Ispit(predmet, student, ocjena, datum, new Dvorana(nazivDvorane, nazivZgrade)));
        }

        return ispiti;
    }

    private static Ocjena intToOcjena(int intOcjena) {
        return switch (intOcjena) {
            case 1 -> Ocjena.NEDOVOLJAN;
            case 2 -> Ocjena.DOVOLJAN;
            case 3 -> Ocjena.DOBAR;
            case 4 -> Ocjena.VRLODOBAR;
            case 5 -> Ocjena.ODLICAN;
            default -> throw new RuntimeException("Critical error, nije moguce doci do ovog dijela koda!");
        };
    }
}


