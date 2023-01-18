package hr.java.vjezbe.glavna;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PredmetiUnosController {
    @FXML
    private TextField sifraTextField;
    @FXML
    private TextField nazivTextField;
    @FXML
    private TextField ectsTextField;
    @FXML
    private ChoiceBox<Profesor> nositeljChoiceBox;
    @FXML
    private ListView<Student> studentiListView;

    public void initialize() {
        try {
            nositeljChoiceBox.setItems(FXCollections.observableList(BazaPodataka.getProfesori()));
            studentiListView.setItems(FXCollections.observableList(BazaPodataka.getStudenti()));
            studentiListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        } catch (BazaPodatakaException e) {
            throw new RuntimeException(e);
        }
    }

    public void spremi(){
        try {
            String sifra = sifraTextField.getText();
            String naziv = nazivTextField.getText();
            String ects = ectsTextField.getText();

            List<String> greske = new ArrayList<>();

            if(sifra.isEmpty())
                greske.add("sifra");
            if(naziv.isEmpty())
                greske.add("naziv");
            if(ects.isEmpty())
                greske.add("ECTS");
            if(nositeljChoiceBox.getSelectionModel().isEmpty())
                greske.add("nositelj");
            if(studentiListView.getSelectionModel().isEmpty())
                greske.add("studenti");

            if (greske.isEmpty()) {
                Set<Student> studenti = new HashSet<>(studentiListView.getSelectionModel().getSelectedItems());
                BazaPodataka.addPredmet(new Predmet(null, sifra, naziv, Integer.parseInt(ects), nositeljChoiceBox.getValue(), studenti));
            } else
                Glavna.pogresanUnosPodataka(greske);
        } catch (BazaPodatakaException e) {
            throw new RuntimeException(e);
        }
    }
}
