package br.feevale;

public class Credito extends Pagamento {

    private int parcelas;
    private final double taxa_juros = 0.02;

    public Credito(double valorInicial, int parcelas){
        super(valorInicial);
        this.parcelas = parcelas;
    }
    
    @Override
    public double processarPagamento(){

        if (parcelas <= 1){
            return this.valorInicial;
        } else {
            double jurosTotal = this.valorInicial * (taxa_juros * this.parcelas);
            return this.valorInicial + jurosTotal;
        }
    }

    @Override
    public String gerarRecibo() {
        double total = processarPagamento();
        double valorParcela = total / parcelas;

        return "=== RECIBO ===\n" +
               "Forma de pagamento: Crédito\n" +
               "Valor inicial: R$ " + String.format("%.2f", valorInicial) + "\n" +
               "Parcelas: " + parcelas + "x de R$ " + String.format("%.2f", valorParcela) + "\n" +
               "Taxa Juros: " + (taxa_juros * 100) + "% a.p.\n" +
               "TOTAL FINAL: R$ " + String.format("%.2f", total);
    }

}