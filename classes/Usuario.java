package classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Usuario {

    private String nome;
    private String usuario;
    private String email;
    private String senha;

    public Usuario(String nome, String usuario, String email, String senha) {
        this.nome = nome;
        this.usuario = usuario;
        this.email = email;
        this.senha = senha;
    }

    public void salvarUsuario() {

        try {

            File pasta = new File("Usuarios");


            FileWriter arquivo = new FileWriter(
                    "Usuarios/" + usuario + ".txt"
            );

            arquivo.write("Nome: " + nome + "\n");
            arquivo.write("Usuário: " + usuario + "\n");
            arquivo.write("E-mail: " + email + "\n");
            arquivo.write("Senha: " + senha + "\n");

            arquivo.close();

        } catch (IOException e) {
            System.out.println("Erro ao salvar o usuário.");
        }
    }
}