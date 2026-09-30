package classes;

/**
 * Regras dos campos numéricos de um equipamento.
 *
 * Bônus CA e penalidade podem ser negativos de propósito, então não
 * entram aqui.
 */
public final class ValidadorEquipamento {

    private ValidadorEquipamento() {
    }

    /**
     * Devolve null se o valor serve, ou a mensagem de erro.
     */
    public static String erroQuantidade(int quantidade) {

        if (quantidade < 1) {
            return "A quantidade precisa ser pelo menos 1.";
        }

        return null;
    }

    public static String erroCarga(int carga) {

        if (carga < 0) {
            return "A carga não pode ser negativa.";
        }

        return null;
    }

    public static String erroAlcance(Double alcance) {

        if (alcance == null) {
            return "Informe o alcance.";
        }

        if (alcance < 0) {
            return "O alcance não pode ser negativo.";
        }

        return null;
    }

    public static String erroMaxDestreza(int maxDestreza) {

        if (maxDestreza < 0) {
            return "O máximo de destreza não pode ser negativo.";
        }

        return null;
    }
}
