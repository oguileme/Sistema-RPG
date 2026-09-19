package classes;
import java.util.List;

public class Rolagem implements Rolavel{
    private List<String> tiposDado;
    private List<Integer> quantidades;
    private int somaResultado;
    private int resultadoFinal; //nao vai ter quando o cara cadastrar a rolagem
    private int data; // nao vai ter quando o cara cadastrar a rolagem

    public Rolagem(){
    }

    public void rolavel(){}//envia as 5 informações
}
