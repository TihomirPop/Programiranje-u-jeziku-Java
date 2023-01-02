package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.util.Datoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

public class PredmetiUnosController {
    private List<Predmet> predmeti;
    @FXML
    private TextField sifraTextField;
    @FXML
    private TextField nazivTextField;
    @FXML
    private TextField ectsTextField;
    @FXML
    private TextField nositeljTextField;

    public void initialize(){
        predmeti = Datoteke.getPredmeti(Datoteke.getProfesori(), Datoteke.getStudenti());
    }

    public void spremi(){

    }
}
