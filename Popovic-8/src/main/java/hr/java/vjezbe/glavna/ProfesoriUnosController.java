package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.util.Datoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;

public class ProfesoriUnosController {
    private List<Profesor> profesori;
    @FXML
    private TextField sifraProfesoraTextField;
    @FXML
    private TextField prezimeProfesoraTextField;
    @FXML
    private TextField imeProfesoraTextField;
    @FXML
    private TextField titulaProfesoraTextField;

    public void initialize(){
        profesori = Datoteke.getProfesori();
    }

    @FXML
    public void spremi(){
        String sifra = sifraProfesoraTextField.getText();
        String prezime = prezimeProfesoraTextField.getText();
        String ime = imeProfesoraTextField.getText();
        String titula = titulaProfesoraTextField.getText();
        List<String> greske = new ArrayList<>();

        if(sifra.isEmpty())
            greske.add("sifra");
        if(prezime.isEmpty())
            greske.add("prezime");
        if(ime.isEmpty())
            greske.add("ime");
        if(titula.isEmpty())
            greske.add("titula");

        if(greske.isEmpty()){
            try(BufferedWriter out = new BufferedWriter(new FileWriter(Datoteke.PROFESORI_PATH, true))) {
                OptionalLong optionalId = profesori.stream().mapToLong(p -> p.getId()).max();
                Long id = optionalId.getAsLong() + 1;
                out.write('\n' + id.toString());
                out.write('\n' + ime);
                out.write('\n' + prezime);
                out.write('\n' + sifra);
                out.write('\n' + titula);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else
            Glavna.pogresanUnosPodataka(greske);
    }
}
