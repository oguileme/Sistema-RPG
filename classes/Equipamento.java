package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Equipamento {
    private String nome;
    private int quantidade;
    private int carga;
    private String descricao;

    public Equipamento(String nome, int quantidade, int carga, String descricao) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.carga = carga;
        this.descricao = descricao;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    // carga de UMA unidade
    public int getCarga() {
        return carga;
    }

    public void setCarga(int carga) {
        this.carga = carga;
    }

    // carga de uma unidade x quantidade
    public int getCargaTotal() {
        return carga * quantidade;
    }

    // tipo do equipamento, sobrescrito pelas classes filhas
    public String getTipo() {
        return "Equipamento";
    }

    // pasta onde o equipamento é salvo, sobrescrita pelas classes filhas
    protected String getPasta() {
        return "Equipamentos";
    }

    // cria a pasta do equipamento caso ela ainda não exista
    protected void garantirPasta() {
        new File(getPasta()).mkdirs();
    }

    //salvar arquivo .txt
    public void salvarEquipamento() {
        garantirPasta();

        try {
            FileWriter arquivo = new FileWriter(
                    getPasta() + "/" + nome + ".txt"
            );
            arquivo.write("Nome: " + nome + "\n");
            arquivo.write("Quantidade: " + quantidade + "\n");
            arquivo.write("Carga: " + carga + "\n");
            arquivo.write("Descrição: " + descricao + "\n");
            arquivo.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar o equipamento.");
        }
    }

    //nome do arquivo sem a extensão
    protected static String nomeDoArquivo(File arquivo) {
        return arquivo.getName().replace(".txt", "");
    }

    //lê um arquivo .txt e devolve cada campo no formato "chave" -> "valor"
    protected static Map<String, String> lerCampos(String caminho) {

        Map<String, String> campos = new LinkedHashMap<>();

        try {

            BufferedReader leitor = new BufferedReader(
                    new FileReader(caminho)
            );

            String linha;

            while ((linha = leitor.readLine()) != null) {

                int separador = linha.indexOf(": ");

                if (separador > 0) {
                    campos.put(
                            linha.substring(0, separador),
                            linha.substring(separador + 2)
                    );
                }
            }

            leitor.close();

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + caminho);
        }

        return campos;
    }

    //lê um campo numérico inteiro
    protected static int lerInteiro(Map<String, String> campos, String chave) {

        String valor = campos.get(chave);

        if (valor == null) {
            return 0;
        }

        return Integer.parseInt(valor.trim());
    }

    //lê um campo numérico com casas decimais
    protected static Double lerDecimal(Map<String, String> campos, String chave) {

        String valor = campos.get(chave);

        if (valor == null) {
            return 0.0;
        }

        return Double.parseDouble(valor.trim());
    }

    //lê um campo de texto
    protected static String lerTexto(Map<String, String> campos, String chave) {

        String valor = campos.get(chave);

        if (valor == null) {
            return "";
        }

        return valor.trim();
    }

    //carrega um equipamento salvo em .txt, devolve null se não existir
    public static Equipamento carregar(String nome) {

        File arquivo = new File("Equipamentos/" + nome + ".txt");

        if (!arquivo.exists()) {
            return null;
        }

        Map<String, String> campos = lerCampos(arquivo.getPath());

        return new Equipamento(
                lerTexto(campos, "Nome"),
                lerInteiro(campos, "Quantidade"),
                lerInteiro(campos, "Carga"),
                lerTexto(campos, "Descrição")
        );
    }

    //lista todos os equipamentos salvos na pasta
    public static List<Equipamento> listar() {

        List<Equipamento> equipamentos = new ArrayList<>();

        File[] arquivos = new File("Equipamentos").listFiles();

        if (arquivos == null) {
            return equipamentos;
        }

        for (File arquivo : arquivos) {

            if (arquivo.getName().endsWith(".txt")) {

                Equipamento equipamento =
                        Equipamento.carregar(nomeDoArquivo(arquivo));

                if (equipamento != null) {
                    equipamentos.add(equipamento);
                }
            }
        }

        return equipamentos;
    }
}
