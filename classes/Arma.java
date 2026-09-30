package classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Override
    public String getTipo() {
        return "Arma";
    }

    @Override
    protected String getPasta() {
        return "Armas";
    }

    //salvar arma em arquivo .txt
    public void salvarArma() {
        garantirPasta();

        try {
            FileWriter arquivo = new FileWriter(
                    getPasta() + "/" + getNome() + ".txt"
            );
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

    //carrega uma arma salva em .txt, devolve null se não existir
    public static Arma carregar(String nome) {

        File arquivo = new File("Armas/" + nome + ".txt");

        if (!arquivo.exists()) {
            return null;
        }

        Map<String, String> campos = lerCampos(arquivo.getPath());

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

    //lista todas as armas salvas na pasta
    public static List<Equipamento> listar() {

        List<Equipamento> armas = new ArrayList<>();

        File[] arquivos = new File("Armas").listFiles();

        if (arquivos == null) {
            return armas;
        }

        for (File arquivo : arquivos) {

            if (arquivo.getName().endsWith(".txt")) {

                Arma arma = Arma.carregar(nomeDoArquivo(arquivo));

                if (arma != null) {
                    armas.add(arma);
                }
            }
        }

        return armas;
    }
}
