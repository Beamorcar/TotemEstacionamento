package br.feevale;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class QuaternaryController {

    @FXML
    private void initialize() {
        new Thread(() -> {
            try {
                Thread.sleep(4000); // 4 segundos
                Platform.runLater(() -> {
                    try {
                        App.setRoot("primary");
                        Dados.adicionarLog("Voltando para a tela inicial...");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
