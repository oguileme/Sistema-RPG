package classes;

import Telas.TelaCampanha;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Campanha {

    private String nome;
    private String descricao;
    private Usuario mestre;
    private String loginMestre; // login do mestre, lido direto do arquivo da campanha
    private List<Usuario> jogadores;
    private List<Ficha> fichas;

    public Campanha(
            String nome,
            String descricao,
            Usuario mestre
    ) {

        this.nome = nome;
        this.descricao = descricao;
        this.mestre = mestre;
        this.loginMestre = (mestre != null) ? mestre.getUsuario() : null;

        this.jogadores = new ArrayList<>();
        this.fichas = new ArrayList<>();
    }

    // =========================
    // GETTERS
    // =========================

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Usuario getMestre() {
        return mestre;
    }

    public String getLoginMestre() {
        return loginMestre;
    }

    public void setLoginMestre(String loginMestre) {
        this.loginMestre = loginMestre;
    }

    public List<Usuario> getJogadores() {
        return jogadores;
    }

    public List<Ficha> getFichas() {
        return fichas;
    }

    // =========================
    // SETTERS
    // =========================

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // =========================
    // JOGADORES
    // =========================

    public void addPlayer(Usuario player) {
        this.jogadores.add(player);
    }

    public void addFicha(Ficha ficha) {
        this.fichas.add(ficha);
    }

    // =========================
    // VERIFICA SE O USUÁRIO É O MESTRE
    // =========================

    public boolean ehMestre(Usuario usuario) {

        if (usuario == null || usuario.getUsuario() == null || loginMestre == null) {
            return false;
        }

        return usuario.getUsuario().trim().equals(loginMestre.trim());
    }

    // =========================
    // CARREGAR FICHAS DA CAMPANHA
    // =========================
    // Mestre: vê todas as fichas.
    // Jogador: vê somente as fichas que ele criou.

    public List<Ficha> carregarFichas(Usuario usuario) {

        fichas.clear();

        File pasta = new File("Fichas/" + nome);
        File[] arquivos = pasta.listFiles(
                (dir, n) -> n.endsWith(".txt")
        );

        if (arquivos == null) {
            return fichas;
        }

        boolean mestreDaCampanha = ehMestre(usuario);

        for (File arquivo : arquivos) {

            Ficha p = Ficha.carregarFicha(arquivo);

            if (p == null) {
                continue;
            }

            boolean ehDono =
                    usuario != null &&
                            usuario.getUsuario().equals(p.getDonoUsuario());

            if (mestreDaCampanha || ehDono) {
                fichas.add(p);
            }
        }

        return fichas;
    }

    // =========================
    // SALVAR CAMPANHA
    // =========================

    public void salvarCampanha() {

        File pasta = new File("Campanhas");

        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        File arquivo = new File(
                pasta,
                nome + ".txt"
        );

        // A descrição é uma linha só: quebra-la faria o
        // "Mestre: " cair em outra linha e a campanha perder o dono
        String descricaoEmUmaLinha =
                (descricao == null ? "" : descricao)
                        .replace("\r", " ")
                        .replace("\n", " ")
                        .trim();

        try (PrintWriter writer =
                     new PrintWriter(arquivo, StandardCharsets.UTF_8)) {

            writer.println("Nome: " + nome);
            writer.println("Descrição: " + descricaoEmUmaLinha);

            String login = (mestre != null)
                    ? mestre.getUsuario()
                    : loginMestre;

            writer.println(
                    "Mestre: " +
                            (login == null ? "" : login)
            );

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao salvar campanha: "
                            + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // EXCLUIR CAMPANHA
    // =========================
    // Apaga o arquivo da campanha e todas as fichas dela.
    // Retorna true se tudo foi apagado.

    public boolean excluir() {

        boolean ok = true;

        // Fichas da campanha
        File pastaFichas = new File("Fichas/" + nome);

        if (pastaFichas.exists()) {

            File[] arquivos = pastaFichas.listFiles();

            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    if (!arquivo.delete()) {
                        ok = false;
                    }
                }
            }

            if (!pastaFichas.delete()) {
                ok = false;
            }
        }

        // Arquivo da campanha
        File arquivoCampanha = new File("Campanhas", nome + ".txt");

        if (arquivoCampanha.exists() && !arquivoCampanha.delete()) {
            ok = false;
        }

        return ok;
    }

    // =========================
    // EDITAR CAMPANHA
    // =========================
    // Altera nome e descrição. Se o nome mudar, também:
    //  - renomeia o arquivo Campanhas/<nome>.txt
    //  - renomeia a pasta Fichas/<nome>
    //  - atualiza a linha "Campanha:" dentro de cada ficha
    // Retorna null se deu certo, ou a mensagem de erro.

    public String editar(String novoNome, String novaDescricao) {

        novoNome = novoNome.trim();

        if (novoNome.isEmpty()) {
            return "Digite o nome da campanha.";
        }

        if (novoNome.matches(".*[\\/:*?\"<>|].*")) {
            return "O nome não pode conter: \\ / : * ? \" < > |";
        }

        // Só a descrição mudou
        if (novoNome.equals(nome)) {
            this.descricao = novaDescricao;
            salvarCampanha();
            return null;
        }

        File pastaCampanhas = new File("Campanhas");
        File arquivoAntigo = new File(pastaCampanhas, nome + ".txt");
        File arquivoNovo = new File(pastaCampanhas, novoNome + ".txt");

        // Já existe outra campanha com esse nome
        // (ignora se for só troca de maiúscula/minúscula)
        if (arquivoNovo.exists() && !nome.equalsIgnoreCase(novoNome)) {
            return "Já existe uma campanha com esse nome.";
        }

        File pastaFichasAntiga = new File("Fichas/" + nome);
        File pastaFichasNova = new File("Fichas/" + novoNome);

        if (pastaFichasNova.exists() && !nome.equalsIgnoreCase(novoNome)) {
            return "Já existe uma pasta de fichas com esse nome.";
        }

        try {

            // 1) Renomeia a pasta das fichas
            if (pastaFichasAntiga.exists()) {
                Files.move(
                        pastaFichasAntiga.toPath(),
                        pastaFichasNova.toPath(),
                        StandardCopyOption.REPLACE_EXISTING
                );
            }

            // 2) Renomeia o arquivo da campanha
            if (arquivoAntigo.exists()) {
                Files.move(
                        arquivoAntigo.toPath(),
                        arquivoNovo.toPath(),
                        StandardCopyOption.REPLACE_EXISTING
                );
            }

        } catch (IOException e) {
            return "Erro ao renomear a campanha: " + e.getMessage();
        }

        // 3) Atualiza os dados da campanha e regrava o arquivo
        this.nome = novoNome;
        this.descricao = novaDescricao;
        salvarCampanha();

        // 4) Atualiza o nome da campanha dentro de cada ficha
        File[] arquivosFichas = pastaFichasNova.listFiles(
                (dir, n) -> n.endsWith(".txt")
        );

        if (arquivosFichas != null) {
            for (File arquivo : arquivosFichas) {

                Ficha ficha = Ficha.carregarFicha(arquivo);

                if (ficha != null) {
                    ficha.setNomeCampanha(novoNome);
                    ficha.salvarFicha();
                }
            }
        }

        return null;
    }

    // =========================
    // CARREGAR UMA CAMPANHA
    // =========================

    public static Campanha carregarCampanha(
            File arquivo
    ) {

        try (
                BufferedReader leitor =
                        new BufferedReader(
                                new InputStreamReader(
                                        new FileInputStream(arquivo),
                                        StandardCharsets.UTF_8
                                )
                        )
        ) {

            Map<String, String> d = new HashMap<>();
            String linha;

            while ((linha = leitor.readLine()) != null) {
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

            String nome = d.get("Nome");
            String descricao = d.get("Descrição");

            String loginMestre = d.get("Mestre");

            if (loginMestre != null) {
                loginMestre = loginMestre.trim();
            }

            Usuario mestre = null;

            if (loginMestre != null && !loginMestre.isEmpty()) {
                mestre = Usuario.carregarUsuario(loginMestre);
            }

            Campanha campanha = new Campanha(
                    nome,
                    descricao,
                    mestre
            );

            campanha.setLoginMestre(loginMestre);

            return campanha;

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao carregar campanha: "
                            + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );

            return null;
        }
    }

    // =========================
    // CARREGAR CAMPANHAS
    // =========================

    public static void carregarCampanhas(
            JPanel painelCampanhas,
            Usuario usuario
    ) {

        File pasta =
                new File("Campanhas");

        if (!pasta.exists()) {

            JLabel nenhuma =
                    new JLabel(
                            "Nenhuma campanha cadastrada."
                    );

            painelCampanhas.add(nenhuma);

            return;
        }

        File[] arquivos =
                pasta.listFiles();

        if (
                arquivos == null ||
                        arquivos.length == 0
        ) {

            JLabel nenhuma =
                    new JLabel(
                            "Nenhuma campanha cadastrada."
                    );

            painelCampanhas.add(nenhuma);

            return;
        }

        for (File arquivo : arquivos) {

            if (!arquivo.isFile()) {
                continue;
            }

            if (!arquivo.getName().endsWith(".txt")) {
                continue;
            }

            // Carrega a campanha inteira
            Campanha campanha =
                    carregarCampanha(arquivo);

            if (campanha == null) {
                continue;
            }

            JButton botao =
                    new JButton(
                            campanha.getNome()
                    );

            botao.setAlignmentX(
                    Component.CENTER_ALIGNMENT
            );

            // =========================
            // CLIQUE NA CAMPANHA
            // =========================

            botao.addActionListener(e -> {

                JFrame telaAtual =
                        (JFrame) SwingUtilities
                                .getWindowAncestor(
                                        painelCampanhas
                                );

                // Fecha a TelaPrincipal
                telaAtual.dispose();

                // Abre a campanha
                new TelaCampanha(
                        campanha,
                        usuario
                ).setVisible(true);
            });

            painelCampanhas.add(botao);

            painelCampanhas.add(
                    Box.createVerticalStrut(10)
            );
        }
    }
}