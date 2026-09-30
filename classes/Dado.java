package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dado {
    private int faces;

    public Dado(int faces) {
        setFaces(faces);
    }

    //cria um dado a partir do texto "d8"
    public static Dado de(String texto) {

        String limpo = texto.trim().toLowerCase();

        if (!limpo.startsWith("d")) {
            throw new IllegalArgumentException(
                    "Dado invalido: " + texto + " (use o formato d8)"
            );
        }

        String facesTexto = limpo.substring(1);

        if (facesTexto.isEmpty()) {
            throw new IllegalArgumentException(
                    "Informe as faces do dado, por exemplo: d8"
            );
        }

        int faces;

        try {
            faces = Integer.parseInt(facesTexto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Dado invalido: " + texto + " (use o formato d8)"
            );
        }

        return new Dado(faces);
    }

    // Getters e Setters
    public int getFaces() {
        return faces;
    }

    public void setFaces(int faces) {
        if (faces < 2) {
            throw new IllegalArgumentException(
                    "Um dado precisa de pelo menos 2 faces."
            );
        }

        this.faces = faces;
    }

    //sorteia um valor de 1 ate o numero de faces
    public int rolar(Random aleatorio) {
        return aleatorio.nextInt(faces) + 1;
    }

    //sorteia a quantidade de vezes e devolve os valores
    public List<Integer> rolarVariasVezes(int quantidade, Random aleatorio) {

        List<Integer> valores = new ArrayList<>();

        for (int i = 0; i < quantidade; i++) {
            valores.add(rolar(aleatorio));
        }

        return valores;
    }

    @Override
    public String toString() {
        return "d" + faces;
    }
}
