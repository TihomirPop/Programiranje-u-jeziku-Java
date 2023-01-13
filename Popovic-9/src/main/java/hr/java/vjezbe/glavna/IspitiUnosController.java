package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.util.Datoteke;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
        List<Profesor> profesori = Datoteke.getProfesori();
        List<Student> studenti = Datoteke.getStudenti();
        List<Predmet> predmeti = Datoteke.getPredmeti(profesori, studenti);
        ispiti = Datoteke.getIspiti(predmeti, studenti);

        predmetChoiceBox.setItems(FXCollections.observableList(predmeti));
        studentChoiceBox.setItems(FXCollections.observableList(studenti));
        ocjenaChoiceBox.setItems(FXCollections.observableList(Arrays.asList(Ocjena.values())));
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

        if(greske.isEmpty()){
            try(BufferedWriter out = new BufferedWriter(new FileWriter(Datoteke.ISPITI_PATH, true))) {
                OptionalLong optionalId = ispiti.stream().mapToLong(p -> p.getId()).max();
                Long id = optionalId.getAsLong() + 1;
                out.write('\n' + id.toString());
                out.write('\n' + predmetChoiceBox.getValue().getId().toString());
                out.write('\n' + studentChoiceBox.getValue().getId().toString());
                out.write('\n' + ocjenaChoiceBox.getValue().getInt().toString());
                out.write('\n' + datum + "T" + vrijeme);
                out.write("\ndvorana");
                out.write("\nzgrada");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else
            Glavna.pogresanUnosPodataka(greske);
    }
}
