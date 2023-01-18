package hr.java.vjezbe.glavna;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StudentiUnosController {
    @FXML
    private TextField jmbagTextField;
    @FXML
    private TextField prezimeTextField;
    @FXML
    private TextField imeTextField;
    @FXML
    private DatePicker datumRodenjaDatePicker;
    public void initialize(){}

    public void spremi() {
        try {
            String jmbag = jmbagTextField.getText();
            String prezime = prezimeTextField.getText();
            String ime = imeTextField.getText();
            datumRodenjaDatePicker.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
            List<String> greske = new ArrayList<>();

            if (jmbag.isEmpty())
                greske.add("JMBAG");
            if (prezime.isEmpty())
                greske.add("prezime");
            if (ime.isEmpty())
                greske.add("ime");
            if (datumRodenjaDatePicker == null)
                greske.add("datum rodenja");

            if (greske.isEmpty()) {
                BazaPodataka.addStudent(new Student(null, ime, prezime, jmbag, datumRodenjaDatePicker.getValue(), null, null));
            } else
                Glavna.pogresanUnosPodataka(greske);
        } catch (BazaPodatakaException e) {
            throw new RuntimeException(e);
        }
    }
}
