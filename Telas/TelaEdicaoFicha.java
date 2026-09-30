package Telas;

import classes.Atributos;
import classes.Ficha;
import classes.NPC;
import classes.Protagonista;

import javax.swing.*;
import java.awt.*;

public class TelaEdicaoFicha extends JFrame {

    public TelaEdicaoFicha(Ficha protagonista) {

        setTitle("Editar Ficha");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // Título
        JLabel titulo = new JLabel("Editar Ficha");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        painelPrincipal.add(titulo, BorderLayout.NORTH);

        // Painel dos campos
        JPanel painelCampos = new JPanel();
        painelCampos.setLayout(new GridLayout(0, 2, 10, 10));

        // Nome
        painelCampos.add(new JLabel("Nome:"));
        JTextField campoNome = new JTextField(protagonista.getNome());
        painelCampos.add(campoNome);

        // Classe
        painelCampos.add(new JLabel("Classe:"));
        JTextField campoClasse = new JTextField(protagonista.getClasse());
        painelCampos.add(campoClasse);

        // Vida máxima
        painelCampos.add(new JLabel("Vida máxima:"));
        JTextField campoVidaMax = new JTextField(
                String.valueOf(protagonista.getVidaMax())
        );
        painelCampos.add(campoVidaMax);

        // Vida atual
        painelCampos.add(new JLabel("Vida atual:"));
        JTextField campoVidaAtual = new JTextField(
                String.valueOf(protagonista.getVidaAtual())
        );
        painelCampos.add(campoVidaAtual);

        // Mana máxima
        painelCampos.add(new JLabel("Mana máxima:"));
        JTextField campoManaMax = new JTextField(
                String.valueOf(protagonista.getManaMax())
        );
        painelCampos.add(campoManaMax);

        // Mana atual
        painelCampos.add(new JLabel("Mana atual:"));
        JTextField campoManaAtual = new JTextField(
                String.valueOf(protagonista.getManaAtual())
        );
        painelCampos.add(campoManaAtual);

        // Experiência
        painelCampos.add(new JLabel("Pontos de experiência:"));
        JTextField campoExp = new JTextField(
                String.valueOf(protagonista.getPontosExp())
        );
        painelCampos.add(campoExp);

        // Deslocamento
        painelCampos.add(new JLabel("Deslocamento:"));
        JTextField campoDeslocamento = new JTextField(
                String.valueOf(protagonista.getDeslocamento())
        );
        painelCampos.add(campoDeslocamento);

        // Dinheiro
        painelCampos.add(new JLabel("Dinheiro:"));
        JTextField campoDinheiro = new JTextField(
                String.valueOf(protagonista.getDinheiro())
        );
        painelCampos.add(campoDinheiro);

        // Separador
        painelCampos.add(new JLabel("-----"));
        painelCampos.add(new JLabel("ATRIBUTOS"));

        // Força
        painelCampos.add(new JLabel("Força:"));
        JTextField campoForca = new JTextField(
                String.valueOf(protagonista.getAtributos().getForca())
        );
        painelCampos.add(campoForca);

        // Destreza
        painelCampos.add(new JLabel("Destreza:"));
        JTextField campoDestreza = new JTextField(
                String.valueOf(protagonista.getAtributos().getDestreza())
        );
        painelCampos.add(campoDestreza);

        // Constituição
        painelCampos.add(new JLabel("Constituição:"));
        JTextField campoConstituicao = new JTextField(
                String.valueOf(protagonista.getAtributos().getConstituicao())
        );
        painelCampos.add(campoConstituicao);

        // Inteligência
        painelCampos.add(new JLabel("Inteligência:"));
        JTextField campoInteligencia = new JTextField(
                String.valueOf(protagonista.getAtributos().getInteligencia())
        );
        painelCampos.add(campoInteligencia);

        // Sabedoria
        painelCampos.add(new JLabel("Sabedoria:"));
        JTextField campoSabedoria = new JTextField(
                String.valueOf(protagonista.getAtributos().getSabedoria())
        );
        painelCampos.add(campoSabedoria);

        // Carisma
        painelCampos.add(new JLabel("Carisma:"));
        JTextField campoCarisma = new JTextField(
                String.valueOf(protagonista.getAtributos().getCarisma())
        );
        painelCampos.add(campoCarisma);

        // Personalidade (somente NPC)
        JTextField campoPersonalidade = new JTextField();

        if (protagonista instanceof NPC) {
            campoPersonalidade.setText(((NPC) protagonista).getPersonalidade());
            painelCampos.add(new JLabel("Personalidade:"));
            painelCampos.add(campoPersonalidade);
        }

        painelPrincipal.add(new JScrollPane(painelCampos), BorderLayout.CENTER);

        // Botões
        JPanel painelBotoes = new JPanel();

        JButton botaoSalvar = new JButton("Salvar alterações");
        JButton botaoCancelar = new JButton("Cancelar");

        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoCancelar);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        // Salvar alterações
        botaoSalvar.addActionListener(e -> {

            try {

                String nome = campoNome.getText();
                String classe = campoClasse.getText();

                if (nome.trim().isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Digite o nome da ficha.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

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
                int forca = Integer.parseInt(campoForca.getText());
                int destreza = Integer.parseInt(campoDestreza.getText());
                int constituicao =
                        Integer.parseInt(campoConstituicao.getText());
                int inteligencia =
                        Integer.parseInt(campoInteligencia.getText());
                int sabedoria =
                        Integer.parseInt(campoSabedoria.getText());
                int carisma = Integer.parseInt(campoCarisma.getText());

                Atributos atributos = new Atributos(
                        forca,
                        destreza,
                        constituicao,
                        inteligencia,
                        sabedoria,
                        carisma
                );

                Ficha fichaEditada;

                if (protagonista instanceof NPC) {

                    fichaEditada = new NPC(
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
                            protagonista.getRolagens(),
                            null,
                            campoPersonalidade.getText()
                    );

                } else {

                    fichaEditada = new Protagonista(
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
                            protagonista.getRolagens(),
                            null,
                            null
                    );
                }

                // Mantém a ficha na mesma campanha e com o mesmo dono
                fichaEditada.setNomeCampanha(protagonista.getNomeCampanha());
                fichaEditada.setDonoUsuario(protagonista.getDonoUsuario());

                // Se o nome mudou, renomeia o arquivo da ficha
                if (!nome.equals(protagonista.getNome())) {

                    if (!protagonista.renomearArquivo(nome)) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Já existe uma ficha com esse nome nesta campanha.",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE
                        );

                        return;
                    }
                }

                fichaEditada.salvarFicha();

                new TelaFicha(fichaEditada).setVisible(true);

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

        // Cancelar
        botaoCancelar.addActionListener(e -> {

            new TelaFicha(protagonista).setVisible(true);

            dispose();
        });

        add(painelPrincipal);
    }
}