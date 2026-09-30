package Telas;

import classes.Atributos;
import classes.Campanha;
import classes.Ficha;
import classes.NPC;
import classes.Protagonista;
import classes.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroFicha extends JFrame {

    private Campanha campanha;
    private Usuario usuario;
    private boolean ehMestre;

    // Chamado depois que a ficha é salva (atualiza a lista da campanha)
    private Runnable aoSalvar;

    public TelaCadastroFicha(
            Campanha campanha,
            Usuario usuario,
            boolean ehMestre,
            Runnable aoSalvar
    ) {

        this.campanha = campanha;
        this.usuario = usuario;
        this.ehMestre = ehMestre;
        this.aoSalvar = aoSalvar;

        setTitle(
                ehMestre
                        ? "Cadastro de NPC"
                        : "Cadastro de Protagonista"
        );

        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // =========================
        // PAINEL PRINCIPAL
        // =========================

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));

        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // =========================
        // TÍTULO
        // =========================

        JLabel titulo = new JLabel(
                ehMestre
                        ? "Cadastro de NPC"
                        : "Cadastro de Protagonista"
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        painelPrincipal.add(titulo, BorderLayout.NORTH);

        // =========================
        // CAMPOS
        // =========================

        JPanel painelCampos = new JPanel();
        painelCampos.setLayout(new GridLayout(0, 2, 10, 10));

        // Nome
        painelCampos.add(new JLabel("Nome:"));
        JTextField campoNome = new JTextField();
        painelCampos.add(campoNome);

        // Classe
        painelCampos.add(new JLabel("Classe:"));
        JTextField campoClasse = new JTextField();
        painelCampos.add(campoClasse);

        // Vida máxima
        painelCampos.add(new JLabel("Vida máxima:"));
        JTextField campoVidaMax = new JTextField();
        painelCampos.add(campoVidaMax);

        // Vida atual
        painelCampos.add(new JLabel("Vida atual:"));
        JTextField campoVidaAtual = new JTextField();
        painelCampos.add(campoVidaAtual);

        // Mana máxima
        painelCampos.add(new JLabel("Mana máxima:"));
        JTextField campoManaMax = new JTextField();
        painelCampos.add(campoManaMax);

        // Mana atual
        painelCampos.add(new JLabel("Mana atual:"));
        JTextField campoManaAtual = new JTextField();
        painelCampos.add(campoManaAtual);

        // Experiência
        painelCampos.add(new JLabel("Pontos de experiência:"));
        JTextField campoExp = new JTextField();
        painelCampos.add(campoExp);

        // Deslocamento
        painelCampos.add(new JLabel("Deslocamento:"));
        JTextField campoDeslocamento = new JTextField();
        painelCampos.add(campoDeslocamento);

        // Dinheiro
        painelCampos.add(new JLabel("Dinheiro:"));
        JTextField campoDinheiro = new JTextField();
        painelCampos.add(campoDinheiro);

        // =========================
        // ATRIBUTOS
        // =========================

        JLabel separador = new JLabel("ATRIBUTOS");
        separador.setFont(new Font("Arial", Font.BOLD, 14));

        painelCampos.add(new JLabel());
        painelCampos.add(separador);

        // Força
        painelCampos.add(new JLabel("Força:"));
        JTextField campoForca = new JTextField();
        painelCampos.add(campoForca);

        // Destreza
        painelCampos.add(new JLabel("Destreza:"));
        JTextField campoDestreza = new JTextField();
        painelCampos.add(campoDestreza);

        // Constituição
        painelCampos.add(new JLabel("Constituição:"));
        JTextField campoConstituicao = new JTextField();
        painelCampos.add(campoConstituicao);

        // Inteligência
        painelCampos.add(new JLabel("Inteligência:"));
        JTextField campoInteligencia = new JTextField();
        painelCampos.add(campoInteligencia);

        // Sabedoria
        painelCampos.add(new JLabel("Sabedoria:"));
        JTextField campoSabedoria = new JTextField();
        painelCampos.add(campoSabedoria);

        // Carisma
        painelCampos.add(new JLabel("Carisma:"));
        JTextField campoCarisma = new JTextField();
        painelCampos.add(campoCarisma);

        // Personalidade (somente NPC)
        JTextField campoPersonalidade = new JTextField();

        if (ehMestre) {
            painelCampos.add(new JLabel("Personalidade:"));
            painelCampos.add(campoPersonalidade);
        }

        // =========================
        // INVENTÁRIO
        // =========================

        painelCampos.add(new JLabel("Inventário:"));
        JButton botaoInventario = new JButton("Abrir Inventário");
        painelCampos.add(botaoInventario);

        painelPrincipal.add(
                new JScrollPane(painelCampos),
                BorderLayout.CENTER
        );

        // =========================
        // BOTÕES
        // =========================

        JPanel painelBotoes = new JPanel();

        JButton botaoSalvar = new JButton("Salvar");
        JButton botaoCancelar = new JButton("Cancelar");

        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoCancelar);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        // =========================
        // SALVAR
        // =========================

        botaoSalvar.addActionListener(e -> {

            try {

                String nome = campoNome.getText();
                String classe = campoClasse.getText();

                if (nome.trim().isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Digite o nome da ficha.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                int vidaMax = Integer.parseInt(campoVidaMax.getText());
                int vidaAtual = Integer.parseInt(campoVidaAtual.getText());
                int manaMax = Integer.parseInt(campoManaMax.getText());
                int manaAtual = Integer.parseInt(campoManaAtual.getText());
                int experiencia = Integer.parseInt(campoExp.getText());
                double deslocamento = Double.parseDouble(campoDeslocamento.getText());
                int dinheiro = Integer.parseInt(campoDinheiro.getText());

                // Atributos
                int forca = Integer.parseInt(campoForca.getText());
                int destreza = Integer.parseInt(campoDestreza.getText());
                int constituicao = Integer.parseInt(campoConstituicao.getText());
                int inteligencia = Integer.parseInt(campoInteligencia.getText());
                int sabedoria = Integer.parseInt(campoSabedoria.getText());
                int carisma = Integer.parseInt(campoCarisma.getText());

                Atributos atributos = new Atributos(
                        forca,
                        destreza,
                        constituicao,
                        inteligencia,
                        sabedoria,
                        carisma
                );

                // =========================
                // NPC (mestre) ou PROTAGONISTA (jogador)
                // =========================

                Ficha ficha;

                if (ehMestre) {

                    ficha = new NPC(
                            vidaMax,
                            vidaAtual,
                            manaMax,
                            manaAtual,
                            nome,
                            classe,
                            experiencia,
                            deslocamento,
                            dinheiro,
                            atributos,
                            null,
                            null,
                            campoPersonalidade.getText()
                    );

                } else {

                    ficha = new Protagonista(
                            vidaMax,
                            vidaAtual,
                            manaMax,
                            manaAtual,
                            nome,
                            classe,
                            experiencia,
                            deslocamento,
                            dinheiro,
                            atributos,
                            null,
                            null,
                            null
                    );
                }

                // Associa a ficha à campanha e ao usuário que a criou
                ficha.setNomeCampanha(campanha.getNome());

                if (usuario != null) {
                    ficha.setDonoUsuario(usuario.getUsuario());
                }

                ficha.salvarFicha();

                // Avisa a tela da campanha para atualizar a lista
                if (this.aoSalvar != null) {
                    this.aoSalvar.run();
                }

                JOptionPane.showMessageDialog(
                        this,
                        (ehMestre ? "NPC" : "Protagonista")
                                + " cadastrado com sucesso!"
                );

                // Abre a tela da ficha
                new TelaFicha(ficha).setVisible(true);

                dispose();

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Preencha os campos numéricos corretamente.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // =========================
        // CANCELAR
        // =========================

        botaoCancelar.addActionListener(e -> dispose());

        add(painelPrincipal);
    }
}