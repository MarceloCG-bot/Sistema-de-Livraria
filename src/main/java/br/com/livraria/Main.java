package br.com.livraria;

import br.com.livraria.model.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Biblioteca biblioteca = new Biblioteca();

    public static void main(String[] args) {
        System.out.println("=== Sistema de Livraria ===");

        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro("Opção: ");
            processarOpcao(opcao);
        } while (opcao != 0);

        scanner.close();
        System.out.println("Sistema encerrado. 👋");
    }

    private static void exibirMenu() {
        System.out.println("\nEscolha uma opção:");
        System.out.println("1 - Cadastrar novo autor");
        System.out.println("2 - Cadastrar novo livro");
        System.out.println("3 - Ver autores cadastrados");
        System.out.println("4 - Ver livros disponíveis");
        System.out.println("5 - Ver todos os livros");
        System.out.println("6 - Buscar livros por título");
        System.out.println("7 - Emprestar um livro");
        System.out.println("8 - Devolver um livro");
        System.out.println("9 - Ver empréstimos");
        System.out.println("0 - Sair");
    }

    private static void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> cadastrarAutor();
            case 2 -> cadastrarLivro();
            case 3 -> verAutores();
            case 4 -> verLivrosDisponiveis();
            case 5 -> verTodosLivros();
            case 6 -> buscarLivros();
            case 7 -> emprestarLivro();
            case 8 -> devolverLivro();
            case 9 -> verEmprestimos();
            case 0 -> System.out.println("Encerrando...");
            default -> System.out.println("Opção inválida. Tente novamente.");
        }
    }

    // Métodos especializados (reduzem a complexidade do main)

    private static void cadastrarAutor() {
        System.out.print("ID do autor: ");
        int id = lerInteiro("");
        System.out.print("Nome do autor: ");
        String nome = scanner.nextLine();
        System.out.print("Data de nascimento (yyyy-MM-dd): ");
        LocalDate nasc = LocalDate.parse(scanner.nextLine());

        biblioteca.adicionarAutor(new Autor(id, nome, nasc));
        System.out.println("✅ Autor cadastrado!");
    }

    private static void cadastrarLivro() {
        System.out.print("ID do livro: ");
        int idLivro = lerInteiro("");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        verAutores();
        System.out.print("ID do autor: ");
        int idAutor = lerInteiro("");
        Autor autor = biblioteca.buscarAutorPorId(idAutor)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));
        biblioteca.adicionarLivro(new Livro(idLivro, titulo, autor));
        System.out.println("✅ Livro cadastrado!");
    }

    private static void verAutores() {
        System.out.println("\n👤 Autores cadastrados:");
        biblioteca.getAutores().forEach(a ->
                System.out.println(a.getId() + " - " + a.getNome() +
                        " (nasc. " + a.getDataNascimento() + ")"));
    }

    private static void verLivrosDisponiveis() {
        System.out.println("\n📚 Livros disponíveis:");
        biblioteca.listarLivrosDisponiveis().forEach(l ->
                System.out.println(l.getId() + " - " + l.getTitulo() +
                        " (" + l.getAutor().getNome() + ")"));
    }

    private static void verTodosLivros() {
        System.out.println("\n📖 Todos os livros cadastrados:");
        biblioteca.getLivros().forEach(l ->
                System.out.println(l.getId() + " - " + l.getTitulo() +
                        " (" + l.getAutor().getNome() + ") - " +
                        (l.isDisponivel() ? "Disponível" : "Emprestado")));
    }

    private static void buscarLivros() {
        System.out.print("Digite parte do título: ");
        String termo = scanner.nextLine();
        biblioteca.buscarLivrosPorTitulo(termo).forEach(l ->
                System.out.println(l.getId() + " - " + l.getTitulo() +
                        " (" + l.getAutor().getNome() + ")"));
    }

    private static void emprestarLivro() {
        verLivrosDisponiveis();
        System.out.print("Digite o ID do livro: ");
        int livroId = lerInteiro("");
        System.out.print("Nome do cliente: ");
        String nomeCliente = scanner.nextLine();

        Emprestimo e = biblioteca.emprestarLivro(
                biblioteca.getEmprestimos().size() + 1,
                livroId,
                nomeCliente,
                LocalDate.now()
        );

        System.out.println("✅ Empréstimo realizado!");
        System.out.println("📘 " + e.getLivro().getTitulo() + " → " + e.getNomeCliente());
    }

    private static void devolverLivro() {
        System.out.print("Digite o ID do empréstimo: ");
        int id = lerInteiro("");
        biblioteca.devolverLivro(id, LocalDate.now());
        System.out.println("✅ Livro devolvido!");
    }

    private static void verEmprestimos() {
        biblioteca.getEmprestimos().forEach(e -> {
            String status = e.isDevolvido() ? "Devolvido" : "Em aberto";
            System.out.println("ID " + e.getId() + " | " +
                    e.getLivro().getTitulo() + " → " + e.getNomeCliente() + " (" + status + ")");
        });
    }

    private static int lerInteiro(String mensagem) {
        if (!mensagem.isBlank()) System.out.print(mensagem);
        while (!scanner.hasNextInt()) {
            System.out.print("Digite um número válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }
}
