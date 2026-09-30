package Telas;

import classes.Campanha;
import classes.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaEdicaoCampanha extends JFrame {

    private Campanha campanha;
    private Usuario usuario;

    public TelaEdicaoCampanha(Campanha campanha, Usuario usuario) {

        this.campanha = campanha;
        this.usuario = usuario;

        setTitle("Editar Campanha");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));

        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        // =========================
        // TÍTULO
        // =========================

        JLabel titulo = new JLabel("Editar Campanha");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        painelPrincipal.add(titulo, BorderLayout.NORTH);

        // =========================
        // CAMPOS
        // =========================

        JPanel painelCampos = new JPanel(new BorderLayout(5, 10));

        // Nome
        JPanel painelNome = new JPanel(new BorderLayout(5, 5));
        painelNome.add(new JLabel("Nome da campanha:"), BorderLayout.NORTH);

        JTextField campoNome = new JTextField(campanha.getNome());
        painelNome.add(campoNome, BorderLayout.CENTER);

        painelCampos.add(painelNome, BorderLayout.NORTH);

        // Descrição
        JPanel painelDescricao = new JPanel(new BorderLayout(5, 5));
        painelDescricao.add(new JLabel("Descrição:"), BorderLayout.NORTH);

        JTextArea campoDescricao = new JTextArea(campanha.getDescricao());
        campoDescricao.setLineWrap(true);
        campoDescricao.setWrapStyleWord(true);

        painelDescricao.add(new JScrollPane(campoDescricao), BorderLayout.CENTER);

        painelCampos.add(painelDescricao, BorderLayout.CENTER);

        painelPrincipal.add(painelCampos, BorderLayout.CENTER);

        // =========================
        // BOTÕES
        // =========================

        JButton botaoSalvar = new JButton("Salvar alterações");
        JButton botaoCancelar = new JButton("Cancelar");

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        botoes.add(botaoCancelar);
        botoes.add(botaoSalvar);

        painelPrincipal.add(botoes, BorderLayout.SOUTH);

        // =========================
        // AÇÕES
        // =========================

        botaoSalvar.addActionListener(e -> {

            // Só o mestre pode editar a campanha
            if (!campanha.ehMestre(usuario)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Somente o mestre pode editar a campanha.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            String erro = campanha.editar(
                    campoNome.getText(),
                    campoDescricao.getText()
            );

            if (erro != null) {

                JOptionPane.showMessageDialog(
                        this,
                        erro,
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Campanha atualizada com sucesso!"
            );

            new TelaCampanha(campanha, usuario).setVisible(true);

            dispose();
        });

        botaoCancelar.addActionListener(e -> {

            new TelaCampanha(campanha, usuario).setVisible(true);

            dispose();
        });

        add(painelPrincipal);
    }
}