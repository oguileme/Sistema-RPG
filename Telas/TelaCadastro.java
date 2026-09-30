package Telas;

import classes.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaCadastro extends JFrame {

    public TelaCadastro() {

        setTitle("Cadastro de Usuário");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        // Título
        JLabel titulo = new JLabel("Cadastro de Usuário");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        painelPrincipal.add(titulo, BorderLayout.NORTH);

        // Campos
        JPanel painelCampos = new JPanel();
        painelCampos.setLayout(new GridLayout(4, 2, 10, 10));

        // Nome
        painelCampos.add(new JLabel("Nome:"));
        JTextField campoNome = new JTextField();
        painelCampos.add(campoNome);

        // Usuário
        painelCampos.add(new JLabel("Usuário:"));
        JTextField campoUsuario = new JTextField();
        painelCampos.add(campoUsuario);

        // E-mail
        painelCampos.add(new JLabel("E-mail:"));
        JTextField campoEmail = new JTextField();
        painelCampos.add(campoEmail);

        // Senha
        painelCampos.add(new JLabel("Senha:"));
        JPasswordField campoSenha = new JPasswordField();
        painelCampos.add(campoSenha);

        painelPrincipal.add(painelCampos, BorderLayout.CENTER);

        // Botões
        JPanel painelBotoes = new JPanel();

        JButton botaoCadastrar = new JButton("Cadastrar");
        JButton botaoCancelar = new JButton("Cancelar");

        painelBotoes.add(botaoCadastrar);
        painelBotoes.add(botaoCancelar);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        // Botão cadastrar
        botaoCadastrar.addActionListener(e -> {

            String nome = campoNome.getText();
            String usuario = campoUsuario.getText();
            String email = campoEmail.getText();
            String senha = new String(campoSenha.getPassword());

            Usuario novoUsuario = new Usuario(
                    nome,
                    usuario,
                    email,
                    senha
            );

            novoUsuario.salvarUsuario();

            JOptionPane.showMessageDialog(
                    this,
                    "Usuário cadastrado com sucesso!"
            );

            dispose();
        });

        // Botão cancelar
        botaoCancelar.addActionListener(e -> dispose());

        add(painelPrincipal);
    }

    public static void main(String[] args) {
        new TelaCadastro().setVisible(true);
    }
}