package br.feevale;

import java.io.IOException;
import java.util.Random;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class PrimaryController {

    //lista de registro dos veiculos
    @FXML
    private ListView<Ticket> listaVeiculos;

    //lista de logs 
    @FXML
    private ListView<String> lvLogs;

    //id do text field onde a placa vai ser digitada
    @FXML private TextField tfPlaca;

    //id da label de aviso
    @FXML private Label lblAviso;

    //botao de ir pro pagamento
    @FXML private Button btPagamento;

    //variavel pra guardar o ticket selecionado
    private Ticket ticketSelecionado = null;

    //variavel pra controlar a thread de simulação
    private volatile boolean simular = true;

    @FXML
    private void switchToSecondary() throws IOException {

        if (ticketSelecionado != null){
            Dados.setTicketPagamento(ticketSelecionado);

            Dados.setUltimaplaca(tfPlaca.getText());

            simular = false;
            App.setRoot("secondary");
            Dados.adicionarLog("Trocando para tela 2...");
        } else {
            Dados.adicionarLog("Erro: Nenhum ticket selecionado.");
        }
    
}

    @FXML
    private void initialize() {

        if (Dados.getEstacionamento().getLista().isEmpty()){
            Dados.getEstacionamento().adicionar(new Ticket("ABC1D23", "Pago", 5, 32));
            Dados.getEstacionamento().adicionar(new Ticket("XYZ9A88", "Pendente", 1, 28));
            Dados.getEstacionamento().adicionar(new Ticket("BRA2E19", "Pago", 3, 54));
        }
        
        // adiciona no ListView
        listaVeiculos.getItems().addAll(Dados.getEstacionamento().getLista());
        lvLogs.setItems(Dados.getListaLogs());

        //começa invisivel ate a pessoa digitar a placa corretamente
        btPagamento.setVisible(false);

        //recuperar texto anterior, se a pessoa saiu pra tela 2 e voltou
        String textoSalvo = Dados.getUltimaplaca();

        if (textoSalvo != null && !textoSalvo.isEmpty()){
            tfPlaca.setText(textoSalvo);

            checarPlaca();
        }

        //aciona as simulações
        simulacao();
        passagemTempo();
    }
    
    @FXML
    private void checarPlaca(){

        String placaDigitada = tfPlaca.getText();

            boolean encontrou = false;
            boolean status = false;

            ticketSelecionado = null;

        for (Ticket ticket : Dados.getEstacionamento().getLista()){

            if(ticket.getPlaca().equalsIgnoreCase(placaDigitada)){
                encontrou = true;
                ticketSelecionado = ticket;
                if (ticket.getStatus().equals("Pago")){
                    status = true;
                }
                break;
            } 
        }

        if ((encontrou)&&(!(status))){
            lblAviso.setText("Placa registrada. Por favor, seguir para pagamento.");
            Dados.adicionarLog("Placa " + tfPlaca.getText() + " seguindo para pagamento.");
            btPagamento.setVisible(true);

        } else if ((encontrou)&& (status)){
            lblAviso.setText("O pagamento para este veículo já foi efetuado.");
            btPagamento.setVisible(false);
            Dados.adicionarLog("Advertência: Veículo já pago");
        }
        else {
            lblAviso.setText("Placa não encontrada. Por favor, reveja sua digitação.");
            btPagamento.setVisible(false);
            Dados.adicionarLog("Advertência: Placa não encontrada");

        }

    }

    //método para simular entrada de veiculos

    private void simulacao(){

        Thread threadSimulacao = new Thread(() -> {
            Random random = new Random();


            while (simular) {
                try {
                    
                    Thread.sleep(5000 + random.nextInt(10000));

                    if (!simular) break;
                    
                    if (random.nextBoolean()) {
                        
                        //gerar placa
                        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                        String novaPlaca = "" + letras.charAt(random.nextInt(26)) 
                                              + letras.charAt(random.nextInt(26)) 
                                              + letras.charAt(random.nextInt(26))
                                              + random.nextInt(10)
                                              + letras.charAt(random.nextInt(26))
                                              + random.nextInt(100);
                        
                        //gerar ticket do carro
                        Ticket novoTicket = new Ticket(novaPlaca, "Pendente", 0, 1);
                        
                        Platform.runLater(() -> {
                            Dados.getEstacionamento().adicionar(novoTicket); 
                            listaVeiculos.getItems().add(novoTicket); 
                            Dados.adicionarLog("Veículo " + novaPlaca + " foi registrado.");
                        });


                    }

                } catch (InterruptedException e) {
                    break; // Para a thread se o programa fechar
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
                    
                    //dentro da simulação, 10s = 1min da vida real, para fins de visualização do funcionamento
                    //do codigo
                    Thread.sleep(10000); 

                    Platform.runLater(() -> {
                        for (Ticket t : Dados.getEstacionamento().getLista()) {
                            
                            if (t.getStatus().equals("Pendente")) {
                                t.adicionarMinuto();
                            }
                        }
                        listaVeiculos.refresh();
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