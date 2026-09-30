package Telas;

import classes.Campanha;
import classes.Usuario;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TelaPrincipal extends JFrame {

    private Usuario usuario;

    public TelaPrincipal(Usuario usuario) {

        this.usuario = usuario;

        setTitle("Tela Principal");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new BorderLayout(10, 10));

        painel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        JLabel titulo = new JLabel(
                "Bem-vindo ao sistema!"
        );

        titulo.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        titulo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        painel.add(titulo, BorderLayout.NORTH);

        // Lista de campanhas
        JPanel painelCampanhas = new JPanel();

        painelCampanhas.setLayout(
                new BoxLayout(
                        painelCampanhas,
                        BoxLayout.Y_AXIS
                )
        );

        Campanha.carregarCampanhas(painelCampanhas);

        JScrollPane scroll = new JScrollPane(
                painelCampanhas
        );

        painel.add(scroll, BorderLayout.CENTER);

        // Botões
        JPanel painelBotoes = new JPanel();

        JButton botaoCriarCampanha =
                new JButton("Criar Campanha");

        JButton botaoSair =
                new JButton("Sair");

        painelBotoes.add(botaoCriarCampanha);
        painelBotoes.add(botaoSair);

        painel.add(
                painelBotoes,
                BorderLayout.SOUTH
        );

        // Criar campanha
        botaoCriarCampanha.addActionListener(e -> {

            dispose();

            new TelaCadastroCampanha(usuario).setVisible(true);

        });


        // Sair
        botaoSair.addActionListener(e -> {

            new TelaLogin().setVisible(true);

            dispose();

        });

        add(painel);
    }



    public static void main(String[] args) {

        new TelaPrincipal(null)
                .setVisible(true);

    }
}
