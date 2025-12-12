package br.feevale;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 760, 520);
        stage.setScene(scene);
        stage.setTitle("Totem Estacionamento");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
    return new FXMLLoader(App.class.getResource("/br/feevale/" + fxml + ".fxml")).load();
}

    public static void main(String[] args) {
        launch();
    }
}