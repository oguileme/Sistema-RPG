package classes;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

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
                                new InputStreamReader(
                                        new FileInputStream(arquivo),
                                        StandardCharsets.UTF_8
                                )
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

    // Retorna false se não foi possível gravar
    public boolean salvarUsuario() {

        try {

            File pasta = new File("Usuarios");

            // Cria a pasta caso ainda não exista
            if (!pasta.exists()) {
                pasta.mkdirs();
            }

            try (PrintWriter arquivo = new PrintWriter(Files.newBufferedWriter(
                    new File(pasta, usuario + ".txt").toPath(),
                    StandardCharsets.UTF_8))) {

                arquivo.println("Nome: " + nome);
                arquivo.println("Usuário: " + usuario);
                arquivo.println("E-mail: " + email);
                arquivo.println("Senha: " + senha);
            }

        } catch (IOException | RuntimeException e) {
            return false;
        }

        return true;
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
                                new InputStreamReader(
                                        new FileInputStream(arquivo),
                                        StandardCharsets.UTF_8
                                )
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