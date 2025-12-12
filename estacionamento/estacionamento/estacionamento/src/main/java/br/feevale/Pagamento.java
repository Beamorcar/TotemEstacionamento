package br.feevale;

public abstract class Pagamento implements Comprovante {

    double valorInicial;

    public Pagamento(double valor){
        this.valorInicial = valor;
    }

    public double getValorInicial() {
        return valorInicial;
    }
    
    public void setValorInicial(double valorInicial) {
        this.valorInicial = valorInicial;
    }

    public abstract double processarPagamento();
}