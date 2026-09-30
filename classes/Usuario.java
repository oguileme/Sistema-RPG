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

    public String getNome() {
        return this.nome;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public static Usuario carregarUsuario(String usuario) {

        File arquivo = new File(
                "Usuarios/" + usuario + ".txt"
        );

        if (!arquivo.exists()) {
            return null;
        }

        try (
                BufferedReader leitor =
                        new BufferedReader(
                                new FileReader(arquivo)
                        )
        ) {

            String nome = "";
            String nomeUsuario = "";
            String email = "";
            String senha = "";

            String linha;

            while ((linha = leitor.readLine()) != null) {

                if (linha.startsWith("Nome: ")) {
                    nome = linha.substring(6);
                }

                else if (linha.startsWith("Usuário: ")) {
                    nomeUsuario = linha.substring(9);
                }

                else if (linha.startsWith("E-mail: ")) {
                    email = linha.substring(8);
                }

                else if (linha.startsWith("Senha: ")) {
                    senha = linha.substring(7);
                }
            }

            return new Usuario(
                    nome,
                    nomeUsuario,
                    email,
                    senha
            );

        } catch (IOException e) {

            return null;
        }
    }

    public void salvarUsuario() {

        try {

            File pasta = new File("Usuarios");

            // Cria a pasta caso ainda não exista
            if (!pasta.exists()) {
                pasta.mkdirs();
            }

            try (
                    FileWriter arquivo = new FileWriter(
                            "Usuarios/" + usuario + ".txt"
                    )
            ) {

                arquivo.write("Nome: " + nome + "\n");
                arquivo.write("Usuário: " + usuario + "\n");
                arquivo.write("E-mail: " + email + "\n");
                arquivo.write("Senha: " + senha + "\n");
            }

        } catch (IOException e) {
            System.out.println("Erro ao salvar o usuário.");
        }
    }

    public static Usuario autenticar(String usuario, String senha) {

        File arquivo = new File(
                "Usuarios/" + usuario + ".txt"
        );

        if (!arquivo.exists()) {
            return null;
        }

        try (
                BufferedReader leitor =
                        new BufferedReader(
                                new FileReader(arquivo)
                        )
        ) {

            String linha;

            String nomeArquivo = null;
            String usuarioArquivo = null;
            String emailArquivo = null;
            String senhaArquivo = null;

            while ((linha = leitor.readLine()) != null) {

                if (linha.startsWith("Nome: ")) {
                    nomeArquivo = linha.substring(6);
                }

                else if (linha.startsWith("Usuário: ")) {
                    usuarioArquivo = linha.substring(9);
                }

                else if (linha.startsWith("E-mail: ")) {
                    emailArquivo = linha.substring(8);
                }

                else if (linha.startsWith("Senha: ")) {
                    senhaArquivo = linha.substring(7);
                }
            }

            if (senha.equals(senhaArquivo)) {

                return new Usuario(
                        nomeArquivo,
                        usuarioArquivo,
                        emailArquivo,
                        senhaArquivo
                );
            }

            return null;

        } catch (IOException e) {

            return null;
        }
    }
}