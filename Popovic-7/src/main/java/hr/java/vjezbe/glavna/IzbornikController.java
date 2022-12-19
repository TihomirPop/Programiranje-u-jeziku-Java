package hr.java.vjezbe.glavna;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class IzbornikController {
    @FXML
    public void prikaziPretraguProfesora() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("profesori.fxml"));
        prikaziScene(new Scene(fxmlLoader.load(), 400, 500));
    }
    @FXML
    public void prikaziPretraguStudenta() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("studenti.fxml"));
        prikaziScene(new Scene(fxmlLoader.load(), 400, 500));
    }
    @FXML
    public void prikaziPretraguPredmeta() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("predmeti.fxml"));
        prikaziScene(new Scene(fxmlLoader.load(), 400, 500));
    }
    @FXML
    public void prikaziPretraguIspita() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ispiti.fxml"));
        prikaziScene(new Scene(fxmlLoader.load(), 400, 500));
    }
    private void prikaziScene(Scene scene){
        HelloApplication.mainStage.setTitle("Popovic-7");
        HelloApplication.mainStage.setScene(scene);
        HelloApplication.mainStage.show();
    }
}
