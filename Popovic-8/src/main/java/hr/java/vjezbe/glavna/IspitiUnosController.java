package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.util.Datoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class IspitiUnosController {
    private List<Ispit> ispiti;
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

    }
}
