package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ResultadoRolagem {
    private int quantidade;
    private Dado dado;
    private List<Integer> resultados;
    private int subtotal;

    public ResultadoRolagem(int quantidade, Dado dado) {
        setQuantidade(quantidade);
        this.dado = dado;
        this.resultados = new ArrayList<>();
    }

    //cria um termo a partir do texto "2d8", tambem aceita "2D8" e "d8"
    public static ResultadoRolagem de(String texto) {

        String limpo = texto.trim().toLowerCase();

        String[] partes = limpo.split("d");

        if (partes.length != 2) {
            throw new IllegalArgumentException(
                    "Termo invalido: " + texto + " (use o formato 2d8)"
            );
        }

        //sem quantidade na frente, rola um dado so
        String quantidadeTexto = partes[0].isEmpty() ? "1" : partes[0];

        int quantidade;

        try {
            quantidade = Integer.parseInt(quantidadeTexto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Quantidade invalida no termo: " + texto
                            + " (use o formato 2d8)"
            );
        }

        return new ResultadoRolagem(quantidade, Dado.de("d" + partes[1]));
    }

    // Getters e Setters
    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade < 1) {
            throw new IllegalArgumentException(
                    "A quantidade de dados precisa ser pelo menos 1."
            );
        }

        this.quantidade = quantidade;
    }

    public Dado getDado() {
        return dado;
    }

    public void setDado(Dado dado) {
        this.dado = dado;
    }

    public List<Integer> getResultados() {
        return resultados;
    }

    public int getSubtotal() {
        return subtotal;
    }

    //os valores separados por virgula, usados no arquivo
    public String getValores() {
        return String.join(", ", resultados.stream()
                .map(String::valueOf)
                .toList());
    }

    //sorteia a quantidade de dados e guarda os valores
    public void rolar(Random aleatorio) {
        registrar(dado.rolarVariasVezes(quantidade, aleatorio));
    }

    //guarda os valores ja sorteados e recalcula o subtotal, assim o
    //subtotal nunca consegue ficar diferente da soma dos valores
    public void registrar(List<Integer> valores) {
        this.resultados = valores;
        this.subtotal = valores.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    @Override
    public String toString() {
        return quantidade + String.valueOf(dado)
                + ": " + getValores() + " = " + subtotal;
    }
}
