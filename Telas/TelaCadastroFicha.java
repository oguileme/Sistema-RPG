package Telas;

import classes.Atributos;
import classes.Protagonista;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroFicha extends JFrame {

    public TelaCadastroFicha() {

        setTitle("Cadastro de Ficha");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // Título
        JLabel titulo = new JLabel("Cadastro de Ficha");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        painelPrincipal.add(titulo, BorderLayout.NORTH);

        // Painel dos campos
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

        // Pontos de experiência
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
        // Separador
        painelCampos.add(new JLabel("-----"));
        painelCampos.add(new JLabel("ATRIBUTOS"));

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

        // Inventário
        painelCampos.add(new JLabel("Inventário:"));
        JButton botaoInventario = new JButton("Abrir Inventário");
        painelCampos.add(botaoInventario);

        painelPrincipal.add(painelCampos, BorderLayout.CENTER);

        // Botões inferiores
        JPanel painelBotoes = new JPanel();

        JButton botaoSalvar = new JButton("Salvar");
        JButton botaoCancelar = new JButton("Cancelar");

        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoCancelar);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        // BOTÃO SALVAR
        botaoSalvar.addActionListener(e -> {

            try {

                // Dados básicos
                String nome = campoNome.getText();
                String classe = campoClasse.getText();

                int vidaMax = Integer.parseInt(campoVidaMax.getText());
                int vidaAtual = Integer.parseInt(campoVidaAtual.getText());

                int manaMax = Integer.parseInt(campoManaMax.getText());
                int manaAtual = Integer.parseInt(campoManaAtual.getText());

                int experiencia = Integer.parseInt(campoExp.getText());

                Double deslocamento =
                        Double.parseDouble(campoDeslocamento.getText());

                int dinheiro =
                        Integer.parseInt(campoDinheiro.getText());

                // Atributos
                int forca =
                        Integer.parseInt(campoForca.getText());

                int destreza =
                        Integer.parseInt(campoDestreza.getText());

                int constituicao =
                        Integer.parseInt(campoConstituicao.getText());

                int inteligencia =
                        Integer.parseInt(campoInteligencia.getText());

                int sabedoria =
                        Integer.parseInt(campoSabedoria.getText());

                int carisma =
                        Integer.parseInt(campoCarisma.getText());

                Atributos atributos = new Atributos(
                        forca,
                        destreza,
                        constituicao,
                        inteligencia,
                        sabedoria,
                        carisma
                );

                // Cria a ficha
                Protagonista protagonista = new Protagonista(
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



                // Salva a ficha
                protagonista.salvarFicha();

                // Abre a TelaFicha
                new TelaFicha(protagonista).setVisible(true);

                // Fecha a tela de cadastro
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

        // BOTÃO CANCELAR
        botaoCancelar.addActionListener(e -> dispose());

        // Adiciona tudo à janela
        add(painelPrincipal);
    }

    public static void main(String[] args) {
        new TelaCadastroFicha().setVisible(true);
    }
}