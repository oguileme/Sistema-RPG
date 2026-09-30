package classes;

import java.io.PrintWriter;
import java.util.List;

public class NPC extends Ficha {
    private String personalidade;

    public NPC(int vidaMax, int vidaAtual, int manaMax, int manaAtual,
               String nome, String classe, int pontosExp,
               Double deslocamento, int dinheiro,
               Atributos atributos, List<Rolagem> rolagens,
               Inventario inventario, String personalidade) {

        super(vidaMax, vidaAtual, manaMax, manaAtual,
                nome, classe, pontosExp, deslocamento, dinheiro,
                atributos, rolagens, inventario);
        this.personalidade = personalidade;
    }

    public String getPersonalidade() {
        return personalidade;
    }

    public void setPersonalidade(String personalidade) {
        this.personalidade = personalidade;
    }

    @Override
    public String getTipo() {
        return "NPC";
    }

    @Override
    protected void salvarExtras(PrintWriter w) {
        w.println();
        w.println("Personalidade: " + (personalidade == null ? "" : personalidade));
    }
}