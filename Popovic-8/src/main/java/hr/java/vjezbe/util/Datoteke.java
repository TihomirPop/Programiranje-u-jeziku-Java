package hr.java.vjezbe.util;

import hr.java.vjezbe.entitet.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
public class Datoteke {

    public static final String PROFESORI_PATH = "dat/profesori.txt";
    public static final String STUDENTI_PATH = "dat/studenti.txt";
    public static final String PREDMETI_PATH = "dat/predmeti.txt";
    public static final String ISPITI_PATH = "dat/ispiti.txt";
    private static final String OBRAZOVNE_USTANOVE_PATH = "dat/obrazovneUstanove.txt";
    private static final String OBRAZOVNE_USTANOVE_SERIJALIZACIJA_PATH = "dat/obrazovne-ustanove.dat";
    private static final int SIZE_OF_PROFESOR = 5;
    private static final int SIZE_OF_STUDENT = 7;
    private static final int SIZE_OF_PREDMET = 6;
    private static final int SIZE_OF_ISPIT = 7;
    private static final int SIZE_OF_OBRAZOVNA_USTANOVA = 7;

    public static List<Profesor> getProfesori(){
        try(BufferedReader profesoriBuffer = new BufferedReader(new FileReader(PROFESORI_PATH))) {
            System.out.println("Učitavanje profesora…");
            List<String> profesoriLines = profesoriBuffer.lines().collect(Collectors.toList());
            List<Profesor> profesori = new ArrayList<>();
            for(int i = 0; i < profesoriLines.size(); i += SIZE_OF_PROFESOR){
                profesori.add(new Profesor.Builder(
                        Long.parseLong(profesoriLines.get(i)),
                        profesoriLines.get(i + 1),
                        profesoriLines.get(i + 2))
                        .saSifrom(profesoriLines.get(i + 3))
                        .saTitulom(profesoriLines.get(i + 4))
                        .build());
            }
            return profesori;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static List<Student> getStudenti(){
        try(BufferedReader studentiBuffer = new BufferedReader(new FileReader(STUDENTI_PATH))){
            System.out.println("Učitavanje studenata…");
            List<String> studentiLines = studentiBuffer.lines().collect(Collectors.toList());
            List<Student> studenti = new ArrayList<>();
            for(int i = 0; i < studentiLines.size(); i += SIZE_OF_STUDENT) {
                studenti.add(new Student(
                        Long.parseLong(studentiLines.get(i)),
                        studentiLines.get(i + 1),
                        studentiLines.get(i + 2),
                        studentiLines.get(i + 3),
                        LocalDate.parse(studentiLines.get(i + 4), DateTimeFormatter.ofPattern("dd.MM.yyyy.")),
                        intToOcjena(Integer.parseInt(studentiLines.get(i + 5))),
                        intToOcjena(Integer.parseInt(studentiLines.get(i + 6)))
                ));
            }
            return studenti;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static List<Predmet> getPredmeti(List<Profesor> profesori, List<Student> studenti) {
        try(BufferedReader predmetiBuffer = new BufferedReader(new FileReader(PREDMETI_PATH))){
            System.out.println("Učitavanje predmeta…");
            List<String> predmetiLines = predmetiBuffer.lines().collect(Collectors.toList());
            List<Predmet> predmeti = new ArrayList<>();
            for(int i = 0; i < predmetiLines.size(); i += SIZE_OF_PREDMET) {
                Long profesorID = Long.parseLong(predmetiLines.get(i + 4));
                List<Long> studentiID = Arrays.stream(predmetiLines.get(i + 5).split(" ")).map(Long::parseLong).collect(Collectors.toList());
                predmeti.add(new Predmet(
                        Long.parseLong(predmetiLines.get(i)),
                        predmetiLines.get(i + 1),
                        predmetiLines.get(i + 2),
                        Integer.parseInt(predmetiLines.get(i + 3)),
                        profesori.stream().filter(p -> p.getId().equals(profesorID)).collect(Collectors.toList()).get(0),
                        studenti.stream().filter(s -> studentiID.contains(s.getId())).collect(Collectors.toSet())
                ));
            }
            return predmeti;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public static List<Ispit> getIspiti(List<Predmet> predmeti, List<Student> studenti) {
        try(BufferedReader ispitiBuffer = new BufferedReader(new FileReader(ISPITI_PATH))){
            System.out.println("Učitavanje ispita i ocjena…");
            List<String> ispitiLines = ispitiBuffer.lines().collect(Collectors.toList());
            List<Ispit> ispiti = new ArrayList<>();
            for(int i = 0; i < ispitiLines.size(); i += SIZE_OF_ISPIT) {
                Long predmetID = Long.parseLong(ispitiLines.get(i + 1));
                Long studentID = Long.parseLong(ispitiLines.get(i + 2));
                ispiti.add(new Ispit(
                        Long.parseLong(ispitiLines.get(i)),
                        predmeti.stream().filter(p -> p.getId().equals(predmetID)).collect(Collectors.toList()).get(0),
                        studenti.stream().filter(s -> s.getId().equals(studentID)).collect(Collectors.toList()).get(0),
                        intToOcjena(Integer.parseInt(ispitiLines.get(i + 3))),
                        LocalDateTime.parse(ispitiLines.get(i + 4), DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm")),
                        new Dvorana(ispitiLines.get(i + 5), ispitiLines.get(i + 6))
                ));
            }
            return ispiti;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static List<ObrazovnaUstanova> getObrazovneUstanove(List<Predmet> predmeti, List<Profesor> profesori, List<Student> studenti, List<Ispit> ispiti) {
        try(BufferedReader obrazovneUstanoveBuffer = new BufferedReader(new FileReader(OBRAZOVNE_USTANOVE_PATH))){
            System.out.println("Učitavanje obrazovnih ustanova…");
            List<String> obrazovneUstanoveLines = obrazovneUstanoveBuffer.lines().collect(Collectors.toList());
            List<ObrazovnaUstanova> obrazovneUstanove = new ArrayList<>();
            for(int i = 0; i < obrazovneUstanoveLines.size(); i += SIZE_OF_OBRAZOVNA_USTANOVA) {
                Long id = Long.parseLong(obrazovneUstanoveLines.get(i));
                String naziv = obrazovneUstanoveLines.get(i + 1);
                List<Long> predmetiID = Arrays.stream(obrazovneUstanoveLines.get(i + 2).split(" ")).map(Long::parseLong).collect(Collectors.toList());
                List<Long> profesoriID = Arrays.stream(obrazovneUstanoveLines.get(i +3).split(" ")).map(Long::parseLong).collect(Collectors.toList());
                List<Long> studentiID = Arrays.stream(obrazovneUstanoveLines.get(i + 4).split(" ")).map(Long::parseLong).collect(Collectors.toList());
                List<Long> ispitiID = Arrays.stream(obrazovneUstanoveLines.get(i + 5).split(" ")).map(Long::parseLong).collect(Collectors.toList());
                Integer vrstaUstanove = Integer.parseInt(obrazovneUstanoveLines.get(i + 6));

                List<Predmet> predmetiUstanove = predmeti.stream().filter(p -> predmetiID.contains(p.getId())).collect(Collectors.toList());
                List<Profesor> profesoriUstanove = profesori.stream().filter(p -> profesoriID.contains(p.getId())).collect(Collectors.toList());
                List<Student> studentiUstanove = studenti.stream().filter(s -> studentiID.contains(s.getId())).collect(Collectors.toList());
                List<Ispit> ispitiUstanove = ispiti.stream().filter(ispit -> ispitiID.contains(ispit.getId())).collect(Collectors.toList());

                ObrazovnaUstanova obrazovnaUstanova = switch (vrstaUstanove){
                    case 1 -> new VeleucilisteJave(id, naziv, predmetiUstanove, profesoriUstanove, studentiUstanove, ispitiUstanove);
                    case 2 -> new FakultetRacunarstva(id, naziv, predmetiUstanove, profesoriUstanove, studentiUstanove, ispitiUstanove);
                    default -> throw new RuntimeException("Nedozvoljena vrijednost za odabir obrazovne ustanove");
                };
                obrazovneUstanove.add(obrazovnaUstanova);
            }
            return obrazovneUstanove;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private static Ocjena intToOcjena(Integer intOcjena) {
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
