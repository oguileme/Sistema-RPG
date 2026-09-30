package Telas;

import classes.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {

    public TelaLogin() {

        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 10));

        painel.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        // Usuário
        JLabel usuarioLabel = new JLabel("Usuário:");
        JTextField usuarioField = new JTextField();

        painel.add(usuarioLabel);
        painel.add(usuarioField);

        // Senha
        JLabel senhaLabel = new JLabel("Senha:");
        JPasswordField senhaField = new JPasswordField();

        painel.add(senhaLabel);
        painel.add(senhaField);

        // Botão entrar
        JButton entrarButton = new JButton("Entrar");

        painel.add(new JLabel());
        painel.add(entrarButton);

        // Botão cadastro
        JButton cadastroButton = new JButton("Cadastrar");

        painel.add(new JLabel());
        painel.add(cadastroButton);

        add(painel);

        // Botão Entrar
        entrarButton.addActionListener(e -> {

            String usuario = usuarioField.getText();
            String senha = new String(senhaField.getPassword());

            if (Usuario.autenticar(usuario, senha)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Login realizado com sucesso!"
                );

                // Abre a TelaPrincipal
                new TelaPrincipal().setVisible(true);

                // Fecha a tela de login
                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Usuário ou senha incorretos.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Botão Cadastrar
        cadastroButton.addActionListener(e -> {

            new TelaCadastro().setVisible(true);

            dispose();
        });
    }

    public static void main(String[] args) {
        new TelaLogin().setVisible(true);
    }
}