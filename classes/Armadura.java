package classes;

public class Armadura extends Equipamento{
    private int bonusCA;
    private int maxDestreza;
    private int penalidade;

    public Armadura(String nome, int quantidade, int carga, int bonusCA, int maxDestreza, int penalidade) {
        super(nome, quantidade, carga);
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

    
}
