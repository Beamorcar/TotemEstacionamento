package br.feevale;

import java.io.IOException;
import java.util.Random;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class SecondaryController {

    @FXML private Label lblValor;
    @FXML private Label lblTempoTotal;
    @FXML private Label lbquantasVezes;
    @FXML private Label lbfrasePadrao;

    @FXML private RadioButton rbCredito;
    @FXML private RadioButton rbDebito;
    @FXML private RadioButton rbPix;

    @FXML
    private ListView<Ticket> lvlistaVeiculos2;

    @FXML
    private ListView<String> lvLogs2;

    @FXML private Button btFinalizarPagamento;

    @FXML private ComboBox<Integer> cbparcelas; 

    //variavel pra controlar a thread de simulação
    private volatile boolean simular = true;

    private ToggleGroup grupoPagamento;

    @FXML
    private void initialize() {
        grupoPagamento = new ToggleGroup();
        rbCredito.setToggleGroup(grupoPagamento);
        rbDebito.setToggleGroup(grupoPagamento);
        rbPix.setToggleGroup(grupoPagamento);

        cbparcelas.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12);
        cbparcelas.getSelectionModel().selectFirst();

        cbparcelas.setVisible(false);
        lbquantasVezes.setVisible(false);
        lbfrasePadrao.setVisible(false);

        grupoPagamento.selectedToggleProperty().addListener((obs, antigo, novo) -> {
            if (rbCredito.isSelected()) {
                cbparcelas.setVisible(true); 
                lbquantasVezes.setVisible(true);
                lbfrasePadrao.setText("Esta opção possui juros de 2% ao mês a partir de 2 parcelas.");
                lbfrasePadrao.setVisible(true);
            } else if (rbDebito.isSelected()){
                cbparcelas.setVisible(false); 
                lbquantasVezes.setVisible(false);
                lbfrasePadrao.setVisible(false);
            } else if (rbPix.isSelected()){
                cbparcelas.setVisible(false); 
                lbquantasVezes.setVisible(false);
                lbfrasePadrao.setText("Esta opção possui desconto de 3% no valor final.");
                lbfrasePadrao.setVisible(true);

            }
        });


        Ticket ticket = Dados.getTicketPagamento();

        if (ticket != null){
            
            //atualiza tempo e valor de acordo com o ticket 
            lblValor.setText("R$ " + String.format("%.2f", ticket.getValor()));

            lblTempoTotal.setText(ticket.getTempoTotal());
        }


        //adciona no listview
        lvlistaVeiculos2.getItems().addAll(Dados.getEstacionamento().getLista());
        lvLogs2.setItems(Dados.getListaLogs());

        //fazer as simulações
        simulacao();
        passagemTempo();

        btFinalizarPagamento.setVisible(false);
    }

    @FXML private void liberarBotao(){
        btFinalizarPagamento.setVisible(true);
    }

    @FXML
    private void finalizarPagamento() {

        Ticket ticket = Dados.getTicketPagamento();
        if (ticket == null) return;

        Pagamento pagamentoRealizado = null;
        String tipoPagamento = null;

        if (rbPix.isSelected()) {
            pagamentoRealizado = new Pix(ticket.getValor());
            tipoPagamento = "Pix";
        } 
        else if (rbDebito.isSelected()) {
            pagamentoRealizado = new Debito(ticket.getValor());
            tipoPagamento = "Débito";
        } 
        else if (rbCredito.isSelected()) {
            int qtdParcelas = cbparcelas.getValue();
            
            pagamentoRealizado = new Credito(ticket.getValor(), qtdParcelas);
            tipoPagamento = "Crédito";
        }

        if (pagamentoRealizado != null) {
            double valorFinal = pagamentoRealizado.processarPagamento();
            Dados.adicionarLog("Pago: R$ " + String.format("%.2f", valorFinal) + " no " + tipoPagamento);
            System.out.println(pagamentoRealizado.gerarRecibo());
            Dados.setReciboFinal(pagamentoRealizado.gerarRecibo());
            
            try {
                App.setRoot("tertiary");
                Dados.adicionarLog("Trocando para tela 3...");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       
    }

    @FXML private void switchToTerciary() throws IOException {
        App.setRoot("tertiary");
        Dados.adicionarLog("Trocando para tela 3...");
    }
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
        Dados.adicionarLog("Trocando para tela 1...");
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
                            lvlistaVeiculos2.getItems().add(novoTicket); 
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
                        lvlistaVeiculos2.refresh();
                    });

                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        threadRelogio.setDaemon(true);
        threadRelogio.start();
    }
    
}