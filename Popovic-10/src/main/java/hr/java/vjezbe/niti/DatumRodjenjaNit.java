package hr.java.vjezbe.niti;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.scene.control.Alert;

import java.util.List;

public class DatumRodjenjaNit implements Runnable{
    @Override
    public void run() {
        try {
            List<Student> studenti = BazaPodataka.getRodendanStudenti();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sretan rodendan");
            alert.setHeaderText("Sretan rodendan sljedecim studentima:");
            if(studenti.isEmpty()){
                alert.setContentText("Niti jedan student danas nema rodendan.");
            }
            else {
                String stringStudenta = studenti.get(0).getIme() + " " + studenti.get(0).getPrezime();
                for(int i = 1; i < studenti.size(); i++)
                    stringStudenta += ", " + studenti.get(i).getIme() + " " + studenti.get(i).getPrezime();
                stringStudenta += ".";
                alert.setContentText(stringStudenta);
            }
            alert.showAndWait();
        } catch (BazaPodatakaException e) {
            throw new RuntimeException(e);
        }
    }
}
