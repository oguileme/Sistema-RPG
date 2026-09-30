package classes;

import java.io.*;

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

    public static boolean autenticar(String usuario, String senha) {

        File arquivo = new File(
                "Usuarios/" + usuario + ".txt"
        );

        if (!arquivo.exists()) {
            return false;
        }

        try {

            BufferedReader leitor = new BufferedReader(
                    new FileReader(arquivo)
            );

            String linha;
            String senhaArquivo = null;

            while ((linha = leitor.readLine()) != null) {

                if (linha.startsWith("Senha: ")) {
                    senhaArquivo = linha.substring(7);
                }
            }

            leitor.close();

            return senha.equals(senhaArquivo);

        } catch (IOException e) {

            return false;
        }
    }
}