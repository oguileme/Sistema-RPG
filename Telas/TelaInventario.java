package Telas;

import classes.Equipamento;
import classes.Ficha;
import classes.Inventario;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TelaInventario extends JFrame {

    private final Ficha ficha;

    private final Inventario inventario;

    // lista dos itens que o personagem carrega
    private JList<String> listaItens;

    // lista do catálogo, para escolher o que pegar
    private JList<String> listaCatalogo;

    private JLabel rotuloCarga;

    //catalogo mostrado na lista da direita, guardado para a seleção
    //continuar valendo depois que a lista foi desenhada
    private List<Equipamento> catalogo = new ArrayList<>();

    public TelaInventario(Ficha ficha) {

        this.ficha = ficha;
        this.inventario = ficha.getInventario();

        setTitle("Inventário - " + ficha.getNome());
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        painelPrincipal.add(
                new JLabel("Inventário de " + ficha.getNome()),
                BorderLayout.NORTH
        );

        // =========================
        // AS DUAS LISTAS
        // =========================

        // O que está na mochila
        listaItens = new JList<String>();
        listaItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaItens.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane rolagemItens = new JScrollPane(listaItens);

        JPanel painelItens = new JPanel(new BorderLayout(0, 5));
        painelItens.setBorder(
                BorderFactory.createTitledBorder("Carregando")
        );

        painelItens.add(rolagemItens, BorderLayout.CENTER);

        rotuloCarga = new JLabel(" ", SwingConstants.CENTER);
        rotuloCarga.setFont(new Font("Arial", Font.BOLD, 13));
        painelItens.add(rotuloCarga, BorderLayout.SOUTH);

        // O que existe no catálogo para pegar
        listaCatalogo = new JList<String>();
        listaCatalogo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaCatalogo.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane rolagemCatalogo = new JScrollPane(listaCatalogo);

        JPanel painelCatalogo = new JPanel(new BorderLayout(0, 5));
        painelCatalogo.setBorder(
                BorderFactory.createTitledBorder("Disponível")
        );

        painelCatalogo.add(rolagemCatalogo, BorderLayout.CENTER);

        JPanel painelListas = new JPanel(new GridLayout(1, 2, 10, 0));

        painelListas.add(painelItens);
        painelListas.add(painelCatalogo);

        painelPrincipal.add(painelListas, BorderLayout.CENTER);

        // =========================
        // BOTÕES
        // =========================

        JPanel painelBotoes = new JPanel();

        JButton botaoPegar = new JButton("Pegar");
        JButton botaoLargar = new JButton("Largar");
        JButton botaoSalvar = new JButton("Salvar");
        JButton botaoFechar = new JButton("Fechar");

        painelBotoes.add(botaoPegar);
        painelBotoes.add(botaoLargar);
        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoFechar);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        // =========================
        // AÇÕES
        // =========================

        botaoPegar.addActionListener(e -> pegar());

        botaoLargar.addActionListener(e -> largar());

        botaoSalvar.addActionListener(e -> salvar());

        botaoFechar.addActionListener(e -> dispose());

        atualizarListas();

        add(painelPrincipal);
    }

    //pega o item selecionado no catálogo
    private void pegar() {

        Equipamento disponivel = selecionadoDaCatalogo();

        if (disponivel == null) {
            mostrarAviso("Selecione um item em \"Disponível\".");
            return;
        }

        //recarrega do arquivo, para não colocar na mochila o mesmo
        //objeto que a tela de catálogo está usando
        Equipamento doArquivo = Equipamento.carregarPorTipo(
                disponivel.getTipo().toString(),
                disponivel.getNome()
        );

        if (doArquivo == null) {
            mostrarAviso("Esse equipamento não foi mais encontrado.");
            return;
        }

        if (!inventario.addEquipamento(doArquivo)) {
            mostrarAviso(
                    "\"" + doArquivo.getNome() + "\" não cabe: "
                            + "falta carga no inventário."
            );
            return;
        }

        atualizarListas();
    }

    //larga o item selecionado na mochila
    private void largar() {

        int posicao = listaItens.getSelectedIndex();

        if (posicao < 0) {
            mostrarAviso("Selecione um item em \"Carregando\".");
            return;
        }

        Equipamento carregado = inventario.getEquipamentos().get(posicao);

        if (!inventario.removeEquipamento(carregado)) {
            mostrarAviso("Não foi possível largar esse item.");
            return;
        }

        atualizarListas();
    }

    //grava o inventário no arquivo da ficha
    private void salvar() {

        if (!ficha.salvarFicha()) {

            mostrarAviso("Não foi possível gravar o inventário.");
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Inventário salvo."
        );
    }

    //atualiza as duas listas e o rótulo de carga
    private void atualizarListas() {

        DefaultListModel<String> modeloItens = new DefaultListModel<String>();

        for (Equipamento equipamento : inventario.getEquipamentos()) {
            modeloItens.addElement(
                    equipamento.getNome()
                            + " x" + equipamento.getQuantidade()
                            + " (" + equipamento.getTipo() + ", "
                            + equipamento.getCargaTotal() + " de carga)"
            );
        }

        listaItens.setModel(modeloItens);

        catalogo = Equipamento.listarTodos();

        DefaultListModel<String> modeloCatalogo = new DefaultListModel<String>();

        for (Equipamento equipamento : catalogo) {
            modeloCatalogo.addElement(
                    equipamento.getNome()
                            + " (" + equipamento.getTipo() + ", "
                            + equipamento.getCargaTotal() + " de carga)"
            );
        }

        listaCatalogo.setModel(modeloCatalogo);

        int atual = inventario.getCargaAtual();
        int max = inventario.getCargaMax();

        if (max <= 0) {
            rotuloCarga.setText(
                    "Carga: " + atual + " (sem limite definido)"
            );
        } else {

            rotuloCarga.setText(
                    "Carga: " + atual + " / " + max
            );

            //avisa quando não há mais espaço
            if (atual > max) {
                rotuloCarga.setForeground(Color.RED);
            } else if (atual == max) {
                rotuloCarga.setForeground(new Color(180, 110, 0));
            } else {
                rotuloCarga.setForeground(Color.DARK_GRAY);
            }
        }

        if (inventario.isEmpty() && catalogo.isEmpty()) {
            rotuloCarga.setText(
                    "Nenhum equipamento cadastrado ainda."
            );
        }
    }

    private Equipamento selecionadoDaCatalogo() {

        int posicao = listaCatalogo.getSelectedIndex();

        if (posicao < 0 || posicao >= catalogo.size()) {
            return null;
        }

        return catalogo.get(posicao);
    }

    private void mostrarAviso(String mensagem) {

        JOptionPane.showMessageDialog(
                this,
                mensagem,
                "Aviso",
                JOptionPane.WARNING_MESSAGE
        );
    }
}
