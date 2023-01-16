package hr.java.vjezbe.glavna;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.util.Datoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

public class ProfesoriController {
    private List<Profesor> profesori;
    @FXML
    private TextField sifraProfesoraTextField;
    @FXML
    private TextField prezimeProfesoraTextField;
    @FXML
    private TextField imeProfesoraTextField;
    @FXML
    private TextField titulaProfesoraTextField;
    @FXML
    private TableView<Profesor> profesorTableView;
    @FXML
    private TableColumn<Profesor, String> profesorSifraTableColumn;
    @FXML
    private TableColumn<Profesor, String> profesorPrezimeTableColumn;
    @FXML
    private TableColumn<Profesor, String> profesorImeTableColumn;
    @FXML
    private TableColumn<Profesor, String> profesorTitulaTableColumn;


    public void initialize(){
        profesori = BazaPodataka.getProfesori();
        profesorSifraTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSifra()));
        profesorPrezimeTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPrezime()));
        profesorImeTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIme()));
        profesorTitulaTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitula()));
        profesorTableView.setItems(FXCollections.observableList(profesori));
    }

    public void pretraziProfesore(){
        String sifra = sifraProfesoraTextField.getText();
        String prezime = prezimeProfesoraTextField.getText();
        String ime = imeProfesoraTextField.getText();
        String titula = titulaProfesoraTextField.getText();

        List<Profesor> filteredProfesori = profesori;

        if(!sifra.isEmpty())
            filteredProfesori = filteredProfesori.stream().filter(p -> p.getSifra().contains(sifra)).toList();
        if(!prezime.isEmpty())
            filteredProfesori = filteredProfesori.stream().filter(p -> p.getPrezime().contains(prezime)).toList();
        if(!ime.isEmpty())
            filteredProfesori = filteredProfesori.stream().filter(p -> p.getIme().contains(ime)).toList();
        if(!titula.isEmpty())
            filteredProfesori = filteredProfesori.stream().filter(p -> p.getTitula().toLowerCase().contains(titula.toLowerCase())).toList();

        profesorTableView.setItems(FXCollections.observableList(filteredProfesori));
    }
}
