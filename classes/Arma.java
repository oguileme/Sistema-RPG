package classes;

import java.io.FileWriter;
import java.io.IOException;

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

    //salvar arma em arquivo .txt
    public void salvarArma() {
        try {
            FileWriter arquivo = new FileWriter("Armas/" + getNome() + ".txt");
            arquivo.write("Nome: " + getNome() + "\n");
            arquivo.write("Quantidade: " + getQuantidade() + "\n");
            arquivo.write("Carga: " + getCarga() + "\n");
            arquivo.write("Descrição: " + getDescricao() + "\n");
            arquivo.write("Tipo de Dano: " + tipoDano + "\n");
            arquivo.write("Alcance: " + alcance + "\n");
            arquivo.write("Pontos para Crítico: " + pontosParaCritico + "\n");
            arquivo.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar arma: " + e.getMessage());
        }
    }
}
