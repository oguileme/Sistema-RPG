package classes;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Arma extends Equipamento {

    private String tipoDano;
    private Double alcance;
    private int pontosParaCritico;

    public Arma(String nome, int quantidade, int carga, String descricao,
                String tipoDano, Double alcance, int pontosParaCritico) {
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

    @Override
    public String getTipo() {
        return "Arma";
    }

    @Override
    protected String getPasta() {
        return "Armas";
    }

    @Override
    protected void salvarCamposExtras(PrintWriter w) {
        w.println("Tipo de Dano: " + (tipoDano == null ? "" : tipoDano));
        w.println("Alcance: " + (alcance == null ? 0.0 : alcance));
        w.println("Pontos para Crítico: " + pontosParaCritico);
    }

    // método estático: esconde o da base, não sobrescreve
    protected static Equipamento carregarDe(Map<String, String> campos) {

        return new Arma(
                lerTexto(campos, "Nome"),
                lerInteiro(campos, "Quantidade"),
                lerInteiro(campos, "Carga"),
                lerTexto(campos, "Descrição"),
                lerTexto(campos, "Tipo de Dano"),
                lerDecimal(campos, "Alcance"),
                lerInteiro(campos, "Pontos para Crítico")
        );
    }

    /**
     * Carrega uma arma salva em .txt.
     *
     * @return null se não existir ou se o arquivo estiver ilegível
     */
    public static Arma carregarArma(String nome) {

        Map<String, String> campos = lerArquivo("Armas", nome);

        return campos == null ? null : (Arma) carregarDe(campos);
    }

    // lista todas as armas salvas na pasta
    public static List<Equipamento> listarArmas() {

        List<Equipamento> armas = new ArrayList<>();

        for (java.io.File arquivo : arquivosDaPasta("Armas")) {

            Arma arma = carregarArma(nomeDoArquivo(arquivo));

            if (arma != null) {
                armas.add(arma);
            }
        }

        return armas;
    }
}
