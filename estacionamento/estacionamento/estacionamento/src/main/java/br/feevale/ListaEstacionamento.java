package br.feevale;

import java.util.LinkedList;

public class ListaEstacionamento {

    private LinkedList<Ticket> listaEstacionamento;

    public ListaEstacionamento() {
        listaEstacionamento = new LinkedList<>();
    }

    public void adicionar(Ticket t) {
        listaEstacionamento.add(t);
    }

    public LinkedList<Ticket> getLista() {
        return listaEstacionamento;
    }
}
