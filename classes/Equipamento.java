package classes;

import java.io.FileWriter;
import java.io.IOException;

public class Equipamento {
    private String nome;
    private int quantidade;
    private int carga;
    private String descricao;

    public Equipamento(String nome, int quantidade, int carga, String descricao) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.carga = carga * quantidade;
        this.descricao = descricao;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getCarga() {
        return carga;
    }

    public void setCarga(int carga) {
        this.carga = carga;
    }

    //salvar arquivo .txt
    public void salvarEquipamento() {
        try {
            FileWriter arquivo = new FileWriter("Equipamentos/" + nome + ".txt");
            arquivo.write("Nome: " + nome + "\n");
            arquivo.write("Quantidade: " + quantidade + "\n");
            arquivo.write("Carga: " + carga + "\n");
            arquivo.write("Descrição: " + descricao + "\n");
            arquivo.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar o equipamento.");
        }
    }
}
