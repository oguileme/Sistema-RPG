package Telas;

import classes.Arma;
import classes.Armadura;
import classes.Equipamento;
import classes.ValidadorNome;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroEquipamento extends JFrame {

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

    // Painel dos campos, mudado de acordo com o tipo escolhido
    private JPanel painelCampos;

    public TelaCadastroEquipamento() {

        setTitle("Cadastro de Equipamento");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // Título
        JLabel titulo = new JLabel("Cadastro de Equipamento");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        // Tipo do equipamento
        campoTipo = new JComboBox<String>(
                new String[]{"Equipamento", "Arma", "Armadura"}
        );

        JPanel painelTipo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        painelTipo.add(new JLabel("Tipo de equipamento:"));
        painelTipo.add(campoTipo);

        JPanel painelTopo = new JPanel(new BorderLayout(10, 10));
        painelTopo.add(titulo, BorderLayout.NORTH);
        painelTopo.add(painelTipo, BorderLayout.SOUTH);

        painelPrincipal.add(painelTopo, BorderLayout.NORTH);

        // Campos
        campoNome = new JTextField();
        campoQuantidade = new JTextField("1");
        campoCarga = new JTextField();
        campoDescricao = new JTextField();

        campoTipoDano = new JTextField();
        campoAlcance = new JTextField();
        campoCritico = new JTextField();

        campoBonusCA = new JTextField();
        campoMaxDestreza = new JTextField();
        campoPenalidade = new JTextField();

        rotuloTipoDano = new JLabel("Tipo de Dano:");
        rotuloAlcance = new JLabel("Alcance:");
        rotuloCritico = new JLabel("Pontos para Crítico:");

        rotuloBonusCA = new JLabel("Bônus CA:");
        rotuloMaxDestreza = new JLabel("Máximo Destreza:");
        rotuloPenalidade = new JLabel("Penalidade:");

        painelCampos = new JPanel(new GridLayout(0, 2, 10, 10));

        painelPrincipal.add(painelCampos, BorderLayout.CENTER);

        // Botões
        JPanel painelBotoes = new JPanel();

        JButton botaoSalvar = new JButton("Salvar");
        JButton botaoCancelar = new JButton("Cancelar");

        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoCancelar);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        // Mostra os campos do tipo escolhido
        mostrarCamposDoTipo((String) campoTipo.getSelectedItem());

        // Muda os campos quando o tipo muda
        campoTipo.addActionListener(e ->
                mostrarCamposDoTipo((String) campoTipo.getSelectedItem())
        );

        // Botão salvar
        botaoSalvar.addActionListener(e -> salvarEquipamento());

        // Botão cancelar
        botaoCancelar.addActionListener(e -> dispose());

        add(painelPrincipal);
    }

    //mostra os campos comuns e os campos do tipo escolhido
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

    //cria o equipamento do tipo escolhido e salva
    private void salvarEquipamento() {

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

            String tipo = (String) campoTipo.getSelectedItem();

            Equipamento equipamento;

            if (tipo.equals("Arma")) {

                equipamento = new Arma(
                        nome,
                        quantidade,
                        carga,
                        descricao,
                        campoTipoDano.getText().trim(),
                        Double.parseDouble(campoAlcance.getText().trim()),
                        Integer.parseInt(campoCritico.getText().trim())
                );

            } else if (tipo.equals("Armadura")) {

                equipamento = new Armadura(
                        nome,
                        quantidade,
                        carga,
                        descricao,
                        Integer.parseInt(campoBonusCA.getText().trim()),
                        Integer.parseInt(campoMaxDestreza.getText().trim()),
                        Integer.parseInt(campoPenalidade.getText().trim())
                );

            } else {

                equipamento = new Equipamento(
                        nome,
                        quantidade,
                        carga,
                        descricao
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
                    "Equipamento cadastrado com sucesso!"
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

    public static void main(String[] args) {
        new TelaCadastroEquipamento().setVisible(true);
    }
}
