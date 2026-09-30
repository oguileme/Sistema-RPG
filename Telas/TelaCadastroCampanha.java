package Telas;

import classes.Campanha;
import classes.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroCampanha extends JFrame {

    private Usuario usuario;

    public TelaCadastroCampanha(Usuario usuario) {

        this.usuario = usuario;

        setTitle("Criar Campanha");
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

        JLabel titulo = new JLabel("Criar Campanha");

        titulo.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        titulo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        painelPrincipal.add(
                titulo,
                BorderLayout.NORTH
        );

        // =========================
        // CAMPOS
        // =========================

        JPanel painelCampos = new JPanel(
                new GridLayout(4, 1, 5, 5)
        );

        // Nome
        JLabel labelNome =
                new JLabel("Nome da campanha:");

        JTextField campoNome =
                new JTextField();

        painelCampos.add(labelNome);
        painelCampos.add(campoNome);

        // Descrição
        JLabel labelDescricao =
                new JLabel("Descrição:");

        JTextArea campoDescricao =
                new JTextArea();

        campoDescricao.setLineWrap(true);
        campoDescricao.setWrapStyleWord(true);

        JScrollPane scrollDescricao =
                new JScrollPane(campoDescricao);

        painelCampos.add(labelDescricao);
        painelCampos.add(scrollDescricao);

        painelPrincipal.add(
                painelCampos,
                BorderLayout.CENTER
        );

        // =========================
        // BOTÕES
        // =========================

        JButton botaoCriar =
                new JButton("Criar");

        JButton botaoCancelar =
                new JButton("Cancelar");

        JPanel botoes =
                new JPanel(new FlowLayout(FlowLayout.RIGHT));

        botoes.add(botaoCancelar);
        botoes.add(botaoCriar);

        painelPrincipal.add(
                botoes,
                BorderLayout.SOUTH
        );

        // =========================
        // AÇÕES
        // =========================

        botaoCriar.addActionListener(e -> {

            String nome =
                    campoNome.getText();

            String descricao =
                    campoDescricao.getText();

            if (nome.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Digite o nome da campanha."
                );

                return;
            }

            Campanha campanha =
                    new Campanha(
                            nome,
                            descricao,
                            usuario
                    );

            if (!campanha.salvarCampanha()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Não foi possível criar a campanha.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Campanha criada com sucesso!"
            );

            dispose();

            new TelaPrincipal(usuario).setVisible(true);

        });

        botaoCancelar.addActionListener(e ->
                dispose()
        );

        add(painelPrincipal);
    }
}
