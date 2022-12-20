package hr.java.vjezbe.glavna;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Glavna extends Application {
    static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Glavna.class.getResource("pocetna.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 500);
        stage.setTitle("Popovic-7");
        stage.setScene(scene);
        stage.show();
    }

    public static void prikaziScene(FXMLLoader fxmlLoader){
        try {
            mainStage.setScene(new Scene(fxmlLoader.load(), 400, 500));
            mainStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch();
    }
}
