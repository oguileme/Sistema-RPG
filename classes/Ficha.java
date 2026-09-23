package classes;

public abstract class Ficha {
    private int vidaMax;
    private int vidaAtual;
    private int manaMax;
    private int manaAtual;
    private String nome;
    private String classe;
    private String raca;
    private String origem;
    private String divindade;
    private int pontosExp;
    private int idade;
    private Double tamanho;
    private Double deslocamento;
    private int dinheiro;

    //Atributos
    private int forca;
    private int destreza;
    private int constituicao;
    private int inteligencia;
    private int sabedoria;
    private int carisma;

    private Rolagem histRolagem;


    //metodo construtur da ficha
    public Ficha(int vidaMax, int vidaAtual, int manaMax, int manaAtual, String nome, String classe, String raca, String origem, 
    String divindade, int pontosExp, int idade, Double tamanho, Double deslocamento, int dinheiro,
    int forca, int destreza, int constituicao, int inteligencia, int sabedoria, int carisma) {
        this.vidaMax = vidaMax;
        this.vidaAtual = vidaAtual;
        this.manaMax = manaMax;
        this.manaAtual = manaAtual;
        this.nome = nome;
        this.classe = classe;
        this.raca = raca;
        this.origem = origem;
        this.divindade = divindade;
        this.pontosExp = pontosExp;
        this.idade = idade;
        this.tamanho = tamanho;
        this.deslocamento = deslocamento;
        this.dinheiro = dinheiro;
        this.forca = forca;
        this.destreza = destreza;
        this.constituicao = constituicao;
        this.inteligencia = inteligencia;
        this.sabedoria = sabedoria;
        this.carisma = carisma;
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

    public String getRaca() {
        return raca;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDivindade() {
        return divindade;
    }

    public int getPontosExp() {
        return pontosExp;
    }

    public int getIdade() {
        return idade;
    }

    public Double getTamanho() {
        return tamanho;
    }

    public Double getDeslocamento() {
        return deslocamento;
    }

    public int getDinheiro() {
        return dinheiro;
    }

    public int getForca() {
        return forca;
    }

    public int getDestreza() {
        return destreza;
    }

    public int getConstituicao() {
        return constituicao;
    }

    public int getInteligencia() {
        return inteligencia;
    }

    public int getSabedoria() {
        return sabedoria;
    }

    public int getCarisma() {
        return carisma;
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

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public void setDivindade(String divindade) {
        this.divindade = divindade;
    }

    public void setPontosExp(int pontosExp) {
        this.pontosExp = pontosExp;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setTamanho(Double tamanho) {
        this.tamanho = tamanho;
    }

    public void setDeslocamento(Double deslocamento) {
        this.deslocamento = deslocamento;
    }

    public void setDinheiro(int dinheiro) {
        this.dinheiro = dinheiro;
    }

    public void setForca(int forca) {
        this.forca = forca;
    }

    public void setDestreza(int destreza) {
        this.destreza = destreza;
    }

    public void setConstituicao(int constituicao) {
        this.constituicao = constituicao;
    }

    public void setInteligencia(int inteligencia) {
        this.inteligencia = inteligencia;
    }

    public void setSabedoria(int sabedoria) {
        this.sabedoria = sabedoria;
    }

    public void setCarisma(int carisma) {
        this.carisma = carisma;
    }
}