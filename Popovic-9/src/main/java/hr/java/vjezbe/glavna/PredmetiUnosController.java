package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.util.Datoteke;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;

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
        nositeljChoiceBox.setItems(FXCollections.observableList(Datoteke.getProfesori()));
        studentiListView.setItems(FXCollections.observableList(Datoteke.getStudenti()));
        studentiListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

        public void spremi(){
        List<Profesor> profesori = Datoteke.getProfesori();
        List<Student> studenti = Datoteke.getStudenti();
        List<Predmet> predmeti = Datoteke.getPredmeti(profesori, studenti);
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

        if(greske.isEmpty()){
            try(BufferedWriter out = new BufferedWriter(new FileWriter(Datoteke.PREDMETI_PATH, true))) {
                OptionalLong optionalId = predmeti.stream().mapToLong(p -> p.getId()).max();
                Long id = optionalId.getAsLong() + 1;

                ObservableList<Student> selectedStudenti = studentiListView.getSelectionModel().getSelectedItems();
                String studentiId = selectedStudenti.get(0).getId().toString();
                for(int i = 1; i < selectedStudenti.size(); i++)
                    studentiId += ' ' + selectedStudenti.get(i).getId().toString();

                out.write('\n' + id.toString());
                out.write('\n' + sifra);
                out.write('\n' + naziv);
                out.write('\n' + ects);
                out.write('\n' + nositeljChoiceBox.getValue().getId().toString());
                out.write('\n' + studentiId);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else
            Glavna.pogresanUnosPodataka(greske);
    }
}
