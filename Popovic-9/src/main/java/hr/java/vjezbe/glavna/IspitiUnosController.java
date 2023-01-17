package hr.java.vjezbe.glavna;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.Datoteke;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalLong;

public class IspitiUnosController {
    @FXML
    private ChoiceBox<Predmet> predmetChoiceBox;
    @FXML
    private ChoiceBox<Student> studentChoiceBox;
    @FXML
    private ChoiceBox<Ocjena> ocjenaChoiceBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField vrijemeTextField;

    private List<Ispit> ispiti;

    public void initialize(){
        try {
            predmetChoiceBox.setItems(FXCollections.observableList(BazaPodataka.getPredmeti()));
            studentChoiceBox.setItems(FXCollections.observableList(BazaPodataka.getStudenti()));
            ocjenaChoiceBox.setItems(FXCollections.observableList(Arrays.asList(Ocjena.values())));
        } catch (BazaPodatakaException e) {
            throw new RuntimeException(e);
        }
    }

    public void spremi(){
        String datum = datePicker.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
        String vrijeme = vrijemeTextField.getText();

        List<String> greske = new ArrayList<>();

        if(predmetChoiceBox.getSelectionModel().isEmpty())
            greske.add("predmet");
        if(studentChoiceBox.getSelectionModel().isEmpty())
            greske.add("student");
        if(ocjenaChoiceBox.getSelectionModel().isEmpty())
            greske.add("ocjena");
        if(datum.isEmpty())
            greske.add("datum");
        if(vrijeme.isEmpty())
            greske.add("vrijeme");

        if (greske.isEmpty()) {
            try {
                BazaPodataka.addIspit(new Ispit(null, predmetChoiceBox.getValue(), studentChoiceBox.getValue(), ocjenaChoiceBox.getValue(), LocalDateTime.parse(datum + vrijeme, DateTimeFormatter.ofPattern("dd.MM.yyyy.HH:mm")), null));
            } catch (BazaPodatakaException e) {
                throw new RuntimeException(e);
            }
        }
        else
            Glavna.pogresanUnosPodataka(greske);

    }
}
