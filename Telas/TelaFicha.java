package Telas;

import classes.Protagonista;

import javax.swing.*;
import java.awt.*;

public class TelaFicha extends JFrame {

    public TelaFicha(Protagonista protagonista) {

        setTitle("Ficha do Personagem");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // Título
        JLabel titulo = new JLabel("Ficha do Personagem");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        painelPrincipal.add(titulo, BorderLayout.NORTH);

        // Painel dos dados
        JPanel painelDados = new JPanel();
        painelDados.setLayout(new GridLayout(0, 2, 10, 10));

        // Dados básicos
        painelDados.add(new JLabel("Nome:"));
        painelDados.add(new JLabel(protagonista.getNome()));

        painelDados.add(new JLabel("Classe:"));
        painelDados.add(new JLabel(protagonista.getClasse()));

        painelDados.add(new JLabel("Vida máxima:"));
        painelDados.add(new JLabel(
                String.valueOf(protagonista.getVidaMax())
        ));

        painelDados.add(new JLabel("Vida atual:"));
        painelDados.add(new JLabel(
                String.valueOf(protagonista.getVidaAtual())
        ));

        painelDados.add(new JLabel("Mana máxima:"));
        painelDados.add(new JLabel(
                String.valueOf(protagonista.getManaMax())
        ));

        painelDados.add(new JLabel("Mana atual:"));
        painelDados.add(new JLabel(
                String.valueOf(protagonista.getManaAtual())
        ));

        painelDados.add(new JLabel("Experiência:"));
        painelDados.add(new JLabel(
                String.valueOf(protagonista.getPontosExp())
        ));

        painelDados.add(new JLabel("Deslocamento:"));
        painelDados.add(new JLabel(
                String.valueOf(protagonista.getDeslocamento())
        ));

        painelDados.add(new JLabel("Dinheiro:"));
        painelDados.add(new JLabel(
                String.valueOf(protagonista.getDinheiro())
        ));

        // Separador visual
        painelDados.add(new JLabel("-----"));
        painelDados.add(new JLabel("ATRIBUTOS"));

        // Atributos
        painelDados.add(new JLabel("Força:"));
        painelDados.add(new JLabel(
                String.valueOf(protagonista.getAtributos().getForca())
        ));

        painelDados.add(new JLabel("Destreza:"));
        painelDados.add(new JLabel(
                String.valueOf(protagonista.getAtributos().getDestreza())
        ));

        painelDados.add(new JLabel("Constituição:"));
        painelDados.add(new JLabel(
                String.valueOf(protagonista.getAtributos().getConstituicao())
        ));

        painelDados.add(new JLabel("Inteligência:"));
        painelDados.add(new JLabel(
                String.valueOf(protagonista.getAtributos().getInteligencia())
        ));

        painelDados.add(new JLabel("Sabedoria:"));
        painelDados.add(new JLabel(
                String.valueOf(protagonista.getAtributos().getSabedoria())
        ));

        painelDados.add(new JLabel("Carisma:"));
        painelDados.add(new JLabel(
                String.valueOf(protagonista.getAtributos().getCarisma())
        ));

        painelPrincipal.add(painelDados, BorderLayout.CENTER);

        // Botões inferiores
        JPanel painelBotoes = new JPanel();

        // Botão editar
        JButton botaoEditar = new JButton("Editar");

        botaoEditar.addActionListener(e -> {
            new TelaEdicaoFicha(protagonista).setVisible(true);
            dispose();
        });

        // Botão fechar
        JButton botaoFechar = new JButton("Fechar");

        botaoFechar.addActionListener(e -> dispose());

        painelBotoes.add(botaoEditar);
        painelBotoes.add(botaoFechar);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);
    }
}