package classes;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Inventario {

    // carga que o personagem aguenta
    private int cargaMax;

    private final List<Equipamento> equipamentos;

    public Inventario() {
        this(new ArrayList<>(), 0);
    }

    public Inventario(int cargaMax) {
        this(new ArrayList<>(), cargaMax);
    }

    public Inventario(List<Equipamento> equipamentos, int cargaMax) {

        this.equipamentos = equipamentos == null
                ? new ArrayList<>()
                : equipamentos;

        this.cargaMax = cargaMax;
    }

    public List<Equipamento> getEquipamentos() {
        return equipamentos;
    }

    public int getCargaMax() {
        return cargaMax;
    }

    public void setCargaMax(int cargaMax) {
        this.cargaMax = cargaMax;
    }

    // a carga atual é sempre a soma dos itens, nunca um numero guardado
    public int getCargaAtual() {
        return getCargaTotal();
    }

    // soma da carga de todos os itens, contando a quantidade de cada um
    public int getCargaTotal() {

        int total = 0;

        for (Equipamento equipamento : equipamentos) {
            total += equipamento.getCargaTotal();
        }

        return total;
    }

    public int getCargaRestante() {
        return cargaMax - getCargaTotal();
    }

    public int size() {
        return equipamentos.size();
    }

    public boolean isEmpty() {
        return equipamentos.isEmpty();
    }

    /**
     * Coloca um equipamento no inventário.
     *
     * @return false se não couber na carga, ou se o nome já está lá
     */
    public boolean addEquipamento(Equipamento equipamento) {

        if (equipamento == null) {
            return false;
        }

        // já existe um item com esse nome: somar as quantidades em vez
        // de criar uma entrada repetida
        Equipamento jaExiste = buscarPorNome(equipamento.getNome());

        if (jaExiste != null) {
            return somarQuantidade(jaExiste, equipamento.getQuantidade());
        }

        if (!cabe(equipamento.getCargaTotal())) {
            return false;
        }

        return equipamentos.add(equipamento);
    }

    /**
     * Tira um equipamento do inventário.
     *
     * Se o item está lá com mais unidades do que o pedido, tira só as
     * que existem, para um clique não apagar o item inteiro à toa.
     *
     * @return false se o item não está no inventário
     */
    public boolean removeEquipamento(Equipamento equipamento) {

        if (equipamento == null) {
            return false;
        }

        Equipamento noInventario = buscarPorNome(equipamento.getNome());

        if (noInventario == null) {
            return false;
        }

        int quantidade = Math.min(noInventario.getQuantidade(), equipamento.getQuantidade());

        return somarQuantidade(noInventario, -quantidade);
    }

    //procura ignorando maiusculas, como o nome dos arquivos
    public Equipamento buscarPorNome(String nome) {

        if (nome == null) {
            return null;
        }

        for (Equipamento equipamento : equipamentos) {

            if (equipamento.getNome().equalsIgnoreCase(nome.trim())) {
                return equipamento;
            }
        }

        return null;
    }

    public boolean contem(Equipamento equipamento) {
        return buscarPorNome(equipamento == null ? null : equipamento.getNome()) != null;
    }

    public void limpar() {
        equipamentos.clear();
    }

    //soma ou subtrai a quantidade de um item; quantidade 0 remove a entrada
    private boolean somarQuantidade(Equipamento equipamento, int quantidade) {

        int total = equipamento.getQuantidade() + quantidade;

        if (total <= 0) {
            return equipamentos.remove(equipamento);
        }

        //getCargaTotal() ainda conta a quantidade antiga, então a carga
        //que entra (ou sai) é a de uma unidade vezes o que foi pedido
        int cargaNova = getCargaTotal() + equipamento.getCarga() * quantidade;

        if (!cabe(cargaNova)) {
            return false;
        }

        equipamento.setQuantidade(total);

        return true;
    }

    //cabe mais essa carga dentro do limite?
    private boolean cabe(int carga) {
        return carga <= cargaMax;
    }

    // =========================
    // GRAVAÇÃO NO ARQUIVO DA FICHA
    // =========================

    // "Equipamento: Espada Longa | Tipo: Arma | Quantidade: 1"
    // Só o nome e o tipo: a descrição e o resto dos campos continuam
    // guardados no arquivo do equipamento, para não duplicar dados.
    public void salvar(PrintWriter w) {

        w.println();
        w.println("--- INVENTÁRIO ---");
        w.println("Carga Máxima: " + cargaMax);

        for (Equipamento equipamento : equipamentos) {

            w.println(
                    "Equipamento: " + equipamento.getNome()
                            + " | Tipo: " + equipamento.getTipo()
                            + " | Quantidade: " + equipamento.getQuantidade()
            );
        }
    }

    /**
     * Reconstrói o inventário a partir das linhas da seção
     * "--- INVENTÁRIO ---".
     */
    public static Inventario ler(List<String> linhas) {

        Inventario inventario = new Inventario();
        List<String> itens = new ArrayList<>();

        for (String linha : linhas) {

            if (linha.startsWith("Carga Máxima:")) {

                try {
                    inventario.cargaMax = Integer.parseInt(
                            linha.substring("Carga Máxima:".length()).trim()
                    );
                } catch (NumberFormatException e) {
                    //carga ilegível: fica 0, e os itens ainda são lidos
                    inventario.cargaMax = 0;
                }

            } else if (linha.startsWith("Equipamento:")) {

                itens.add(linha);
            }
        }

        for (String linha : itens) {

            Equipamento equipamento = lerItem(linha);

            if (equipamento != null) {
                //ignora o limite de carga na leitura: um inventário
                //salvo não deve perder itens só porque a carga mudou
                inventario.equipamentos.add(equipamento);
            }
        }

        return inventario;
    }

    // "Equipamento: Espada Longa | Tipo: Arma | Quantidade: 1"
    private static Equipamento lerItem(String linha) {

        String nome = "";
        String tipo = "Equipamento";
        int quantidade = 1;

        for (String parte : linha.split("\\|")) {

            int i = parte.indexOf(":");

            if (i < 0) {
                continue;
            }

            String chave = parte.substring(0, i).trim();
            String valor = parte.substring(i + 1).trim();

            switch (chave) {
                case "Equipamento":
                    nome = valor;
                    break;
                case "Tipo":
                    tipo = valor;
                    break;
                case "Quantidade":
                    try {
                        quantidade = Integer.parseInt(valor);
                    } catch (NumberFormatException e) {
                        quantidade = 1;
                    }
                    break;
                default:
                    break;
            }
        }

        if (nome.isEmpty()) {
            return null;
        }

        //se o equipamento não existe mais no catálogo, entra um item
        //simples com o mesmo nome, para a lista não ficar Mentirosa
        Equipamento doCatalogo = Equipamento.carregarPorTipo(tipo, nome);

        if (doCatalogo != null) {
            doCatalogo.setQuantidade(Math.max(1, quantidade));
            return doCatalogo;
        }

        return new Equipamento(nome, Math.max(1, quantidade), 0, "");
    }
}
