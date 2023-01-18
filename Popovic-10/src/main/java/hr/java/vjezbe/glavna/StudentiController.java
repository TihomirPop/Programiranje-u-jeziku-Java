package hr.java.vjezbe.glavna;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Ocjena;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StudentiController {
    private List<Student> studenti;
    @FXML
    private TextField jmbagTextField;
    @FXML
    private TextField prezimeTextField;
    @FXML
    private TextField imeTextField;
    @FXML
    private DatePicker datumRodenjaDatePicker;
    @FXML
    private TableView<Student> studentTableView;
    @FXML
    private TableColumn<Student, String> jmbagTableColumn;
    @FXML
    private TableColumn<Student, String> prezimeTableColumn;
    @FXML
    private TableColumn<Student, String> imeTableColumn;
    @FXML
    private TableColumn<Student, String> datumRodenjaTableColumn;


    public void initialize(){
        try {
            studenti = BazaPodataka.getStudenti();
        } catch (BazaPodatakaException e) {
            throw new RuntimeException(e);
        }
        jmbagTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getJmbag()));
        prezimeTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPrezime()));
        imeTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIme()));
        datumRodenjaTableColumn.setCellValueFactory(student -> {
            SimpleStringProperty property = new SimpleStringProperty();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
            property.setValue(student.getValue().getDatumRodjenja().format(formatter));
            return property;
        });

        studentTableView.setItems(FXCollections.observableList(studenti));
    }

    public void pretraziStudente(){
        String jmbag = jmbagTextField.getText();
        String prezime = prezimeTextField.getText();
        String ime = imeTextField.getText();
        LocalDate datumRodenja = datumRodenjaDatePicker.getValue();

        if(ime.isEmpty())
            ime = null;
        if(prezime.isEmpty())
            prezime = null;
        if(jmbag.isEmpty())
            jmbag = null;

        try {
            studentTableView.setItems(FXCollections.observableList(BazaPodataka.getFilteredStudenti(new Student(null, ime, prezime, jmbag, datumRodenja, Ocjena.ODLICAN, Ocjena.ODLICAN))));
        } catch (BazaPodatakaException e) {
            throw new RuntimeException(e);
        }
    }
}
