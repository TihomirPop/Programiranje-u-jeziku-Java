package hr.java.vjezbe.glavna;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class ProfesoriUnosController {
    @FXML
    private TextField sifraProfesoraTextField;
    @FXML
    private TextField prezimeProfesoraTextField;
    @FXML
    private TextField imeProfesoraTextField;
    @FXML
    private TextField titulaProfesoraTextField;

    public void initialize(){}

    @FXML
    public void spremi(){
        try {
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
                BazaPodataka.addProfesor(new Profesor.Builder(null, ime, prezime).saTitulom(titula).saSifrom(sifra).build());
            }
            else
                Glavna.pogresanUnosPodataka(greske);
        } catch (BazaPodatakaException e) {
            throw new RuntimeException(e);
        }
    }
}
