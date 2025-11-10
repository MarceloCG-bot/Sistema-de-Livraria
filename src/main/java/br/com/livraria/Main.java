package br.com.livraria;

import br.com.livraria.model.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        System.out.println("=== Sistema de Livraria ===");

        do {
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
            System.out.print("Opção: ");

            opcao = lerInteiro(scanner);

            switch (opcao) {
                case 1 -> { // cadastrar autor
                    try {
                        System.out.print("ID do autor: ");
                        int idAutor = lerInteiro(scanner);

                        System.out.print("Nome do autor: ");
                        String nome = scanner.nextLine();

                        System.out.print("Data de nascimento (yyyy-MM-dd): ");
                        LocalDate nasc = lerData(scanner);

                        biblioteca.adicionarAutor(new Autor(idAutor, nome, nasc));
                        System.out.println("✅ Autor cadastrado!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("⚠️ Erro de validação: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("⚠️ Erro: " + e.getMessage());
                    }
                }

                case 2 -> { // cadastrar livro
                    try {
                        System.out.print("ID do livro: ");
                        int idLivro = lerInteiro(scanner);

                        System.out.print("Título do livro: ");
                        String titulo = scanner.nextLine();

                        if (biblioteca.getAutores().isEmpty()) {
                            System.out.println("⚠️ Não há autores cadastrados. Cadastre um autor primeiro (opção 1).");
                            break;
                        }

                        System.out.println("\nSelecione o autor pelo ID:");
                        biblioteca.getAutores().forEach(a ->
                                System.out.println(a.getId() + " - " + a.getNome())
                        );
                        System.out.print("ID do autor: ");
                        int idAutorLivro = lerInteiro(scanner);

                        Autor autor = biblioteca.buscarAutorPorId(idAutorLivro)
                                .orElseThrow(() -> new NoSuchElementException("Autor não encontrado."));

                        Livro livro = new Livro(idLivro, titulo, autor);
                        biblioteca.adicionarLivro(livro);
                        System.out.println("✅ Livro cadastrado!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("⚠️ Erro de validação: " + e.getMessage());
                    } catch (NoSuchElementException e) {
                        System.out.println("⚠️ " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("⚠️ Erro: " + e.getMessage());
                    }
                }

                case 3 -> { // ver autores
                    System.out.println("\n👤 Autores cadastrados:");
                    biblioteca.getAutores().stream()
                            .sorted(Comparator.comparing(Autor::getNome))
                            .forEach(a ->
                                    System.out.println(a.getId() + " - " + a.getNome() +
                                            " (nasc. " + a.getDataNascimento() + ")")
                            );
                }

                case 4 -> { // livros disponíveis
                    System.out.println("\n📚 Livros disponíveis:");
                    biblioteca.listarLivrosDisponiveis().stream()
                            .sorted(Comparator.comparing(Livro::getTitulo))
                            .forEach(l -> System.out.println(l.getId() + " - " + l.getTitulo() +
                                    " (" + l.getAutor().getNome() + ")"));
                }

                case 5 -> { // todos os livros
                    System.out.println("\n📖 Todos os livros cadastrados:");
                    biblioteca.getLivros().stream()
                            .sorted(Comparator.comparing(Livro::getTitulo))
                            .forEach(l -> System.out.println(l.getId() + " - " + l.getTitulo() +
                                    " (" + l.getAutor().getNome() + ") - " +
                                    (l.isDisponivel() ? "Disponível" : "Emprestado")));
                }

                case 6 -> { // buscar por título
                    System.out.print("Digite parte do título para buscar: ");
                    String termo = scanner.nextLine();
                    var encontrados = biblioteca.buscarLivrosPorTitulo(termo).stream()
                            .sorted(Comparator.comparing(Livro::getTitulo))
                            .toList();

                    System.out.println("\n🔎 Resultado da busca por \"" + termo + "\":");
                    if (encontrados.isEmpty()) {
                        System.out.println("Nenhum livro encontrado.");
                    } else {
                        encontrados.forEach(l -> System.out.println(
                                l.getId() + " - " + l.getTitulo() +
                                        " (" + l.getAutor().getNome() + ") - " +
                                        (l.isDisponivel() ? "Disponível" : "Emprestado")
                        ));
                    }
                }

                case 7 -> { // emprestar
                    var disponiveis = biblioteca.listarLivrosDisponiveis();
                    System.out.println("\n📚 Livros disponíveis para empréstimo:");
                    if (disponiveis.isEmpty()) {
                        System.out.println("Nenhum livro disponível no momento.");
                        break;
                    }
                    disponiveis.forEach(l -> System.out.println(
                            l.getId() + " - " + l.getTitulo() + " (" + l.getAutor().getNome() + ")"
                    ));

                    System.out.print("Digite o ID do livro para emprestar: ");
                    int livroId = lerInteiro(scanner);

                    System.out.print("Digite o nome do cliente: ");
                    String nomeCliente = scanner.nextLine();

                    try {
                        // agora o retorno do método é usado corretamente
                        Emprestimo novoEmprestimo = biblioteca.emprestarLivro(
                                biblioteca.getEmprestimos().size() + 1,
                                livroId,
                                nomeCliente,
                                LocalDate.now()
                        );

                        System.out.println("\n✅ Empréstimo realizado com sucesso!");
                        System.out.println("📘 Livro: " + novoEmprestimo.getLivro().getTitulo());
                        System.out.println("👤 Cliente: " + novoEmprestimo.getNomeCliente());
                        System.out.println("📅 Data do Empréstimo: " + novoEmprestimo.getDataEmprestimo());
                        System.out.println("🔖 Status: " + (novoEmprestimo.isDevolvido() ? "Devolvido" : "Em aberto"));

                    } catch (Exception e) {
                        System.out.println("⚠️ Erro: " + e.getMessage());
                    }
                }

                case 8 -> { // devolver
                    System.out.println("\n📋 Empréstimos em aberto:");
                    var emAberto = biblioteca.getEmprestimos().stream()
                            .filter(e -> !e.isDevolvido())
                            .toList();

                    if (emAberto.isEmpty()) {
                        System.out.println("Nenhum empréstimo em aberto para devolver.");
                        break;
                    }

                    emAberto.forEach(e -> System.out.println(
                            "ID " + e.getId() +
                                    " | Livro: " + e.getLivro().getTitulo() +
                                    " | Cliente: " + e.getNomeCliente() +
                                    " | Empréstimo: " + e.getDataEmprestimo()
                    ));

                    System.out.print("Digite o ID do empréstimo para devolver: ");
                    int emprestimoId = lerInteiro(scanner);

                    try {
                        // capturamos o empréstimo ANTES de devolver para exibir os detalhes depois
                        Emprestimo emprestimo = biblioteca.buscarEmprestimoPorId(emprestimoId)
                                .orElseThrow(() -> new NoSuchElementException("Empréstimo não encontrado."));

                        biblioteca.devolverLivro(emprestimoId, LocalDate.now());

                        System.out.println("\n✅ Livro devolvido com sucesso!");
                        System.out.println("📘 Livro: " + emprestimo.getLivro().getTitulo());
                        System.out.println("👤 Cliente: " + emprestimo.getNomeCliente());
                        System.out.println("📅 Empréstimo em: " + emprestimo.getDataEmprestimo());
                        System.out.println("📦 Devolvido em: " + emprestimo.getDataDevolucao());
                        System.out.println("🔖 Status: " + (emprestimo.isDevolvido() ? "Devolvido" : "Em aberto"));

                    } catch (Exception e) {
                        System.out.println("⚠️ Erro: " + e.getMessage());
                    }
                }


                case 9 -> { // ver empréstimos
                    System.out.println("\n🗂️ Empréstimos:");
                    if (biblioteca.getEmprestimos().isEmpty()) {
                        System.out.println("Nenhum empréstimo encontrado.");
                    } else {
                        biblioteca.getEmprestimos().forEach(e -> {
                            String status = e.isDevolvido()
                                    ? "DEVOLVIDO em " + e.getDataDevolucao()
                                    : "ABERTO";
                            System.out.println(
                                    "ID " + e.getId() +
                                            " | Livro: " + e.getLivro().getTitulo() +
                                            " | Cliente: " + e.getNomeCliente() +
                                            " | Empréstimo: " + e.getDataEmprestimo() +
                                            " | Status: " + status
                            );
                        });
                    }
                }

                case 0 -> System.out.println("Encerrando o sistema... 👋");

                default -> System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 0);

        scanner.close();
    }

    private static int lerInteiro(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Digite um número válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // limpar buffer
        return valor;
    }

    private static LocalDate lerData(Scanner scanner) {
        while (true) {
            String s = scanner.nextLine().trim();
            try {
                return LocalDate.parse(s); // formato yyyy-MM-dd
            } catch (DateTimeParseException e) {
                System.out.print("Data inválida. Use yyyy-MM-dd: ");
            }
        }
    }
}
