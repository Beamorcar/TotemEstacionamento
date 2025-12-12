package br.feevale;

public class Debito extends Pagamento {

    public Debito (double valor){
        super(valor);
    }

    @Override
    public double processarPagamento(){
        return this.valorInicial;
    }

    @Override
    public String gerarRecibo() {
        double total = processarPagamento();

        return "=== RECIBO ===\n" +
               "Forma de pagamento: Débito\n" +
               "TOTAL FINAL: R$ " + String.format("%.2f", total);
    }

}
