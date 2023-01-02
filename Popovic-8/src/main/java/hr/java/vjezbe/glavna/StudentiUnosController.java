package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.util.Datoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;

public class StudentiUnosController {
    private List<Student> studenti;
    @FXML
    private TextField jmbagTextField;
    @FXML
    private TextField prezimeTextField;
    @FXML
    private TextField imeTextField;
    @FXML
    private DatePicker datumRodenjaDatePicker;
    public void initialize(){
        studenti = Datoteke.getStudenti();
    }

    public void spremi(){
        String jmbag = jmbagTextField.getText();
        String prezime = prezimeTextField.getText();
        String ime = imeTextField.getText();
        String datumRodenja = datumRodenjaDatePicker.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
        List<String> greske = new ArrayList<>();

        if(jmbag.isEmpty())
            greske.add("JMBAG");
        if(prezime.isEmpty())
            greske.add("prezime");
        if(ime.isEmpty())
            greske.add("ime");
        if(datumRodenja.isEmpty())
            greske.add("datum rodenja");

        if(greske.isEmpty()){
            try(BufferedWriter out = new BufferedWriter(new FileWriter(Datoteke.STUDENTI_PATH, true))) {
                OptionalLong optionalId = studenti.stream().mapToLong(p -> p.getId()).max();
                Long id = optionalId.getAsLong() + 1;
                out.write('\n' + id.toString());
                out.write('\n' + ime);
                out.write('\n' + prezime);
                out.write('\n' + jmbag);
                out.write('\n' + datumRodenja);
                out.write("\n5");
                out.write("\n5");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else
            Glavna.pogresanUnosPodataka(greske);
    }
}
