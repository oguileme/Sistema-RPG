package classes;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public abstract class Ficha {
    private int vidaMax;
    private int vidaAtual;
    private int manaMax;
    private int manaAtual;
    private String nome;
    private String classe;
    private int pontosExp;
    private Double deslocamento;
    private int dinheiro;

    private Atributos atributos;
    private List<Rolagem> rolagens;

    private Inventario inventario;


    //metodo construtur da ficha
    public Ficha(int vidaMax, int vidaAtual, int manaMax, int manaAtual,
                 String nome, String classe, int pontosExp,
                 Double deslocamento, int dinheiro,
                 Atributos atributos, List<Rolagem> rolagens,
                 Inventario inventario) {
        this.vidaMax = vidaMax;
        this.vidaAtual = vidaAtual;
        this.manaMax = manaMax;
        this.manaAtual = manaAtual;
        this.nome = nome;
        this.classe = classe;
        this.pontosExp = pontosExp;
        this.deslocamento = deslocamento;
        this.dinheiro = dinheiro;
        this.atributos = atributos;
        this.rolagens = rolagens;
        this.inventario = inventario;
    }

    //**
    // Métodos getters da ficha*/

    public int getVidaMax() {
        return vidaMax;
    }

    public int getVidaAtual() {
        return vidaAtual;
    }

    public int getManaMax() {
        return manaMax;
    }

    public int getManaAtual() {
        return manaAtual;
    }

    public String getNome() {
        return nome;
    }

    public String getClasse() {
        return classe;
    }


    public int getPontosExp() {
        return pontosExp;
    }


    public Double getDeslocamento() {
        return deslocamento;
    }

    public int getDinheiro() {
        return dinheiro;
    }

    //**
    // Métodos setters da ficha*/

    public void setVidaMax(int vidaMax) {
        this.vidaMax = vidaMax;
    }

    public void setVidaAtual(int vidaAtual) {
        this.vidaAtual = vidaAtual;
    }

    public void setManaMax(int manaMax) {
        this.manaMax = manaMax;
    }

    public void setManaAtual(int manaAtual) {
        this.manaAtual = manaAtual;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public void setPontosExp(int pontosExp) {
        this.pontosExp = pontosExp;
    }

    public void setDeslocamento(Double deslocamento) {
        this.deslocamento = deslocamento;
    }

    public void setDinheiro(int dinheiro) {
        this.dinheiro = dinheiro;
    }

    public void setAtributos(Atributos atributos) {
        this.atributos = atributos;
    }

    public void setRolagens(List<Rolagem> rolagem){ this.rolagens = rolagem;}

    public Atributos getAtributos() {
        return atributos;
    }

    public void salvarFicha() {

        try {
            FileWriter arquivo = new FileWriter("Fichas/"+ nome + ".txt");

            arquivo.write("Nome: " + nome + "\n");
            arquivo.write("Classe: " + classe + "\n");
            arquivo.write("Vida Máxima: " + vidaMax + "\n");
            arquivo.write("Vida Atual: " + vidaAtual + "\n");
            arquivo.write("Mana Máxima: " + manaMax + "\n");
            arquivo.write("Mana Atual: " + manaAtual + "\n");
            arquivo.write("Pontos de Experiência: " + pontosExp + "\n");
            arquivo.write("Deslocamento: " + deslocamento + "\n");
            arquivo.write("Dinheiro: " + dinheiro + "\n");

            arquivo.write("\n--- ATRIBUTOS ---\n");
            arquivo.write("Força: " + atributos.getForca() + "\n");
            arquivo.write("Destreza: " + atributos.getDestreza() + "\n");
            arquivo.write("Constituição: " + atributos.getConstituicao() + "\n");
            arquivo.write("Inteligência: " + atributos.getInteligencia() + "\n");
            arquivo.write("Sabedoria: " + atributos.getSabedoria() + "\n");
            arquivo.write("Carisma: " + atributos.getCarisma() + "\n");

            arquivo.close();

        } catch (IOException e) {
            System.out.println("Erro ao salvar a ficha.");
        }
    }

}