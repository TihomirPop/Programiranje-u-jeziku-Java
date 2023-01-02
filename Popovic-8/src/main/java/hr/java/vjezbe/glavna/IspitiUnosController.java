package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.util.Datoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;

public class IspitiUnosController {
    @FXML
    private TextField nazivTextField;
    @FXML
    private TextField studentTextField;
    @FXML
    private TextField ocjenaTextField;
    @FXML
    private TextField datumIVrijemeTextField;

    public void initialize(){
        List<Student> studenti = Datoteke.getStudenti();
    }

    public void spremi(){
        List<Profesor> profesori = Datoteke.getProfesori();
        List<Student> studenti = Datoteke.getStudenti();
        List<Predmet> predmeti = Datoteke.getPredmeti(profesori, studenti);
        List<Ispit> ispiti = Datoteke.getIspiti(predmeti, studenti);
        String predmet = nazivTextField.getText();
        String student = studentTextField.getText();
        String ocjena = ocjenaTextField.getText();
        String datumIVrijeme = datumIVrijemeTextField.getText();
        List<String> greske = new ArrayList<>();

        if(predmet.isEmpty())
            greske.add("predmet");
        if(student.isEmpty())
            greske.add("student");
        if(ocjena.isEmpty())
            greske.add("ocjena");
        if(datumIVrijeme.isEmpty())
            greske.add("datum i vrijeme");

        if(greske.isEmpty()){
            Long predmetId = predmeti.stream().filter(p -> p.getNaziv().equals(predmet)).toList().get(0).getId();
            Long studentId = studenti.stream().filter(s -> (s.getIme() + " " + s.getPrezime()).equals(student)).toList().get(0).getId();
            try(BufferedWriter out = new BufferedWriter(new FileWriter(Datoteke.ISPITI_PATH, true))) {
                OptionalLong optionalId = predmeti.stream().mapToLong(p -> p.getId()).max();
                Long id = optionalId.getAsLong() + 1;
                out.write('\n' + id.toString());
                out.write('\n' + predmetId.toString());
                out.write('\n' + studentId.toString());
                out.write('\n' + ocjena);
                out.write('\n' + datumIVrijeme);
                out.write("\ndvorana");
                out.write("\nzgrada");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else
            Glavna.pogresanUnosPodataka(greske);
    }
}
