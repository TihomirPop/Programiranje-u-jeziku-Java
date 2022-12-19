package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.util.Datoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

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
        studenti = Datoteke.getStudenti();
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

        List<Student> filteredStudenti = studenti;
        if(!jmbag.isEmpty())
            filteredStudenti = filteredStudenti.stream().filter(s -> s.getJmbag().contains(jmbag)).toList();
        if(!prezime.isEmpty())
            filteredStudenti = filteredStudenti.stream().filter(s -> s.getPrezime().contains(prezime)).toList();
        if(!ime.isEmpty())
            filteredStudenti = filteredStudenti.stream().filter(s -> s.getIme().contains(ime)).toList();
        if(datumRodenja != null)
            filteredStudenti = filteredStudenti.stream().filter(s -> s.getDatumRodjenja().equals(datumRodenja)).toList();

        studentTableView.setItems(FXCollections.observableList(filteredStudenti));
    }
}
