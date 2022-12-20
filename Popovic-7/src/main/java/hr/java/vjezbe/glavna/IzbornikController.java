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
    public void prikaziPretraguProfesora() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Glavna.class.getResource("profesori.fxml"));
        prikaziScene(new Scene(fxmlLoader.load(), 400, 500));
    }
    @FXML
    public void prikaziPretraguStudenta() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Glavna.class.getResource("studenti.fxml"));
        prikaziScene(new Scene(fxmlLoader.load(), 400, 500));
    }
    @FXML
    public void prikaziPretraguPredmeta() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Glavna.class.getResource("predmeti.fxml"));
        prikaziScene(new Scene(fxmlLoader.load(), 400, 500));
    }
    @FXML
    public void prikaziPretraguIspita() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Glavna.class.getResource("ispiti.fxml"));
        prikaziScene(new Scene(fxmlLoader.load(), 400, 500));
    }
    private void prikaziScene(Scene scene){
        Glavna.mainStage.setTitle("Popovic-7");
        Glavna.mainStage.setScene(scene);
        Glavna.mainStage.show();
    }
}
