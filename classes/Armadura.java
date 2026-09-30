package classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Override
    public String getTipo() {
        return "Armadura";
    }

    @Override
    protected String getPasta() {
        return "Armaduras";
    }

    //metodo para salvar armadura em arquivo.txt
    public void salvarArmadura() {
        garantirPasta();

        try {
            FileWriter arquivo = new FileWriter(
                    getPasta() + "/" + getNome() + ".txt"
            );
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

    //carrega uma armadura salva em .txt, devolve null se não existir
    public static Armadura carregar(String nome) {

        File arquivo = new File("Armaduras/" + nome + ".txt");

        if (!arquivo.exists()) {
            return null;
        }

        Map<String, String> campos = lerCampos(arquivo.getPath());

        return new Armadura(
                lerTexto(campos, "Nome"),
                lerInteiro(campos, "Quantidade"),
                lerInteiro(campos, "Carga"),
                lerTexto(campos, "Descrição"),
                lerInteiro(campos, "Bônus CA"),
                lerInteiro(campos, "Máximo Destreza"),
                lerInteiro(campos, "Penalidade")
        );
    }

    //lista todas as armaduras salvas na pasta
    public static List<Equipamento> listar() {

        List<Equipamento> armaduras = new ArrayList<>();

        File[] arquivos = new File("Armaduras").listFiles();

        if (arquivos == null) {
            return armaduras;
        }

        for (File arquivo : arquivos) {

            if (arquivo.getName().endsWith(".txt")) {

                Armadura armadura = Armadura.carregar(nomeDoArquivo(arquivo));

                if (armadura != null) {
                    armaduras.add(armadura);
                }
            }
        }

        return armaduras;
    }
}
