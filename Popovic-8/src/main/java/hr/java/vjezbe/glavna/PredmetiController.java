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
        predmeti = Datoteke.getPredmeti(Datoteke.getProfesori(), Datoteke.getStudenti());
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

        List<Predmet> filteredPredmeti = predmeti;

        if(!sifra.isEmpty())
            filteredPredmeti = filteredPredmeti.stream().filter(p -> p.getSifra().contains(sifra)).toList();
        if(!naziv.isEmpty())
            filteredPredmeti = filteredPredmeti.stream().filter(p -> p.getNaziv().contains(naziv)).toList();
        if(!ects.isEmpty())
        filteredPredmeti = filteredPredmeti.stream().filter(p -> p.getBrojEctsBodova().toString().equals(ects)).toList();
        if(nositelj.length == 1)
            filteredPredmeti = filteredPredmeti.stream().filter(p -> (p.getNositelj().getIme() + " " + p.getNositelj().getPrezime()).contains(nositelj[0])).toList();
        else if(nositelj.length == 2)
            filteredPredmeti = filteredPredmeti.stream().filter(p -> p.getNositelj().getIme().contains(nositelj[0]) && p.getNositelj().getPrezime().contains(nositelj[1])).toList();

        predmetTableView.setItems(FXCollections.observableList(filteredPredmeti));
    }
}
