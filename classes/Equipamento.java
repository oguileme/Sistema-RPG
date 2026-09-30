package classes;
public class Equipamento {
    private String nome;
    private int quantidade;
    private int carga;

    public Equipamento(String nome, int quantidade, int carga) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.carga = carga * quantidade;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
}
