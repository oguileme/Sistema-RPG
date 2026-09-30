package classes;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Campanha {

    private String nome;
    private String descricao;
    private Usuario mestre;
    private List<Usuario> jogadores;
    private List<Ficha> fichas;

    public Campanha(String nome, String descricao, Usuario mestre) {
        this.nome = nome;
        this.descricao = descricao;
        this.mestre = mestre;
        this.jogadores = new ArrayList<>();
        this.fichas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Usuario getMestre() {
        return mestre;
    }

    public List<Usuario> getJogadores() {
        return jogadores;
    }

    public List<Ficha> getFichas() {
        return fichas;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void addPlayer(Usuario player) {
        this.jogadores.add(player);
    }

    public void addFicha(Ficha ficha) {
        this.fichas.add(ficha);
    }

    public void salvarCampanha() {

        File pasta = new File("Campanhas");

        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        File arquivo = new File(pasta, nome + ".txt");

        try (PrintWriter writer = new PrintWriter(arquivo)) {

            writer.println(nome);
            writer.println(descricao);

            if (mestre != null) {
                writer.println("Mestre: " + mestre.getUsuario());
            } else {
                writer.println("Mestre: ");
            }


        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao salvar campanha: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void carregarCampanhas(
            JPanel painelCampanhas
    ) {

        File pasta = new File("Campanhas");

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

        if (arquivos == null ||
                arquivos.length == 0) {

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

            String nome =
                    arquivo.getName();

            if (nome.endsWith(".txt")) {

                nome = nome.substring(
                        0,
                        nome.length() - 4
                );

                JButton botao =
                        new JButton(nome);

                botao.setAlignmentX(
                        Component.CENTER_ALIGNMENT
                );

                painelCampanhas.add(botao);
                painelCampanhas.add(
                        Box.createVerticalStrut(10)
                );
            }
        }
    }

}
