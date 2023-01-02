package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.util.Datoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

public class ProfesoriUnosController {
    private List<Profesor> profesori;
    @FXML
    private TextField sifraProfesoraTextField;
    @FXML
    private TextField prezimeProfesoraTextField;
    @FXML
    private TextField imeProfesoraTextField;
    @FXML
    private TextField titulaProfesoraTextField;

    public void initialize(){
        profesori = Datoteke.getProfesori();
    }

    @FXML
    public void spremi(){
    }
}
