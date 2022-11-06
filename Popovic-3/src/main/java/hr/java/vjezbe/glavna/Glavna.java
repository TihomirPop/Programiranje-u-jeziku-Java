package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Glavna {
    private static final int BROJ_PROFESORA = 2;
    private static final int BROJ_PREDMETA = 2;
    private static final int BROJ_STUDENATA = 2;
    private static final int BROJ_ISPITA = 2;

    private static Profesor[] ucitajProfesore(Scanner input){
        Profesor[] profesori = new Profesor[BROJ_PROFESORA];

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

            profesori[i] = new Profesor.Builder(ime, prezime).saSifrom(sifra).saTitulom(titula).build();
        }
        return profesori;
    }

    private static Predmet[] ucitajPredmete(Scanner input, Profesor[] profesori){
        Predmet[] predmeti = new Predmet[BROJ_PREDMETA];

        for(int i = 0; i < BROJ_PREDMETA; i++){
            System.out.print("Unesite " + (i + 1) + ". predmet: \n");

            System.out.print("Unesite sifru predmeta: ");
            String sifra = input.nextLine();

            System.out.print("Unesite naziv predmeta: ");
            String naziv = input.nextLine();

            System.out.print("Unesite broj ECTS bodova za predmet '" + naziv + "': ");
            Integer ECTS = input.nextInt();
            input.nextLine();

            System.out.print("Odaberite profesora: \n");
            for(int j = 0; j < BROJ_PROFESORA; j++){
                System.out.print((j+1) + ". " + profesori[j].getIme() + " " + profesori[j].getPrezime() + "\n");
            }
            System.out.print("Odabir >> ");
            int brojProfesora = input.nextInt();
            input.nextLine();
            Profesor profesor = profesori[brojProfesora - 1];

            System.out.print("Unesite broj studenata za predmetu '" + naziv + "': ");
            int brojStudenata = input.nextInt();
            input.nextLine();
            Student[] studenti = new Student[brojStudenata];

            predmeti[i] = new Predmet(sifra, naziv, ECTS, profesor, studenti);
        }
        return predmeti;
    }

    private static Student[] ucitajStudente(Scanner input){
        Student[] studenti = new Student[BROJ_STUDENATA];

        for(int i = 0; i < BROJ_STUDENATA; i++){
            System.out.print("Unesite " + (i+1) + ". studenta: \n");
            System.out.print("Unesite ime studenta: ");
            String ime = input.nextLine();

            System.out.print("Unesite prezime studenta: ");
            String prezime = input.nextLine();

            System.out.print("Unesite datum rodenja studenta " + ime + " " + prezime + " u formatu (dd.MM.yyyy.): ");
            LocalDate datumRodenja = LocalDate.parse(input.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy."));

            System.out.print("Unesite JMBAG studenta: " + ime + " " + prezime + ": ");
            String JMBAG = input.nextLine();

            studenti[i] = new Student(ime, prezime, JMBAG, datumRodenja);
        }

        return studenti;
    }

    private static Ispit[] ucitajIspite(Scanner input, Predmet[] predmeti, Student[] studenti){
        Ispit[] ispiti = new Ispit[BROJ_ISPITA];

        for(int i = 0; i < BROJ_ISPITA; i++){
            System.out.print("Unesite " + (i+1) + ". ispitni rok: \n");
            System.out.print("Odaberi predmet: \n");
            for(int j = 0; j < predmeti.length; j++){
                System.out.print((j+1) + ". " + predmeti[j].getNaziv() + "\n");
            }
            System.out.print("Odabir >> ");
            int brojPredmeta = input.nextInt();
            input.nextLine();
            Predmet predmet = predmeti[brojPredmeta - 1];

            System.out.print("Unesite naziv dvorane: ");
            String nazivDvorane = input.nextLine();
            System.out.print("Unesite zgradu dvorane: ");
            String nazivZgrade = input.nextLine();

            System.out.print("Odaberi studenta: \n");
            for(int j = 0; j < studenti.length; j++){
                System.out.print((j+1) + ". " + studenti[j].getIme() + " " + studenti[j].getPrezime() + "\n");
            }
            System.out.print("Odabir >> ");
            int brojStudenta = input.nextInt();
            input.nextLine();
            Student student = studenti[brojStudenta - 1];

            System.out.print("Unesite ocjenu na ispitu (1-5): ");
            Integer ocjena = input.nextInt();
            input.nextLine();

            System.out.print("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyy.THH:mm): ");
            LocalDateTime datum = LocalDateTime.parse(input.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm"));

            ispiti[i] = new Ispit(predmet, student, ocjena, datum, new Dvorana(nazivDvorane, nazivZgrade));
        }

        return ispiti;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Unesite broj obrazovnih ustanova: ");
        int brojUstanova = input.nextInt();
        input.nextLine();

        ObrazovnaUstanova[] obrazovneUstanove = new ObrazovnaUstanova[brojUstanova];

        for(int i = 0; i < brojUstanova; i++) {
            System.out.println("Unesite podatke za " + (i+1) + ". obrazovnu ustanovu:");

            Profesor[] profesori = ucitajProfesore(input);
            Predmet[] predmeti = ucitajPredmete(input, profesori);
            Student[] studenti = ucitajStudente(input);
            Ispit[] ispiti = ucitajIspite(input, predmeti, studenti);

            for (Ispit ispit : ispiti) {
                if (ispit.getOcjena().equals(5)) {
                    System.out.print("Student " + ispit.getStudent().getIme() + " " + ispit.getStudent().getPrezime() + " je ostvario ocjenu 'izvrstan' na predmetu '" + ispit.getPredmet().getNaziv() + "' \n");
                }
            }

            for(Predmet predmet: predmeti){
                int indexStudenta = 0;
                Student[] studentiPredmeta = new Student[predmet.getStudenti().length];

                for(Ispit ispit: ispiti)
                    if(ispit.getPredmet() == predmet){
                        studentiPredmeta[indexStudenta] = ispit.getStudent();
                        indexStudenta++;
                    }

                predmet.setStudenti(studentiPredmeta);
            }

            System.out.print("Odaberite obrazovnu ustanovu za navedene podatke koju želite unijeti (1 - Veleučilište Jave, 2 - Fakultet računarstva): ");
            int odabirUstanove = input.nextInt();
            input.nextLine();

            System.out.print("Unesite naziv obrazovne ustanove: ");
            String nazivUstanove = input.nextLine();

            switch(odabirUstanove){
                case 1:
                    obrazovneUstanove[i] = new VeleucilisteJave(nazivUstanove, predmeti, profesori, studenti, ispiti);

                    for(Student student: obrazovneUstanove[i].getStudenti()){
                        System.out.print("Unesite ocjenu završnog rada za studenta: " + student.getIme() + " " + student.getPrezime() + ": ");
                        int zavrsni = input.nextInt();
                        input.nextLine();
                        System.out.print("Unesite ocjenu obrane zavrsnog rada za studenta: " + student.getIme() + " " + student.getPrezime() + ": ");
                        int obrana = input.nextInt();
                        input.nextLine();
                        System.out.println("Konačna ocjena studija studenta " + student.getIme() + " " + student.getPrezime() + " je " + ((VeleucilisteJave)obrazovneUstanove[i]).izracunajKonacnuOcjenuStudijaZaStudenta(((VeleucilisteJave) obrazovneUstanove[i]).filtrirajIspitePoStudentu(obrazovneUstanove[i].getIspiti(), student), zavrsni, obrana));
                    }

                    Student najboljiStudentVeleuciliste = ((VeleucilisteJave)obrazovneUstanove[i]).odrediNajuspjesnijegStudentaNaGodini(2022);
                    System.out.println("Najbolji student 2022. godine je " + najboljiStudentVeleuciliste.getIme() + " " + najboljiStudentVeleuciliste.getPrezime() + " JMBAG: " + najboljiStudentVeleuciliste.getJmbag());
                    break;
                case 2:
                    obrazovneUstanove[i] = new FakultetRacunarstva(nazivUstanove, predmeti, profesori, studenti, ispiti);

                    for(Student student: obrazovneUstanove[i].getStudenti()){
                        System.out.print("Unesite ocjenu završnog rada za studenta: " + student.getIme() + " " + student.getPrezime() + ": ");
                        int zavrsni = input.nextInt();
                        input.nextLine();
                        System.out.print("Unesite ocjenu obrane zavrsnog rada za studenta: " + student.getIme() + " " + student.getPrezime() + ": ");
                        int obrana = input.nextInt();
                        input.nextLine();
                        System.out.println("Konačna ocjena studija studenta " + student.getIme() + " " + student.getPrezime() + " je " + ((FakultetRacunarstva)obrazovneUstanove[i]).izracunajKonacnuOcjenuStudijaZaStudenta(((FakultetRacunarstva) obrazovneUstanove[i]).filtrirajIspitePoStudentu(obrazovneUstanove[i].getIspiti(), student), zavrsni, obrana));
                    }

                    Student najboljiStudent = ((FakultetRacunarstva)obrazovneUstanove[i]).odrediNajuspjesnijegStudentaNaGodini(2022);
                    System.out.println("Najbolji student 2022. godine je " + najboljiStudent.getIme() + " " + najboljiStudent.getPrezime() + " JMBAG: " + najboljiStudent.getJmbag());
                    Student rektorova = ((FakultetRacunarstva)obrazovneUstanove[i]).odrediStudentaZaRektorovuNagradu();
                    System.out.println("Student koji je osvojio rektorovu nagradu je " + rektorova.getIme() + " " + rektorova.getPrezime() + " JMBAG: " + rektorova.getJmbag());
                    break;
            }

        }
    }
}
