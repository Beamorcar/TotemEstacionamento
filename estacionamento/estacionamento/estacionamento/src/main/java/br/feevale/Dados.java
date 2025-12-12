package br.feevale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Dados {

    private static Ticket ticketPagamento;
    private static String ultimaplaca = "";
    private static ListaEstacionamento estacionamento = new ListaEstacionamento();
    private static ObservableList<String> listaLogs = FXCollections.observableArrayList();
    private static String reciboFinal;


    public static Ticket getTicketPagamento(){
        return ticketPagamento;
    }

    public static void setTicketPagamento(Ticket ticketPagamento) {
        Dados.ticketPagamento = ticketPagamento;
    }

    public static String getUltimaplaca() {
        return ultimaplaca;
    }

    public static void setUltimaplaca(String ultimaplaca) {
        Dados.ultimaplaca = ultimaplaca;
    }

    public static ListaEstacionamento getEstacionamento() {
        return estacionamento;
    }

    public static void setEstacionamento(ListaEstacionamento estacionamento) {
        Dados.estacionamento = estacionamento;
    }

    public static ObservableList<String> getListaLogs() {
        return listaLogs;
    }

    public static void setListaLogs(ObservableList<String> listaLogs) {
        Dados.listaLogs = listaLogs;
    }

    public static String getReciboFinal() {
        return reciboFinal;
    }

    public static void setReciboFinal(String reciboFinal) {
        Dados.reciboFinal = reciboFinal;
    }

    public static void adicionarLog(String mensagem) {
        
        listaLogs.add(0, mensagem);
    }
    
}