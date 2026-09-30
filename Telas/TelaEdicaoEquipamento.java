package Telas;

import classes.Arma;
import classes.Armadura;
import classes.Equipamento;
import classes.ValidadorNome;

import javax.swing.*;
import java.awt.*;

public class TelaEdicaoEquipamento extends JFrame {

    private Equipamento equipamento;

    private JComboBox<String> campoTipo;

    // Campos comuns
    private JTextField campoNome;
    private JTextField campoQuantidade;
    private JTextField campoCarga;
    private JTextField campoDescricao;

    // Campos de arma
    private JLabel rotuloTipoDano;
    private JLabel rotuloAlcance;
    private JLabel rotuloCritico;
    private JTextField campoTipoDano;
    private JTextField campoAlcance;
    private JTextField campoCritico;

    // Campos de armadura
    private JLabel rotuloBonusCA;
    private JLabel rotuloMaxDestreza;
    private JLabel rotuloPenalidade;
    private JTextField campoBonusCA;
    private JTextField campoMaxDestreza;
    private JTextField campoPenalidade;

    // Painel dos campos, mudado de acordo com o tipo do equipamento
    private JPanel painelCampos;

    public TelaEdicaoEquipamento(Equipamento equipamento) {

        this.equipamento = equipamento;

        setTitle("Editar Equipamento");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // Título
        JLabel titulo = new JLabel("Editar Equipamento");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        // Tipo do equipamento, travado porque não muda na edição
        campoTipo = new JComboBox<String>(
                new String[]{"Equipamento", "Arma", "Armadura"}
        );
        campoTipo.setSelectedItem(equipamento.getTipo());
        campoTipo.setEnabled(false);

        JPanel painelTipo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        painelTipo.add(new JLabel("Tipo de equipamento:"));
        painelTipo.add(campoTipo);

        JPanel painelTopo = new JPanel(new BorderLayout(10, 10));
        painelTopo.add(titulo, BorderLayout.NORTH);
        painelTopo.add(painelTipo, BorderLayout.SOUTH);

        painelPrincipal.add(painelTopo, BorderLayout.NORTH);

        // Campos comuns
        campoNome = new JTextField(equipamento.getNome());
        campoQuantidade = new JTextField(
                String.valueOf(equipamento.getQuantidade())
        );
        campoCarga = new JTextField(
                String.valueOf(equipamento.getCarga())
        );
        campoDescricao = new JTextField(equipamento.getDescricao());

        // Campos de arma
        campoTipoDano = new JTextField();
        campoAlcance = new JTextField();
        campoCritico = new JTextField();

        rotuloTipoDano = new JLabel("Tipo de Dano:");
        rotuloAlcance = new JLabel("Alcance:");
        rotuloCritico = new JLabel("Pontos para Crítico:");

        // Campos de armadura
        campoBonusCA = new JTextField();
        campoMaxDestreza = new JTextField();
        campoPenalidade = new JTextField();

        rotuloBonusCA = new JLabel("Bônus CA:");
        rotuloMaxDestreza = new JLabel("Máximo Destreza:");
        rotuloPenalidade = new JLabel("Penalidade:");

        // Preenche os campos do tipo do equipamento
        if (equipamento instanceof Arma) {

            Arma arma = (Arma) equipamento;

            campoTipoDano.setText(arma.getTipoDano());
            campoAlcance.setText(String.valueOf(arma.getAlcance()));
            campoCritico.setText(String.valueOf(arma.getPontosParaCritico()));

        } else if (equipamento instanceof Armadura) {

            Armadura armadura = (Armadura) equipamento;

            campoBonusCA.setText(String.valueOf(armadura.getBonusCA()));
            campoMaxDestreza.setText(String.valueOf(armadura.getMaxDestreza()));
            campoPenalidade.setText(String.valueOf(armadura.getPenalidade()));
        }

        painelCampos = new JPanel(new GridLayout(0, 2, 10, 10));

        painelPrincipal.add(painelCampos, BorderLayout.CENTER);

        // Botões
        JPanel painelBotoes = new JPanel();

        JButton botaoSalvar = new JButton("Salvar alterações");
        JButton botaoCancelar = new JButton("Cancelar");

        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoCancelar);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        // Mostra os campos do tipo do equipamento
        mostrarCamposDoTipo(equipamento.getTipo());

        // Botão salvar alterações
        botaoSalvar.addActionListener(e -> salvarAlteracoes());

        // Botão cancelar
        botaoCancelar.addActionListener(e -> dispose());

        add(painelPrincipal);
    }

    //mostra os campos comuns e os campos do tipo do equipamento
    private void mostrarCamposDoTipo(String tipo) {

        painelCampos.removeAll();

        // Campos comuns
        painelCampos.add(new JLabel("Nome:"));
        painelCampos.add(campoNome);

        painelCampos.add(new JLabel("Quantidade:"));
        painelCampos.add(campoQuantidade);

        painelCampos.add(new JLabel("Carga:"));
        painelCampos.add(campoCarga);

        painelCampos.add(new JLabel("Descrição:"));
        painelCampos.add(campoDescricao);

        // Separador
        painelCampos.add(new JLabel("-----"));
        painelCampos.add(new JLabel(tipo.toUpperCase()));

        // Campos do tipo
        if (tipo.equals("Arma")) {

            painelCampos.add(rotuloTipoDano);
            painelCampos.add(campoTipoDano);

            painelCampos.add(rotuloAlcance);
            painelCampos.add(campoAlcance);

            painelCampos.add(rotuloCritico);
            painelCampos.add(campoCritico);

        } else if (tipo.equals("Armadura")) {

            painelCampos.add(rotuloBonusCA);
            painelCampos.add(campoBonusCA);

            painelCampos.add(rotuloMaxDestreza);
            painelCampos.add(campoMaxDestreza);

            painelCampos.add(rotuloPenalidade);
            painelCampos.add(campoPenalidade);
        }

        painelCampos.revalidate();
        painelCampos.repaint();
    }

    //muda o equipamento com os dados dos campos e salva
    private void salvarAlteracoes() {

        try {

            String nome = campoNome.getText().trim();

            String erroNome = ValidadorNome.erro(nome);

            if (erroNome != null) {

                JOptionPane.showMessageDialog(
                        this,
                        erroNome,
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            int quantidade = Integer.parseInt(
                    campoQuantidade.getText().trim()
            );

            int carga = Integer.parseInt(campoCarga.getText().trim());

            String descricao = campoDescricao.getText().trim();

            // Campos comuns
            equipamento.setNome(nome);
            equipamento.setQuantidade(quantidade);
            equipamento.setCarga(carga);
            equipamento.setDescricao(descricao);

            // Campos do tipo
            if (equipamento instanceof Arma) {

                Arma arma = (Arma) equipamento;

                arma.setTipoDano(campoTipoDano.getText().trim());
                arma.setAlcance(
                        Double.parseDouble(campoAlcance.getText().trim())
                );
                arma.setPontosParaCritico(
                        Integer.parseInt(campoCritico.getText().trim())
                );

            } else if (equipamento instanceof Armadura) {

                Armadura armadura = (Armadura) equipamento;

                armadura.setBonusCA(
                        Integer.parseInt(campoBonusCA.getText().trim())
                );
                armadura.setMaxDestreza(
                        Integer.parseInt(campoMaxDestreza.getText().trim())
                );
                armadura.setPenalidade(
                        Integer.parseInt(campoPenalidade.getText().trim())
                );
            }

            // salvar() já chama os campos extras do tipo correto
            if (!equipamento.salvar()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Não foi possível salvar o equipamento.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Equipamento alterado com sucesso!"
            );

            dispose();

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha os campos numéricos corretamente.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
