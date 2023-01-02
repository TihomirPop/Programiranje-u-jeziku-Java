package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.util.Datoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private TextField nositeljTextField;

    public void spremi(){
        List<Profesor> profesori = Datoteke.getProfesori();
        List<Student> studenti = Datoteke.getStudenti();
        List<Predmet> predmeti = Datoteke.getPredmeti(profesori, studenti);
        String sifra = sifraTextField.getText();
        String naziv = nazivTextField.getText();
        String ects = ectsTextField.getText();
        String nositelj = nositeljTextField.getText();
        List<String> greske = new ArrayList<>();

        if(sifra.isEmpty())
            greske.add("sifra");
        if(naziv.isEmpty())
            greske.add("naziv");
        if(ects.isEmpty())
            greske.add("ECTS");
        if(nositelj.isEmpty())
            greske.add("nositelj");

        if(greske.isEmpty()){
            Long profesorId = profesori.stream().filter(p -> (p.getIme() + " " + p.getPrezime()).equals(nositelj)).toList().get(0).getId();
            try(BufferedWriter out = new BufferedWriter(new FileWriter(Datoteke.PREDMETI_PATH, true))) {
                OptionalLong optionalId = predmeti.stream().mapToLong(p -> p.getId()).max();
                Long id = optionalId.getAsLong() + 1;
                out.write('\n' + id.toString());
                out.write('\n' + sifra);
                out.write('\n' + naziv);
                out.write('\n' + ects);
                out.write('\n' + profesorId.toString());
                out.write("\n0");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else
            Glavna.pogresanUnosPodataka(greske);
    }
}
