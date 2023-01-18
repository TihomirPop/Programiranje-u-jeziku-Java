package hr.java.vjezbe.glavna;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Ocjena;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class IspitiController {
    private List<Ispit> ispiti;
    @FXML
    private TextField nazivTextField;
    @FXML
    private TextField studentTextField;
    @FXML
    private TextField ocjenaTextField;
    @FXML
    private TextField datumIVrijemeTextField;
    @FXML
    private TableView<Ispit> ispitTableView;
    @FXML
    private TableColumn<Ispit, String> nazivTableColumn;
    @FXML
    private TableColumn<Ispit, String> studentTableColumn;
    @FXML
    private TableColumn<Ispit, String> ocjenaTableColumn;
    @FXML
    private TableColumn<Ispit, String> datumIVrijemeTableColumn;


    public void initialize(){
        try {
            ispiti = BazaPodataka.getIspiti();
        } catch (BazaPodatakaException e) {
            throw new RuntimeException(e);
        }
        nazivTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPredmet().getNaziv()));
        studentTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStudent().getIme() + " " + data.getValue().getStudent().getPrezime()));
        ocjenaTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOcjena().getInt().toString()));
        datumIVrijemeTableColumn.setCellValueFactory(ispit -> {
            SimpleStringProperty property = new SimpleStringProperty();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm");
            property.setValue(ispit.getValue().getDatumIVrijeme().format(formatter));
            return property;
        });

        ispitTableView.setItems(FXCollections.observableList(ispiti));
    }

    public void pretraziIspite(){
        String naziv = nazivTextField.getText();
        String student[] = studentTextField.getText().split(" ");
        String ocjenaString = ocjenaTextField.getText();
        String datumIVrijemeString = datumIVrijemeTextField.getText();
        Ocjena ocjena = null;
        LocalDateTime datumIVrijeme = null;

        if(!ocjenaString.isEmpty())
            ocjena = Ocjena.intToOcjena(Integer.parseInt(ocjenaString));
        if(!datumIVrijemeString.isEmpty())
            datumIVrijeme = LocalDateTime.parse(datumIVrijemeString, DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm"));


        try {
            List<Ispit> filteredIspiti = BazaPodataka.getFilteredIspiti(new Ispit(null, null, null, ocjena, datumIVrijeme, null));

            if(!naziv.isEmpty())
                filteredIspiti = filteredIspiti.stream().filter(i -> i.getPredmet().getNaziv().contains(naziv)).toList();
            if(student.length == 1)
                filteredIspiti = filteredIspiti.stream().filter(i -> (i.getStudent().getIme() + " " + i.getStudent().getPrezime()).contains(student[0])).toList();
            else if(student.length == 2)
                filteredIspiti = filteredIspiti.stream().filter(i -> i.getStudent().getIme().contains(student[0]) && i.getStudent().getPrezime().contains(student[1])).toList();
            ispitTableView.setItems(FXCollections.observableList(filteredIspiti));
        } catch (BazaPodatakaException e) {
            throw new RuntimeException(e);
        }
    }
}
