package Telas;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {

        setTitle("Tela Principal");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new BorderLayout(10, 10));

        painel.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        // Título
        JLabel titulo = new JLabel("Bem-vindo ao sistema!");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        painel.add(titulo, BorderLayout.NORTH);

        // Botões
        JPanel painelBotoes = new JPanel(
                new GridLayout(3, 1, 10, 10)
        );

        JButton botaoFicha = new JButton("Minhas Fichas");
        JButton botaoCadastrarFicha = new JButton("Cadastrar Ficha");
        JButton botaoSair = new JButton("Sair");

        painelBotoes.add(botaoFicha);
        painelBotoes.add(botaoCadastrarFicha);
        painelBotoes.add(botaoSair);

        painel.add(painelBotoes, BorderLayout.CENTER);

        // Botão cadastrar ficha
        botaoCadastrarFicha.addActionListener(e -> {
            new TelaCadastroFicha().setVisible(true);
        });

        // Botão sair
        botaoSair.addActionListener(e -> {
            new TelaLogin().setVisible(true);
            dispose();
        });

        add(painel);
    }

    public static void main(String[] args) {
        new TelaPrincipal().setVisible(true);
    }
}