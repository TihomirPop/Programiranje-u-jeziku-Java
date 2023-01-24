package hr.java.vjezbe.glavna;

import hr.java.vjezbe.niti.DatumRodjenjaNit;
import hr.java.vjezbe.niti.NajboljiStudentNit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

public class Glavna extends Application {
    static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Glavna.class.getResource("pocetna.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 500);
        scene.getStylesheets().add("style.css");
        stage.setTitle("Najbolji student jos nije odreden!");
        stage.setScene(scene);
        stage.show();

        Timeline prikazSlavljenika = new Timeline(
                new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Platform.runLater(new DatumRodjenjaNit());
                    }
                }));
        prikazSlavljenika.setCycleCount(Timeline.INDEFINITE);
        prikazSlavljenika.play();

        Timeline prikazNajboljegStudenta = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> Platform.runLater(new NajboljiStudentNit())));
        prikazNajboljegStudenta.setCycleCount(Timeline.INDEFINITE);
        prikazNajboljegStudenta.play();
    }

    public static void setMainStageTitle(String title){
        mainStage.setTitle(title);
    }

    public static void prikaziScene(FXMLLoader fxmlLoader){
        try {
            Scene scene = new Scene(fxmlLoader.load(), 400, 500);
            scene.getStylesheets().add("style.css");
            mainStage.setScene(scene);
            mainStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void pogresanUnosPodataka(List<String> podaci){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Pogrešan unos podataka");
        alert.setHeaderText("Molimo ispravite sljedeće pogreške:");

        String greska = podaci.get(0);
        for(int i = 1; i < podaci.size(); i++)
            greska += ", " + podaci.get(i);
        greska = greska.substring(0, 1).toUpperCase() + greska.substring(1);
        if(podaci.size() == 1)
            alert.setContentText(greska + " je obvezan podatak!");
        else
            alert.setContentText(greska + " su obavezni podaci!");

        alert.showAndWait();
    }
    public static void main(String[] args) {launch();}
}
