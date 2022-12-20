package hr.java.vjezbe.glavna;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class IzbornikController {
    @FXML
    MenuBar menuBar;
    @FXML
    public void initialize(){
        menuBar.prefWidthProperty().bind(Glavna.mainStage.widthProperty());
    }
    @FXML
    public void prikaziPretraguProfesora(){
        FXMLLoader fxmlLoader = new FXMLLoader(Glavna.class.getResource("profesori.fxml"));
        Glavna.prikaziScene(fxmlLoader);
    }
    @FXML
    public void prikaziPretraguStudenta(){
        FXMLLoader fxmlLoader = new FXMLLoader(Glavna.class.getResource("studenti.fxml"));
        Glavna.prikaziScene(fxmlLoader);
    }
    @FXML
    public void prikaziPretraguPredmeta(){
        FXMLLoader fxmlLoader = new FXMLLoader(Glavna.class.getResource("predmeti.fxml"));
        Glavna.prikaziScene(fxmlLoader);
    }
    @FXML
    public void prikaziPretraguIspita(){
        FXMLLoader fxmlLoader = new FXMLLoader(Glavna.class.getResource("ispiti.fxml"));
        Glavna.prikaziScene(fxmlLoader);
    }
}
