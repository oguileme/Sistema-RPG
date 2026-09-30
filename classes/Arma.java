package classes;

public class Arma extends Equipamento{
    private String tipoDano;
    private Double alcance;
    private int pontosParaCritico;

    public Arma(String nome, int quantidade, int carga, String descricao, String tipoDano, Double alcance, int pontosParaCritico) {
        super(nome, quantidade, carga, descricao);
        this.tipoDano = tipoDano;
        this.alcance = alcance;
        this.pontosParaCritico = pontosParaCritico;
    }

    // Getters e Setters
    public String getTipoDano() {
        return tipoDano;
    }

    public void setTipoDano(String tipoDano) {
        this.tipoDano = tipoDano;
    }


    public Double getAlcance() {
        return alcance;
    }

    public void setAlcance(Double alcance) {
        this.alcance = alcance;
    }

    public int getPontosParaCritico() {
        return pontosParaCritico;
    }

    public void setPontosParaCritico(int pontosParaCritico) {
        this.pontosParaCritico = pontosParaCritico;
    }
}
