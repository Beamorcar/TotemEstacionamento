package br.feevale;

public class Pix extends Pagamento{

    private final double desconto = 0.03;

    public Pix (double valor){
        super(valor);
    }
    
    public double processarPagamento(){
        return (valorInicial - (valorInicial * desconto));
    }

    @Override
    public String gerarRecibo() {
        double total = processarPagamento();

        return "=== RECIBO ===\n" +
               "Forma de pagamento: Pix\n" +
               "Valor inicial: R$ " + String.format("%.2f", valorInicial) + "\n" +
               "Desconto: " + (desconto * 100) + "% a.p.\n" +
               "TOTAL FINAL: R$ " + String.format("%.2f", total);
    }
}