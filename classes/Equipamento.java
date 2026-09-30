package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Base de Arma, Armadura e Equipamento.
 *
 * A gravação, a leitura, a renomeação e a exclusão ficam aqui uma vez
 * só: cada subclasse acrescenta os seus campos em salvarCamposExtras e
 * monta o próprio objeto em carregarDe.
 */
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

    // arquivo deste equipamento, único ponto de montagem do caminho
    public File getArquivo() {
        return new File(getPasta(), nome + ".txt");
    }

    // =========================
    // GRAVAÇÃO
    // =========================

    /**
     * Grava o equipamento em arquivo, com os campos comuns mais os
     * campos extras da subclasse.
     *
     * @return false se não foi possível gravar
     */
    public boolean salvar() {

        garantirPasta();

        // PrintWriter esconde o erro de escrita numa flag interna e
        // nunca lança, então um disco cheio passaria por sucesso.
        // BufferedWriter propaga a IOException de verdade.
        try (PrintWriter w = new PrintWriter(Files.newBufferedWriter(
                getArquivo().toPath(),
                StandardCharsets.UTF_8))) {

            w.println("Nome: " + nome);
            w.println("Quantidade: " + quantidade);
            w.println("Carga: " + carga);
            w.println("Descrição: " + (descricao == null ? "" : descricao));

            salvarCamposExtras(w);

        } catch (IOException | RuntimeException e) {
            return false;
        }

        return true;
    }

    // Campos extras no arquivo, sobrescrito pelas classes filhas
    protected void salvarCamposExtras(PrintWriter w) {
    }

    // =========================
    // RENOMEAR E EXCLUIR
    // =========================

    /**
     * Renomeia o arquivo e atualiza o nome em memória.
     *
     * @return null se deu certo, ou a mensagem de erro
     */
    public String renomearArquivo(String novoNome) {

        String erro = ValidadorNome.erro(novoNome);

        if (erro != null) {
            return erro;
        }

        novoNome = novoNome.trim();

        File antigo = getArquivo();
        File novo = new File(antigo.getParentFile(), novoNome + ".txt");

        // Se ainda não existe arquivo, não há o que renomear
        if (!antigo.exists()) {
            this.nome = novoNome;
            return null;
        }

        // Já existe outro equipamento com esse nome
        // (ignora se for só troca de maiúscula/minúscula)
        if (novo.exists() && !antigo.getName().equalsIgnoreCase(novo.getName())) {
            return "Já existe um equipamento com esse nome.";
        }

        try {

            Files.move(
                    antigo.toPath(),
                    novo.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );

            this.nome = novoNome;

            return null;

        } catch (IOException e) {
            return "Erro ao renomear o equipamento: " + e.getMessage();
        }
    }

    /**
     * Apaga o arquivo do equipamento.
     *
     * @return true se o arquivo não existe mais
     */
    public boolean excluir() {

        File arquivo = getArquivo();

        if (!arquivo.exists()) {
            return true;
        }

        return arquivo.delete();
    }

    // =========================
    // LEITURA
    // =========================

    // nome do arquivo sem a extensão ".txt" do final
    protected static String nomeDoArquivo(File arquivo) {

        String nome = arquivo.getName();

        if (nome.endsWith(".txt")) {
            nome = nome.substring(0, nome.length() - ".txt".length());
        }

        return nome;
    }

    /**
     * Lê um arquivo .txt e devolve cada campo no formato "chave" -> "valor".
     * Devolve null se o arquivo não pôde ser lido.
     */
    protected static Map<String, String> lerCampos(File arquivo) {

        Map<String, String> campos = new LinkedHashMap<>();

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(
                        Files.newInputStream(arquivo.toPath()),
                        StandardCharsets.UTF_8))) {

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

        } catch (IOException | RuntimeException e) {
            return null;
        }

        return campos;
    }

    // campo numérico inteiro; valor ilegível vira 0 em vez de estourar
    protected static int lerInteiro(Map<String, String> campos, String chave) {

        String valor = campos.get(chave);

        if (valor == null) {
            return 0;
        }

        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // campo numérico com casas decimais; valor ilegível vira 0.0
    protected static Double lerDecimal(Map<String, String> campos, String chave) {

        String valor = campos.get(chave);

        if (valor == null) {
            return 0.0;
        }

        try {
            return Double.parseDouble(valor.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    // campo de texto
    protected static String lerTexto(Map<String, String> campos, String chave) {

        String valor = campos.get(chave);

        if (valor == null) {
            return "";
        }

        return valor.trim();
    }

    // Monta o objeto a partir dos campos já lidos, sobrescrito pelas filhas
    protected static Equipamento carregarDe(Map<String, String> campos) {

        return new Equipamento(
                lerTexto(campos, "Nome"),
                lerInteiro(campos, "Quantidade"),
                lerInteiro(campos, "Carga"),
                lerTexto(campos, "Descrição")
        );
    }

    /**
     * Abre o arquivo nome+".txt" da pasta indicada e devolve seus campos.
     * Devolve null se o arquivo não existir ou não der para ler.
     */
    protected static Map<String, String> lerArquivo(String pasta, String nome) {

        File arquivo = new File(pasta, nome + ".txt");

        if (!arquivo.exists()) {
            return null;
        }

        return lerCampos(arquivo);
    }

    /**
     * Carrega um equipamento salvo em .txt.
     *
     * @return null se não existir ou se o arquivo estiver ilegível
     */
    public static Equipamento carregarEquipamento(String nome) {

        Map<String, String> campos = lerArquivo("Equipamentos", nome);

        return campos == null ? null : carregarDe(campos);
    }

    // =========================
    // LISTAGEM
    // =========================

    /**
     * Carrega um equipamento pelo tipo e pelo nome.
     *
     * Os carregadores das subclasses são estáticos, então não dá para
     * escolher o tipo pelo objeto: é este método que faz a escolha.
     *
     * @return null se o tipo não for conhecido, ou se não existir
     */
    public static Equipamento carregarPorTipo(String tipo, String nome) {

        if (tipo == null || nome == null) {
            return null;
        }

        switch (tipo.trim()) {
            case "Arma":
                return Arma.carregarArma(nome);
            case "Armadura":
                return Armadura.carregarArmadura(nome);
            case "Equipamento":
                return carregarEquipamento(nome);
            default:
                return null;
        }
    }

    /**
     * Lista todos os equipamentos salvos, de todas as pastas.
     */
    public static List<Equipamento> listarTodos() {

        List<Equipamento> todos = new ArrayList<>();

        todos.addAll(listarEquipamentos());
        todos.addAll(Arma.listarArmas());
        todos.addAll(Armadura.listarArmaduras());

        return todos;
    }

    // lista os equipamentos salvos em uma pasta
    protected static List<File> arquivosDaPasta(String pasta) {

        List<File> arquivos = new ArrayList<>();

        File[] encontrados = new File(pasta).listFiles(
                (dir, nome) -> nome.endsWith(".txt")
        );

        if (encontrados == null) {
            return arquivos;
        }

        for (File arquivo : encontrados) {
            arquivos.add(arquivo);
        }

        return arquivos;
    }

    // lista todos os equipamentos simples
    public static List<Equipamento> listarEquipamentos() {

        List<Equipamento> equipamentos = new ArrayList<>();

        for (File arquivo : arquivosDaPasta("Equipamentos")) {

            Equipamento equipamento =
                    carregarEquipamento(nomeDoArquivo(arquivo));

            if (equipamento != null) {
                equipamentos.add(equipamento);
            }
        }

        return equipamentos;
    }
}
