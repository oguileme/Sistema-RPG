package Telas;

import classes.Ficha;
import classes.ResultadoRolagem;
import classes.Rolagem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TelaRolagem extends JFrame {

    private final Ficha ficha;

    private JTextField campoFormula;
    private JTextField campoBonus;
    private JTextField campoDescricao;
    private JTable tabelaHistorico;
    private JLabel rotuloResultado;
    private DefaultTableModel modeloHistorico;

    public TelaRolagem(Ficha ficha) {

        this.ficha = ficha;

        setTitle("Rolagem - " + ficha.getNome());
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        painelPrincipal.add(
                new JLabel("Rolar dados para " + ficha.getNome()),
                BorderLayout.NORTH
        );

        // =========================
        // CAMPOS DA ROLAGEM
        // =========================

        JPanel painelCampos = new JPanel(new GridLayout(0, 2, 10, 10));

        painelCampos.add(new JLabel("Fórmula:"));

        campoFormula = new JTextField("1d20");
        painelCampos.add(campoFormula);

        painelCampos.add(new JLabel("Bônus:"));

        campoBonus = new JTextField("0");
        painelCampos.add(campoBonus);

        painelCampos.add(new JLabel("Descrição:"));

        campoDescricao = new JTextField();
        painelCampos.add(campoDescricao);

        painelPrincipal.add(
                new JScrollPane(painelCampos),
                BorderLayout.NORTH
        );

        // =========================
        // HISTÓRICO
        // =========================

        modeloHistorico = new DefaultTableModel(
                new Object[]{"Data", "Fórmula", "Total"}, 0
        ) {

            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        tabelaHistorico = new JTable(modeloHistorico);
        tabelaHistorico.setFont(new Font("Arial", Font.PLAIN, 13));

        // Mostra os detalhes de cada termo sorteado logo abaixo
        tabelaHistorico.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                mostrarDetalhe();
            }
        });

        JScrollPane rolagemHistorico = new JScrollPane(tabelaHistorico);

        rotuloResultado = new JLabel(" ", SwingConstants.LEFT);
        rotuloResultado.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel painelHistorico = new JPanel(new BorderLayout(5, 5));
        painelHistorico.setBorder(
                BorderFactory.createTitledBorder("Histórico")
        );

        painelHistorico.add(rolagemHistorico, BorderLayout.CENTER);
        painelHistorico.add(rotuloResultado, BorderLayout.SOUTH);

        painelPrincipal.add(painelHistorico, BorderLayout.CENTER);

        // =========================
        // BOTÕES
        // =========================

        JPanel painelBotoes = new JPanel();

        JButton botaoRolar = new JButton("Rolar");
        JButton botaoLimpar = new JButton("Limpar histórico");
        JButton botaoFechar = new JButton("Fechar");

        painelBotoes.add(botaoRolar);
        painelBotoes.add(botaoLimpar);
        painelBotoes.add(botaoFechar);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        // =========================
        // AÇÕES
        // =========================

        botaoRolar.addActionListener(e -> rolar());

        botaoLimpar.addActionListener(e -> limparHistorico());

        botaoFechar.addActionListener(e -> dispose());

        // Já entra com o histórico que estava gravado na ficha
        carregarHistorico();

        add(painelPrincipal);
    }

    //sorteia a fórmula e grava a rolagem no histórico da ficha
    private void rolar() {

        String formula = campoFormula.getText().trim().toLowerCase();

        if (formula.isEmpty()) {
            mostrarAviso("Informe a fórmula, por exemplo: 2d8+1d20");
            return;
        }

        int bonus;

        try {
            bonus = Integer.parseInt(campoBonus.getText().trim());
        } catch (NumberFormatException e) {
            mostrarAviso("O bônus precisa ser um número inteiro.");
            return;
        }

        Rolagem rolagem;

        try {
            rolagem = new Rolagem(
                    campoDescricao.getText().trim(),
                    bonus
            );

            rolagem.adicionarTermos(formula);
            rolagem.rolar();

        } catch (IllegalArgumentException e) {
            //a mensagem do próprio dado/resultado já diz o que está errado
            mostrarAviso(e.getMessage());
            return;
        }

        ficha.addRolagem(rolagem);

        if (!ficha.salvarFicha()) {

            //A rolagem ficou só na memória: desfaz para não mostrar
            //no histórico algo que não foi gravado
            ficha.getRolagens().remove(rolagem);

            mostrarAviso("Não foi possível gravar a rolagem na ficha.");
            return;
        }

        carregarHistorico();

        //Deixa a linha nova selecionada para o detalhe aparecer junto
        int ultima = modeloHistorico.getRowCount() - 1;

        if (ultima >= 0) {
            tabelaHistorico.setRowSelectionInterval(ultima, ultima);
        }
    }

    //preenche a tabela com as rolagens da ficha
    private void carregarHistorico() {

        modeloHistorico.setRowCount(0);

        List<Rolagem> rolagens = ficha.getRolagens();

        for (int i = rolagens.size() - 1; i >= 0; i--) {
            //da mais recente para a mais antiga
            modeloHistorico.addRow(
                    new Object[]{
                            rolagens.get(i).getDataFormatada(),
                            descricaoDosTermos(rolagens.get(i)),
                            rolagens.get(i).getResultadoFinal()
                    }
            );
        }

        if (rolagens.isEmpty()) {
            rotuloResultado.setText("Nenhuma rolagem registrada ainda.");
        }
    }

    //mostra os valores de cada dado da rolagem selecionada
    private void mostrarDetalhe() {

        int linha = tabelaHistorico.getSelectedRow();

        //a tabela mostra da mais recente para a mais antiga
        int indice = ficha.getRolagens().size() - 1 - linha;

        if (linha < 0 || indice < 0 || indice >= ficha.getRolagens().size()) {
            return;
        }

        Rolagem rolagem = ficha.getRolagens().get(indice);

        StringBuilder texto = new StringBuilder("<html>");

        for (ResultadoRolagem resultado : rolagem.getResultados()) {
            texto.append(resultado).append("<br>");
        }

        texto.append("<b>Total: ").append(rolagem.getResultadoFinal())
                .append("</b></html>");

        rotuloResultado.setText(texto.toString());
    }

    //"2d8 + 1d20", para a coluna da tabela
    private String descricaoDosTermos(Rolagem rolagem) {

        List<String> partes = new ArrayList<>();

        for (ResultadoRolagem resultado : rolagem.getResultados()) {
            partes.add(
                    resultado.getQuantidade()
                            + String.valueOf(resultado.getDado())
            );
        }

        String texto = String.join(" + ", partes);

        if (rolagem.getBonus() != 0) {

            texto = texto.isEmpty()
                    ? String.valueOf(rolagem.getBonus())
                    : texto + " + " + rolagem.getBonus();
        }

        return texto.isEmpty() ? "-" : texto;
    }

    //apaga todo o histórico depois de pedir confirmação
    private void limparHistorico() {

        if (ficha.getRolagens().isEmpty()) {
            mostrarAviso("Não há rolagem para apagar.");
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Apagar as " + ficha.getRolagens().size()
                        + " rolagens desta ficha?\n"
                        + "Essa ação não pode ser desfeita.",
                "Limpar histórico",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (resposta != JOptionPane.YES_OPTION) {
            return;
        }

        ficha.getRolagens().clear();

        if (!ficha.salvarFicha()) {

            mostrarAviso("Não foi possível gravar a ficha.");
            return;
        }

        carregarHistorico();
    }

    private void mostrarAviso(String mensagem) {

        rotuloResultado.setText(" ");

        JOptionPane.showMessageDialog(
                this,
                mensagem,
                "Aviso",
                JOptionPane.WARNING_MESSAGE
        );
    }
}
