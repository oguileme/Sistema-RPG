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



}
