package classes;

import java.io.FileWriter;
import java.io.IOException;

public class Armadura extends Equipamento{
    private int bonusCA;
    private int maxDestreza;
    private int penalidade;

    public Armadura(String nome, int quantidade, int carga, String descricao, int bonusCA, int maxDestreza, int penalidade) {
        super(nome, quantidade, carga, descricao);
        this.bonusCA = bonusCA;
        this.maxDestreza = maxDestreza;
        this.penalidade = penalidade;
    }

    // getters e setters
    public int getBonusCA() {
        return bonusCA;
    }

    public void setBonusCA(int bonusCA) {
        this.bonusCA = bonusCA;
    }

    public int getMaxDestreza() {
        return maxDestreza;
    }

    public void setMaxDestreza(int maxDestreza) {
        this.maxDestreza = maxDestreza;
    }

    public int getPenalidade() {
        return penalidade;
    }

    public void setPenalidade(int penalidade) {
        this.penalidade = penalidade;
    }

    //metodo para salvar armadura em arquivo.txt
    public void salvarArmadura() {
        try {
            FileWriter arquivo = new FileWriter("Armaduras/" + getNome() + ".txt");
            arquivo.write("Nome: " + getNome() + "\n");
            arquivo.write("Quantidade: " + getQuantidade() + "\n");
            arquivo.write("Carga: " + getCarga() + "\n");
            arquivo.write("Descrição: " + getDescricao() + "\n");
            arquivo.write("Bônus CA: " + bonusCA + "\n");
            arquivo.write("Máximo Destreza: " + maxDestreza + "\n");
            arquivo.write("Penalidade: " + penalidade + "\n");
            arquivo.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar armadura: " + e.getMessage());
        }
    }

    
}
