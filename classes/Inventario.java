package classes;
import java.util.List;

public class Inventario {
    private List<Equipamento> equipamentos;
    private int cargaMax;
    private int cargaAtual;

    public Inventario(List<Equipamento> equipamentos, int cargaMax) {
        this.equipamentos = equipamentos;
        this.cargaMax = cargaMax;
        this.cargaAtual = 0;
    }

    public List<Equipamento> getEquipamentos() {
        return equipamentos;
    }

    public int getCargaMax() {
        return cargaMax;
    }

    public int getCargaAtual() {
        return cargaAtual;
    }

    public void setCargaAtual(int cargaAtual) {
        this.cargaAtual = cargaAtual;
    }

    public void setCargaMax(int cargaMax) {
        this.cargaMax = cargaMax;
    }

    public void mostrarEquipamentos() {
        System.out.println("Equipamentos no inventário:");
        for (Equipamento equipamento : equipamentos) {
            System.out.println("- " + equipamento.getNome() + " (Quantidade: " + equipamento.getQuantidade() + ", Carga: " + equipamento.getCarga() + ")");
        }
    }

    public boolean addEquipamento(Equipamento equipamento){
        if(this.cargaAtual + equipamento.getCarga() >= this.cargaMax){
            return false;
        }
        this.cargaAtual += equipamento.getCarga();
        this.equipamentos.add(equipamento);
        return true;
    }

    public boolean removeEquipamento(Equipamento equipamento){
        if(!equipamentos.contains(equipamento)){
            return false;
        }
        this.cargaAtual -= equipamento.getCarga();
        this.equipamentos.remove(equipamento);
        return true;
    }

}
