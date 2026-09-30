package classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rolagem {
    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String descricao;
    private int bonus;
    private LocalDateTime data;
    private List<ResultadoRolagem> resultados;
    private int resultadoFinal;

    public Rolagem(String descricao, int bonus) {
        this(descricao, bonus, LocalDateTime.now());
    }

    public Rolagem(String descricao, int bonus, LocalDateTime data) {
        this.descricao = descricao;
        this.bonus = bonus;
        this.data = data;
        this.resultados = new ArrayList<>();
    }

    //usado ao ler a rolagem de volta do arquivo
    static DateTimeFormatter formatoData() {
        return FORMATO_DATA;
    }

    // Getters e Setters
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    //a data no formato usado no arquivo
    public String getDataFormatada() {
        return data.format(FORMATO_DATA);
    }

    public List<ResultadoRolagem> getResultados() {
        return resultados;
    }

    public int getResultadoFinal() {
        return resultadoFinal;
    }

    //adiciona um termo ja calculado, usado ao ler do arquivo
    public void addResultado(ResultadoRolagem resultado) {
        resultados.add(resultado);
    }

    //adiciona um termo, por exemplo addTermo(2, 8) para 2d8
    public void addTermo(int quantidade, int faces) {
        resultados.add(new ResultadoRolagem(quantidade, new Dado(faces)));
    }

    //adiciona um termo pelo texto, por exemplo addTermo("2d8")
    public void addTermo(String texto) {
        resultados.add(ResultadoRolagem.de(texto));
    }

    //adiciona varios termos de uma vez, por exemplo
    //adicionarTermos("2d8+3d20+1d10")
    public void adicionarTermos(String formula) {

        for (String termo : formula.split("\\+")) {
            addTermo(termo);
        }
    }

    //sorteia todos os termos e guarda o total
    public void rolar() {
        rolar(new Random());
    }

    //sorteia todos os termos usando o sorteio informado, assim da
    //para testar com uma semente fixa
    public void rolar(Random aleatorio) {

        for (ResultadoRolagem resultado : resultados) {
            resultado.rolar(aleatorio);
        }

        recalcularTotal();
    }

    //total = bonus + soma dos subtotais. Usado ao sortear e
    //tambem ao ler a rolagem de volta do arquivo, para nao
    //confiar no total gravado.
    void recalcularTotal() {

        resultadoFinal = bonus;

        for (ResultadoRolagem resultado : resultados) {
            resultadoFinal += resultado.getSubtotal();
        }
    }

    @Override
    public String toString() {

        StringBuilder texto = new StringBuilder();

        for (ResultadoRolagem resultado : resultados) {

            if (texto.length() > 0) {
                texto.append(" | ");
            }

            texto.append(resultado);
        }

        if (bonus != 0) {
            texto.append(" | bonus ").append(bonus);
        }

        texto.append(" | Total: ").append(resultadoFinal);

        return texto.toString();
    }
}
