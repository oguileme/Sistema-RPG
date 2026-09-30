package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;

public abstract class Ficha {
    private int vidaMax;
    private int vidaAtual;
    private int manaMax;
    private int manaAtual;
    private String nome;
    private String classe;
    private int pontosExp;
    private Double deslocamento;
    private int dinheiro;

    private Atributos atributos;
    private List<Rolagem> rolagens;

    private Inventario inventario;

    // Nome da campanha a que a ficha pertence
    private String nomeCampanha;

    // Login do usuário que criou a ficha
    private String donoUsuario;

    //metodo construtur da ficha
    public Ficha(int vidaMax, int vidaAtual, int manaMax, int manaAtual,
                 String nome, String classe, int pontosExp,
                 Double deslocamento, int dinheiro,
                 Atributos atributos, List<Rolagem> rolagens,
                 Inventario inventario) {
        this.vidaMax = vidaMax;
        this.vidaAtual = vidaAtual;
        this.manaMax = manaMax;
        this.manaAtual = manaAtual;
        this.nome = nome;
        this.classe = classe;
        this.pontosExp = pontosExp;
        this.deslocamento = deslocamento;
        this.dinheiro = dinheiro;
        this.atributos = atributos;
        this.rolagens = rolagens == null ? new ArrayList<>() : rolagens;
        this.inventario = inventario;
    }

    //**
    // Métodos getters da ficha*/

    public int getVidaMax() {
        return vidaMax;
    }

    public int getVidaAtual() {
        return vidaAtual;
    }

    public int getManaMax() {
        return manaMax;
    }

    public int getManaAtual() {
        return manaAtual;
    }

    public String getNome() {
        return nome;
    }

    public String getClasse() {
        return classe;
    }

    public int getPontosExp() {
        return pontosExp;
    }

    public Double getDeslocamento() {
        return deslocamento;
    }

    public int getDinheiro() {
        return dinheiro;
    }

    public String getNomeCampanha() {
        return nomeCampanha;
    }

    //**
    // Métodos setters da ficha*/

    public void setVidaMax(int vidaMax) {
        this.vidaMax = vidaMax;
    }

    public void setVidaAtual(int vidaAtual) {
        this.vidaAtual = vidaAtual;
    }

    public void setManaMax(int manaMax) {
        this.manaMax = manaMax;
    }

    public void setManaAtual(int manaAtual) {
        this.manaAtual = manaAtual;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public void setPontosExp(int pontosExp) {
        this.pontosExp = pontosExp;
    }

    public void setDeslocamento(Double deslocamento) {
        this.deslocamento = deslocamento;
    }

    public void setDinheiro(int dinheiro) {
        this.dinheiro = dinheiro;
    }

    public void setAtributos(Atributos atributos) {
        this.atributos = atributos;
    }

    public void setRolagens(List<Rolagem> rolagem) {
        this.rolagens = rolagem;
    }

    public void setNomeCampanha(String nomeCampanha) {
        this.nomeCampanha = nomeCampanha;
    }

    public String getDonoUsuario() {
        return donoUsuario;
    }

    public void setDonoUsuario(String donoUsuario) {
        this.donoUsuario = donoUsuario;
    }

    public List<Rolagem> getRolagens() {
        return rolagens;
    }

    //guarda uma rolagem no historico da ficha
    public void addRolagem(Rolagem rolagem) {
        this.rolagens.add(rolagem);
    }

    public Atributos getAtributos() {
        return atributos;
    }

    // "Protagonista" ou "NPC" (cada subclasse define o seu)
    public abstract String getTipo();

    // Permite que as subclasses gravem campos extras no arquivo
    protected void salvarExtras(PrintWriter w) {
    }

    // Pasta onde ficam as fichas da campanha
    private String pastaCampanha() {
        return (nomeCampanha == null || nomeCampanha.trim().isEmpty())
                ? "SemCampanha"
                : nomeCampanha;
    }

    // Arquivo desta ficha: Fichas/<campanha>/<nome>.txt
    public File getArquivo() {
        return new File("Fichas/" + pastaCampanha(), nome + ".txt");
    }

    // Renomeia o arquivo da ficha para o novo nome.
    // Retorna false se já existir outra ficha com esse nome ou se der erro.
    public boolean renomearArquivo(String novoNome) {

        File antigo = getArquivo();
        File novo = new File(antigo.getParentFile(), novoNome + ".txt");

        // Se ainda não existe arquivo, não há o que renomear
        if (!antigo.exists()) {
            return true;
        }

        // Já existe outra ficha com o nome novo
        // (ignora se for só troca de maiúscula/minúscula)
        if (novo.exists() && !antigo.getName().equalsIgnoreCase(novo.getName())) {
            return false;
        }

        try {
            Files.move(antigo.toPath(), novo.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // Exclui o arquivo da ficha. Retorna true se foi apagado.
    public boolean excluirFicha() {
        return getArquivo().delete();
    }

    public void salvarFicha() {

        File pasta = new File("Fichas/" + pastaCampanha());
        pasta.mkdirs();

        try (PrintWriter w = new PrintWriter(new File(pasta, nome + ".txt"), "UTF-8")) {

            w.println("Tipo: " + getTipo());
            w.println("Campanha: " + pastaCampanha());
            w.println("Dono: " + (donoUsuario == null ? "" : donoUsuario));
            w.println("Nome: " + nome);
            w.println("Classe: " + classe);
            w.println("Vida Máxima: " + vidaMax);
            w.println("Vida Atual: " + vidaAtual);
            w.println("Mana Máxima: " + manaMax);
            w.println("Mana Atual: " + manaAtual);
            w.println("Pontos de Experiência: " + pontosExp);
            w.println("Deslocamento: " + deslocamento);
            w.println("Dinheiro: " + dinheiro);

            w.println();
            w.println("--- ATRIBUTOS ---");
            w.println("Força: " + atributos.getForca());
            w.println("Destreza: " + atributos.getDestreza());
            w.println("Constituição: " + atributos.getConstituicao());
            w.println("Inteligência: " + atributos.getInteligencia());
            w.println("Sabedoria: " + atributos.getSabedoria());
            w.println("Carisma: " + atributos.getCarisma());

            salvarExtras(w);

            salvarRolagens(w);

        } catch (IOException e) {
            System.out.println("Erro ao salvar a ficha: " + e.getMessage());
        }
    }

    // Grava o histórico de rolagens da ficha
    private void salvarRolagens(PrintWriter w) {

        w.println();
        w.println("--- ROLAGENS ---");

        for (Rolagem rolagem : rolagens) {

            w.println(
                    "Rolagem: " + rolagem.getDataFormatada()
                            + " | Descrição: " + rolagem.getDescricao()
                            + " | Bônus: " + rolagem.getBonus()
                            + " | Total: " + rolagem.getResultadoFinal()
            );

            for (ResultadoRolagem resultado : rolagem.getResultados()) {

                w.println(
                        "Termo: " + resultado.getQuantidade()
                                + resultado.getDado()
                                + " | Valores: " + resultado.getValores()
                                + " | Subtotal: " + resultado.getSubtotal()
                );
            }
        }
    }

    // Carrega uma ficha do arquivo (Protagonista ou NPC, conforme a linha "Tipo")
    public static Ficha carregarFicha(File arquivo) {

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(arquivo), StandardCharsets.UTF_8))) {

            Map<String, String> d = new HashMap<>();
            String linha;

            //linhas de rolagem em ordem, porque "Rolagem:" e "Termo:"
            //se repetem e um mapa achataria todas menos a ultima
            List<String> linhasDeRolagem = new ArrayList<>();

            boolean dentroDasRolagens = false;

            while ((linha = leitor.readLine()) != null) {

                String linhaSemEspaco = linha.trim();

                if (linhaSemEspaco.startsWith("--- ROLAGENS")) {
                    dentroDasRolagens = true;
                    continue;
                }

                //qualquer outra secao encerra o historico
                if (dentroDasRolagens && linhaSemEspaco.startsWith("---")) {
                    dentroDasRolagens = false;
                    continue;
                }

                if (dentroDasRolagens) {

                    if (!linhaSemEspaco.isEmpty()) {
                        linhasDeRolagem.add(linhaSemEspaco);
                    }

                    continue;
                }

                int i = linha.indexOf(": ");

                if (i > 0) {

                    d.put(
                            linha.substring(0, i),
                            linha.substring(i + 2)
                    );

                } else if (linha.endsWith(":")) {

                    d.put(
                            linha.substring(0, linha.length() - 1),
                            ""
                    );
                }
            }

            List<Rolagem> rolagens = lerRolagens(linhasDeRolagem);

            Atributos atributos = new Atributos(
                    Integer.parseInt(d.get("Força")),
                    Integer.parseInt(d.get("Destreza")),
                    Integer.parseInt(d.get("Constituição")),
                    Integer.parseInt(d.get("Inteligência")),
                    Integer.parseInt(d.get("Sabedoria")),
                    Integer.parseInt(d.get("Carisma"))
            );

            int vidaMax = Integer.parseInt(d.get("Vida Máxima"));
            int vidaAtual = Integer.parseInt(d.get("Vida Atual"));
            int manaMax = Integer.parseInt(d.get("Mana Máxima"));
            int manaAtual = Integer.parseInt(d.get("Mana Atual"));
            int exp = Integer.parseInt(d.get("Pontos de Experiência"));
            Double desloc = Double.valueOf(d.get("Deslocamento"));
            int dinheiro = Integer.parseInt(d.get("Dinheiro"));

            Ficha ficha;

            if ("NPC".equals(d.get("Tipo"))) {

                String personalidade = d.get("Personalidade");

                ficha = new NPC(vidaMax, vidaAtual, manaMax, manaAtual,
                        d.get("Nome"), d.get("Classe"), exp, desloc, dinheiro,
                        atributos, rolagens, null,
                        personalidade == null ? "" : personalidade);

            } else {

                ficha = new Protagonista(vidaMax, vidaAtual, manaMax, manaAtual,
                        d.get("Nome"), d.get("Classe"), exp, desloc, dinheiro,
                        atributos, rolagens, null, null);
            }

            ficha.setNomeCampanha(d.get("Campanha"));

            String dono = d.get("Dono");
            ficha.setDonoUsuario(dono == null || dono.trim().isEmpty() ? null : dono.trim());

            return ficha;

        } catch (IOException | NumberFormatException e) {
            return null;
        }
    }

    // Reconstrói as rolagens a partir das linhas da seção
    // "--- ROLAGENS ---". Uma linha de "Rolagem:" começa uma rolagem
    // e as linhas de "Termo:" seguintes entram nela.
    private static List<Rolagem> lerRolagens(List<String> linhas) {

        List<Rolagem> rolagens = new ArrayList<>();
        Rolagem atual = null;

        for (String linha : linhas) {

            if (linha.startsWith("Rolagem:")) {

                atual = lerRolagem(linha);

                if (atual != null) {
                    rolagens.add(atual);
                }

            } else if (linha.startsWith("Termo:") && atual != null) {

                ResultadoRolagem termo = lerTermo(linha);

                if (termo != null) {
                    atual.addResultado(termo);
                }
            }
        }

        //o total é recalculado em vez de confiar no que foi gravado
        for (Rolagem rolagem : rolagens) {
            rolagem.recalcularTotal();
        }

        return rolagens;
    }

    // "Rolagem: 2026-09-30 12:00:00 | Descrição: x | Bônus: 5 | Total: 40"
    private static Rolagem lerRolagem(String linha) {

        Map<String, String> campos = new HashMap<>();

        // os campos do meio vêm separados por " | "
        for (String parte : linha.split("\\|")) {

            int i = parte.indexOf(":");

            if (i > 0) {
                campos.put(
                        parte.substring(0, i).trim(),
                        parte.substring(i + 1).trim()
                );
            }
        }

        String descricao = campos.getOrDefault("Descrição", "");

        int bonus = 0;

        try {
            bonus = Integer.parseInt(campos.getOrDefault("Bônus", "0"));
        } catch (NumberFormatException e) {
            bonus = 0;
        }

        LocalDateTime data = LocalDateTime.now();

        try {
            // o primeiro campo depois de "Rolagem:" é sempre a data
            String dataTexto = linha
                    .substring("Rolagem:".length())
                    .split("\\|")[0]
                    .trim();

            if (!dataTexto.isEmpty()) {
                data = LocalDateTime.parse(dataTexto, Rolagem.formatoData());
            }
        } catch (DateTimeException e) {
            // data ilegível: mantém a data atual em vez de perder a rolagem
        }

        return new Rolagem(descricao, bonus, data);
    }

    // "Termo: 2d8 | Valores: 6, 1 | Subtotal: 7"
    private static ResultadoRolagem lerTermo(String linha) {

        Map<String, String> campos = new HashMap<>();

        for (String parte : linha.split("\\|")) {

            int i = parte.indexOf(":");

            if (i > 0) {
                campos.put(
                        parte.substring(0, i).trim(),
                        parte.substring(i + 1).trim()
                );
            }
        }

        String formula = campos.getOrDefault("Termo", "").trim();
        String valoresTexto = campos.getOrDefault("Valores", "");

        ResultadoRolagem termo = ResultadoRolagem.de(formula);

        List<Integer> valores = new ArrayList<>();

        for (String valor : valoresTexto.split(",")) {

            String limpo = valor.trim();

            if (limpo.isEmpty()) {
                continue;
            }

            try {
                valores.add(Integer.valueOf(limpo));
            } catch (NumberFormatException e) {
                // valor ilegível: mantém os que deu para ler
            }
        }

        if (!valores.isEmpty()) {
            termo.registrar(valores);
        }

        return termo;
    }

}