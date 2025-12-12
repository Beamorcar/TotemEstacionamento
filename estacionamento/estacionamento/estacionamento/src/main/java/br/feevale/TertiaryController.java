package br.feevale;

import java.io.IOException;
import java.util.Random;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


public class TertiaryController {

    @FXML private Label lbRecibo;

    @FXML private ListView<Ticket> lvlistaVeiculos3;
    @FXML private ListView<String> lvLogs3;

    private volatile boolean simular = true;

    @FXML
    private void initialize() {

        String texto = Dados.getReciboFinal();

        if (texto != null) {
            lbRecibo.setText(texto);
        } else lbRecibo.setText("Erro: Nenhum recibo encontrado.");
       
        lvlistaVeiculos3.getItems().addAll(Dados.getEstacionamento().getLista());

        lvLogs3.setItems(Dados.getListaLogs());
    }

    @FXML
    private void switchToSecondary() throws IOException {

            App.setRoot("secondary");
            Dados.adicionarLog("Trocando para tela 2...");
    }

    private void simulacao(){

        Thread threadSimulacao = new Thread(() -> {
            Random random = new Random();

            while (simular) {
                try {
                    
                    Thread.sleep(5000 + random.nextInt(10000));

                    if (!simular) break;
                    
                    if (random.nextBoolean()) {
                        
                        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                        String novaPlaca = "" + letras.charAt(random.nextInt(26)) 
                                              + letras.charAt(random.nextInt(26)) 
                                              + letras.charAt(random.nextInt(26))
                                              + random.nextInt(10)
                                              + letras.charAt(random.nextInt(26))
                                              + random.nextInt(100);
            
                        Ticket novoTicket = new Ticket(novaPlaca, "Pendente", 0, 1);
                        
                        Platform.runLater(() -> {
                            Dados.getEstacionamento().adicionar(novoTicket); 
                            lvlistaVeiculos3.getItems().add(novoTicket); 
                            Dados.adicionarLog("Veículo " + novaPlaca + " foi registrado.");
                        });


                    }

                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        threadSimulacao.setDaemon(true); 
        threadSimulacao.start();
    }


    private void passagemTempo() {
        Thread threadRelogio = new Thread(() -> {
            while (simular) { 
                try {
                    
                    Thread.sleep(10000); 

                    Platform.runLater(() -> {
                        for (Ticket t : Dados.getEstacionamento().getLista()) {
                            
                            if (t.getStatus().equals("Pendente")) {
                                t.adicionarMinuto();
                            }
                        }
                        lvlistaVeiculos3.refresh();
                    });

                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        threadRelogio.setDaemon(true);
        threadRelogio.start();
    }

    @FXML
    private void confirmarPagamento() throws IOException {
        App.setRoot("quaternary");
        Dados.adicionarLog("Indo para a tela de sucesso (tela 4)...");
    }
}