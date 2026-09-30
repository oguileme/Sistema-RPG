package Telas;

import classes.Equipamento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TelaEquipamentos extends JFrame {

    private List<Equipamento> equipamentos = new ArrayList<>();

    private JList<String> listaEquipamentos;
    private JLabel rotuloAviso;
    private JButton botaoEditar;

    public TelaEquipamentos() {

        setTitle("Equipamentos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // Título
        JLabel titulo = new JLabel("Equipamentos");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        painelPrincipal.add(titulo, BorderLayout.NORTH);

        // Aviso de lista vazia
        rotuloAviso = new JLabel(" ", SwingConstants.CENTER);
        rotuloAviso.setForeground(Color.GRAY);

        // Lista dos equipamentos salvos
        listaEquipamentos = new JList<String>();
        listaEquipamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaEquipamentos.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane rolagem = new JScrollPane(listaEquipamentos);

        JPanel painelLista = new JPanel(new BorderLayout(0, 10));
        painelLista.add(rotuloAviso, BorderLayout.NORTH);
        painelLista.add(rolagem, BorderLayout.CENTER);

        painelPrincipal.add(painelLista, BorderLayout.CENTER);

        // Botões
        JPanel painelBotoes = new JPanel();

        JButton botaoCadastrar = new JButton("Cadastrar Equipamento");
        botaoEditar = new JButton("Editar selecionado");
        JButton botaoFechar = new JButton("Fechar");

        painelBotoes.add(botaoCadastrar);
        painelBotoes.add(botaoEditar);
        painelBotoes.add(botaoFechar);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        // Carrega os equipamentos salvos
        atualizarLista();

        // Botão cadastrar equipamento
        botaoCadastrar.addActionListener(e -> {
            new TelaCadastroEquipamento().setVisible(true);
        });

        // Botão editar selecionado
        botaoEditar.addActionListener(e -> editarSelecionado());

        // Duplo clique na lista abre a edição
        listaEquipamentos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evento) {
                if (evento.getClickCount() == 2) {
                    editarSelecionado();
                }
            }
        });

        // Atualiza a lista quando a tela volta a ser usada
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent evento) {
                atualizarLista();
            }
        });

        // Botão fechar
        botaoFechar.addActionListener(e -> dispose());

        add(painelPrincipal);
    }

    //carrega os equipamentos salvos de todas as pastas
    private void carregarEquipamentos() {

        equipamentos = Equipamento.listarTodos();
    }

    //mostra os equipamentos na lista
    private void atualizarLista() {

        carregarEquipamentos();

        DefaultListModel<String> modelo = new DefaultListModel<String>();

        for (Equipamento equipamento : equipamentos) {
            modelo.addElement(
                    equipamento.getNome() + " (" + equipamento.getTipo() + ")"
            );
        }

        listaEquipamentos.setModel(modelo);

        botaoEditar.setEnabled(!equipamentos.isEmpty());

        if (equipamentos.isEmpty()) {
            rotuloAviso.setText("Nenhum equipamento cadastrado ainda.");
        } else {
            rotuloAviso.setText(" ");
        }
    }

    //abre a tela de edição do equipamento selecionado
    private void editarSelecionado() {

        int posicao = listaEquipamentos.getSelectedIndex();

        if (posicao < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um equipamento na lista.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        new TelaEdicaoEquipamento(equipamentos.get(posicao)).setVisible(true);
    }

    public static void main(String[] args) {
        new TelaEquipamentos().setVisible(true);
    }
}
