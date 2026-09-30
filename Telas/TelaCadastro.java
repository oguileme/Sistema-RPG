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
        JButton botaoLogin = new JButton("Já tenho uma conta");
        JButton botaoCancelar = new JButton("Cancelar");

        painelBotoes.add(botaoCadastrar);
        painelBotoes.add(botaoLogin);
        painelBotoes.add(botaoCancelar);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        // Botão cadastrar
        botaoCadastrar.addActionListener(e -> {

            String nome = campoNome.getText().trim();
            String usuario = campoUsuario.getText().trim();
            String email = campoEmail.getText().trim();
            String senha = new String(campoSenha.getPassword());

            if (nome.isEmpty() ||
                    usuario.isEmpty() ||
                    email.isEmpty() ||
                    senha.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Preencha todos os campos.",
                        "Atenção",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            Usuario novoUsuario = new Usuario(
                    nome,
                    usuario,
                    email,
                    senha
            );

            if (!novoUsuario.salvarUsuario()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Não foi possível salvar o usuário.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Usuário cadastrado com sucesso!"
            );

            // Botão login

            // Abre a tela de login
            new TelaLogin().setVisible(true);

            // Fecha a tela de cadastro
            dispose();
        });

        botaoLogin.addActionListener(event -> {

            new TelaLogin().setVisible(true);

            dispose();
        });

        // Botão cancelar
        botaoCancelar.addActionListener(e -> dispose());

        add(painelPrincipal);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaCadastro().setVisible(true);
        });
    }
}