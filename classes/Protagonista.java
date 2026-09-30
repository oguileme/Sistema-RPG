package classes;

import java.util.List;

public class Protagonista extends Ficha{
    private Usuario player;

    public Protagonista(int vidaMax, int vidaAtual, int manaMax, int manaAtual,
                        String nome, String classe, int pontosExp,
                        Double deslocamento, int dinheiro,
                        Atributos atributos, List<Rolagem> rolagens,
                        Inventario inventario, Usuario player) {

        super(vidaMax, vidaAtual, manaMax, manaAtual,
                nome, classe, pontosExp, deslocamento, dinheiro,
                atributos, rolagens, inventario);
        this.player = player;
    }
    
}
