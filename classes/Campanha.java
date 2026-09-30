package classes;
import java.util.List;

public class Campanha {
    private String nome;
    private String descricao;
    private Usuario mestre;
    private List<Usuario> jogadores;
    private List<Ficha> fichas;

    //**Método construtor de Campanha */
    public Campanha(String nome, String descricao, Usuario mestre) {
        this.nome = nome;
        this.descricao = descricao;
        this.mestre = mestre;
    }

    //**Método getters */
    public String getNome() {
       return nome;
    }

    public String getDescricao(){
        return descricao;
    }

    public Usuario getMestre() {
        return mestre;
    }

    //**Método setters*/
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    //sem setter de mestre, pois o mestre é definido no momento da criação da campanha e não deve ser alterado posteriormente.

    public void addPlayer(Usuario player) {
        // lógica para adicionar um jogador à campanha
        this.jogadores.add(player);
    }

    public void addFicha(Ficha ficha){
        this.fichas.add(ficha);
    }

}
