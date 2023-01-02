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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    }
}
