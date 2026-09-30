package classes;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Armadura extends Equipamento {

    private int bonusCA;
    private int maxDestreza;
    private int penalidade;

    public Armadura(String nome, int quantidade, int carga, String descricao,
                    int bonusCA, int maxDestreza, int penalidade) {
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
    public TipoEquipamento getTipo() {
        return TipoEquipamento.Armadura;
    }

    @Override
    protected String getPasta() {
        return "Armaduras";
    }

    @Override
    protected void salvarCamposExtras(PrintWriter w) {
        w.println("Bônus CA: " + bonusCA);
        w.println("Máximo Destreza: " + maxDestreza);
        w.println("Penalidade: " + penalidade);
    }

    // método estático: esconde o da base, não sobrescreve
    protected static Equipamento carregarDe(Map<String, String> campos) {

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

    /**
     * Carrega uma armadura salva em .txt.
     *
     * @return null se não existir ou se o arquivo estiver ilegível
     */
    public static Armadura carregarArmadura(String nome) {

        Map<String, String> campos = lerArquivo("Armaduras", nome);

        return campos == null ? null : (Armadura) carregarDe(campos);
    }

    // lista todas as armaduras salvas na pasta
    public static List<Equipamento> listarArmaduras() {

        List<Equipamento> armaduras = new ArrayList<>();

        for (File arquivo : arquivosDaPasta("Armaduras")) {

            Armadura armadura = carregarArmadura(nomeDoArquivo(arquivo));

            if (armadura != null) {
                armaduras.add(armadura);
            }
        }

        return armaduras;
    }
}
