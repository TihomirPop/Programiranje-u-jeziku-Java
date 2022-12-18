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
        Scene scene = new Scene(fxmlLoader.load(), 400, 500);
        HelloApplication.mainStage.setTitle("Popovic-7");
        HelloApplication.mainStage.setScene(scene);
        HelloApplication.mainStage.show();
    }
}
