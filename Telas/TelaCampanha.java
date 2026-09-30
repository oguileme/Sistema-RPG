package Telas;

import classes.Campanha;
import classes.Ficha;
import classes.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaCampanha extends JFrame {

    private Campanha campanha;
    private Usuario usuario;

    // Painel que mostra a lista de fichas
    private JPanel painelFichas;

    public TelaCampanha(
            Campanha campanha,
            Usuario usuario
    ) {

        this.campanha = campanha;
        this.usuario = usuario;

        setTitle(campanha.getNome());
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // =========================
        // PAINEL PRINCIPAL
        // =========================

        JPanel painelPrincipal =
                new JPanel(new BorderLayout(15, 15));

        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        // =========================
        // TÍTULO
        // =========================

        JLabel titulo = new JLabel(campanha.getNome());

        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        painelPrincipal.add(titulo, BorderLayout.NORTH);

        // =========================
        // INFORMAÇÕES DA CAMPANHA
        // =========================

        JPanel painelInformacoes = new JPanel();

        painelInformacoes.setLayout(
                new BoxLayout(painelInformacoes, BoxLayout.Y_AXIS)
        );

        JLabel descricao = new JLabel(
                "<html><b>Descrição:</b> "
                        + campanha.getDescricao()
                        + "</html>"
        );

        JLabel mestre = new JLabel(
                "<html><b>Mestre:</b> "
                        + obterNomeMestre()
                        + "</html>"
        );

        descricao.setAlignmentX(Component.LEFT_ALIGNMENT);
        mestre.setAlignmentX(Component.LEFT_ALIGNMENT);

        painelInformacoes.add(descricao);
        painelInformacoes.add(Box.createVerticalStrut(10));
        painelInformacoes.add(mestre);
        painelInformacoes.add(Box.createVerticalStrut(30));

        // =========================
        // BOTÃO CADASTRAR FICHA
        // =========================

        JButton botaoCadastrarFicha = new JButton("Cadastrar Ficha");

        botaoCadastrarFicha.setAlignmentX(Component.LEFT_ALIGNMENT);

        painelInformacoes.add(botaoCadastrarFicha);

        // =========================
        // LISTA DE FICHAS
        // =========================

        painelInformacoes.add(Box.createVerticalStrut(20));

        JLabel labelFichas = new JLabel("Fichas da campanha:");
        labelFichas.setFont(new Font("Arial", Font.BOLD, 14));
        labelFichas.setAlignmentX(Component.LEFT_ALIGNMENT);

        painelInformacoes.add(labelFichas);
        painelInformacoes.add(Box.createVerticalStrut(5));

        painelFichas = new JPanel();
        painelFichas.setLayout(
                new BoxLayout(painelFichas, BoxLayout.Y_AXIS)
        );
        painelFichas.setAlignmentX(Component.LEFT_ALIGNMENT);

        painelInformacoes.add(painelFichas);

        atualizarFichas();

        // Atualiza a lista sempre que a tela volta a ficar em foco
        // (por exemplo, depois de editar ou renomear uma ficha)
        addWindowFocusListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowGainedFocus(java.awt.event.WindowEvent e) {
                atualizarFichas();
            }
        });

        painelPrincipal.add(
                new JScrollPane(painelInformacoes),
                BorderLayout.CENTER
        );

        // =========================
        // BOTÕES INFERIORES
        // =========================

        JPanel painelBotoes = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 5)
        );

        JButton botaoVoltar = new JButton("Voltar");

        // Somente o mestre pode editar a campanha
        if (ehMestre()) {

            JButton botaoEditar = new JButton("Editar Campanha");

            botaoEditar.addActionListener(e -> {

                new TelaEdicaoCampanha(campanha, usuario).setVisible(true);

                dispose();
            });

            painelBotoes.add(botaoEditar);

            JButton botaoExcluirCampanha = new JButton("Excluir Campanha");

            botaoExcluirCampanha.addActionListener(e -> {

                int resposta = JOptionPane.showConfirmDialog(
                        this,
                        "Tem certeza que deseja excluir a campanha \""
                                + campanha.getNome() + "\"?\n"
                                + "Todas as fichas dela também serão excluídas.\n"
                                + "Essa ação não pode ser desfeita.",
                        "Excluir campanha",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (resposta != JOptionPane.YES_OPTION) {
                    return;
                }

                if (campanha.excluir()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Campanha excluída com sucesso!"
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Nem todos os arquivos puderam ser excluídos.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

                new TelaPrincipal(usuario).setVisible(true);

                dispose();
            });

            painelBotoes.add(botaoExcluirCampanha);
        }

        painelBotoes.add(botaoVoltar);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        // =========================
        // CADASTRAR FICHA
        // =========================

        botaoCadastrarFicha.addActionListener(e -> {

            // true = mestre (NPC) | false = jogador (Protagonista)
            new TelaCadastroFicha(
                    campanha,
                    usuario,
                    ehMestre(),
                    () -> atualizarFichas()
            ).setVisible(true);

        });

        // =========================
        // VOLTAR
        // =========================

        botaoVoltar.addActionListener(e -> {

            new TelaPrincipal(usuario).setVisible(true);

            dispose();

        });

        add(painelPrincipal);
    }

    // =========================
    // ATUALIZA A LISTA DE FICHAS
    // =========================

    private void atualizarFichas() {

        painelFichas.removeAll();

        List<Ficha> fichas = campanha.carregarFichas(usuario);

        if (fichas.isEmpty()) {

            JLabel nenhuma = new JLabel("Nenhuma ficha cadastrada.");
            nenhuma.setAlignmentX(Component.LEFT_ALIGNMENT);
            painelFichas.add(nenhuma);
        }

        for (Ficha ficha : fichas) {

            JButton botao = new JButton(
                    ficha.getNome() + " (" + ficha.getClasse() + ") - " + ficha.getTipo()
            );

            botao.setAlignmentX(Component.LEFT_ALIGNMENT);

            botao.addActionListener(e -> {

                new TelaFicha(ficha).setVisible(true);
            });

            painelFichas.add(botao);
            painelFichas.add(Box.createVerticalStrut(5));
        }

        painelFichas.revalidate();
        painelFichas.repaint();
    }

    // =========================
    // VERIFICA SE É O MESTRE
    // =========================

    private boolean ehMestre() {
        return campanha.ehMestre(usuario);
    }

    // =========================
    // NOME DO MESTRE
    // =========================

    private String obterNomeMestre() {

        if (campanha.getLoginMestre() == null) {
            return "Não definido";
        }

        return campanha.getLoginMestre();
    }
}