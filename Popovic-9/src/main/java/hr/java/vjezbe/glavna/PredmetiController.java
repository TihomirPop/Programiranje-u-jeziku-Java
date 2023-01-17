package hr.java.vjezbe.glavna;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.Datoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

public class PredmetiController {
    private List<Predmet> predmeti;
    @FXML
    private TextField sifraTextField;
    @FXML
    private TextField nazivTextField;
    @FXML
    private TextField ectsTextField;
    @FXML
    private TextField nositeljTextField;
    @FXML
    private TableView<Predmet> predmetTableView;
    @FXML
    private TableColumn<Predmet, String> sifraTableColumn;
    @FXML
    private TableColumn<Predmet, String> nazivTableColumn;
    @FXML
    private TableColumn<Predmet, String> ectsTableColumn;
    @FXML
    private TableColumn<Predmet, String> nositeljTableColumn;


    public void initialize(){
        try {
            predmeti = BazaPodataka.getPredmeti();
        } catch (BazaPodatakaException e) {
            throw new RuntimeException(e);
        }
        sifraTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSifra()));
        nazivTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNaziv()));
        ectsTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBrojEctsBodova().toString()));
        nositeljTableColumn.setCellValueFactory(predmet -> {
            SimpleStringProperty property = new SimpleStringProperty();
            Profesor nositelj = predmet.getValue().getNositelj();
            property.setValue(nositelj.getIme() + " " + nositelj.getPrezime());
            return property;
        });

        predmetTableView.setItems(FXCollections.observableList(predmeti));
    }

    public void pretraziPredmete(){
        String sifra = sifraTextField.getText();
        String naziv = nazivTextField.getText();
        String ects = ectsTextField.getText();
        String nositelj[] = nositeljTextField.getText().split(" ");
        Integer brojEcts = null;
        Profesor profesor = null;

        if(sifra.isEmpty())
            sifra = null;
        if(naziv.isEmpty())
            naziv = null;
        if(!ects.isEmpty())
            brojEcts = Integer.parseInt(ects);
        if(nositelj.length == 2){
            try {
                profesor = BazaPodataka.getFilteredProfesori(new Profesor.Builder(null, nositelj[0], nositelj[1]).build()).get(0);
            } catch (BazaPodatakaException e) {
                throw new RuntimeException(e);
            }
        }
        else if(!nositeljTextField.getText().isEmpty()){
            profesor = new Profesor.Builder(Long.parseLong("-1"), "0", "0").build();
        }

        try {
            predmetTableView.setItems(FXCollections.observableList(BazaPodataka.getFilteredPredmeti(new Predmet(null, sifra, naziv, brojEcts, profesor, null))));
        } catch (BazaPodatakaException e) {
            throw new RuntimeException(e);
        }
    }
}
