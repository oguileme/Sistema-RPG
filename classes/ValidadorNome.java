package classes;

/**
 * Regras de nome usadas antes de gravar qualquer coisa em disco.
 *
 * Os nomes viram nome de arquivo, e um nome com "/" ou "\" cria um
 * caminho cuja pasta não existe: a gravação falha, mas como o erro
 * só ia para o console, a tela chegava a anunciar sucesso.
 */
public final class ValidadorNome {

    private static final String CARACTERES_ILEGAIS = "\\/:*?\"<>|";

    private ValidadorNome() {
    }

    /**
     * Devolve null se o nome serve para arquivo, ou a mensagem de
     * erro se não serve.
     */
    public static String erro(String nome) {

        if (nome == null) {
            return "Digite um nome.";
        }

        String limpo = nome.trim();

        if (limpo.isEmpty()) {
            return "Digite um nome.";
        }

        // "\" precisa ser escapado dentro da regex
        if (limpo.matches(".*[\\\\/:*?\"<>|].*")) {
            return "O nome não pode conter: "
                    + CARACTERES_ILEGAIS;
        }

        return null;
    }

    /**
     * Devolve o nome sem espaços nas pontas, ou null se for inválido.
     */
    public static String limpar(String nome) {

        return erro(nome) == null ? nome.trim() : null;
    }
}
