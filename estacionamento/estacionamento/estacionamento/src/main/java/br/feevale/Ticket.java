package br.feevale;

public class Ticket {

    private String placa;
    private String status;

    private int horas;
    private int minutos;
    private String tempoTotal;

    public Ticket(String placa, String status, int horas, int minutos) {
        this.placa = placa;
        this.status = status;
        this.horas = horas;
        this.minutos = minutos;
        this.tempoTotal = this.horas + "h" + minutos + "min";
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public String getTempoTotal() {
        return tempoTotal;
    }

    public void setTempoTotal(String tempoTotal) {
        this.tempoTotal = tempoTotal;
    }



    @Override
    public String toString() {
        return placa + " - " + status + " - " + getTempoTotal();
    }

    //Métodos da classe
    public double getValor() {
        double valor = 0;

        if ((this.getHoras() == 0)&& (this.getMinutos() < 60)){
            valor = 5.00;
        } else if (this.getHoras() <= 2){
            valor = 6.00;
        } else if (this.getHoras() <= 3){
            valor = 7.00;
        } else if (this.getHoras() < 5){
            valor = 9.00;
        } else if (this.getHoras() >= 5){
            valor = 12.00;
        }
            
        return valor;
    }


    public void adicionarMinuto() {
        this.minutos++; 
        if (this.minutos >= 60) {
            this.minutos = 0;
            this.horas++;
        }

        this.tempoTotal = this.horas + "h " + this.minutos + "min";
    }

}