package Telas;

import javax.swing.*;

public class TelaLogin extends JFrame {

    public TelaLogin() {
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel usuarioLabel = new JLabel("Usuário:");
        JTextField usuarioField = new JTextField(15);

        JLabel senhaLabel = new JLabel("Senha:");
        JPasswordField senhaField = new JPasswordField(15);

        JButton entrarButton = new JButton("Entrar");

        JPanel painel = new JPanel();
        painel.add(usuarioLabel);
        painel.add(usuarioField);
        painel.add(senhaLabel);
        painel.add(senhaField);
        painel.add(entrarButton);

        add(painel);
    }
}
