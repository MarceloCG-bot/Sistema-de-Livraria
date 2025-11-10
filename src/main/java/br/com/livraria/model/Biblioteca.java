package br.com.livraria.model;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Representa a biblioteca, com listas de autores, livros e empréstimos.
 * Oferece operações para cadastro, busca, empréstimo e devolução.
 */
public class Biblioteca {

    private final List<Livro> livros = new ArrayList<>();
    private final List<Autor> autores = new ArrayList<>();
    private final List<Emprestimo> emprestimos = new ArrayList<>();

    // ---------- CONSTRUTOR: inicializa com alguns dados ----------
    public Biblioteca() {
        inicializarDados();
    }

    private void inicializarDados() {
        // Autores
        Autor machado = new Autor(1, "Machado de Assis", LocalDate.of(1839, 6, 21));
        Autor clarice = new Autor(2, "Clarice Lispector", LocalDate.of(1920, 12, 10));
        Autor jorge = new Autor(3, "Jorge Amado", LocalDate.of(1912, 8, 10));

        autores.addAll(List.of(machado, clarice, jorge));

        // Livros
        Livro domCasmurro = new Livro(1, "Dom Casmurro", machado);
        Livro horaEstrela = new Livro(2, "A Hora da Estrela", clarice);
        Livro gabriela = new Livro(3, "Gabriela, Cravo e Canela", jorge);

        livros.addAll(List.of(domCasmurro, horaEstrela, gabriela));
    }

    // ---------- Acesso seguro às listas (somente leitura) ----------
    public List<Livro> getLivros() {
        return Collections.unmodifiableList(livros);
    }

    public List<Autor> getAutores() {
        return Collections.unmodifiableList(autores);
    }

    public List<Emprestimo> getEmprestimos() {
        return Collections.unmodifiableList(emprestimos);
    }

    // ---------- Cadastro ----------
    public void adicionarAutor(Autor autor) {
        Objects.requireNonNull(autor, "autor é obrigatório.");
        if (autores.stream().anyMatch(a -> a.getId() == autor.getId())) {
            throw new IllegalArgumentException("Já existe autor com id=" + autor.getId());
        }
        autores.add(autor);
    }

    public void adicionarLivro(Livro livro) {
        Objects.requireNonNull(livro, "livro é obrigatório.");
        if (livros.stream().anyMatch(l -> l.getId() == livro.getId())) {
            throw new IllegalArgumentException("Já existe livro com id=" + livro.getId());
        }
        if (autores.stream().noneMatch(a -> a.getId() == livro.getAutor().getId())) {
            throw new IllegalStateException("Autor do livro não está cadastrado na biblioteca.");
        }
        livros.add(livro);
    }

    // ---------- Busca utilitária ----------
    public Optional<Autor> buscarAutorPorId(int id) {
        return autores.stream().filter(a -> a.getId() == id).findFirst();
    }

    public Optional<Livro> buscarLivroPorId(int id) {
        return livros.stream().filter(l -> l.getId() == id).findFirst();
    }

    public Optional<Emprestimo> buscarEmprestimoPorId(int id) {
        return emprestimos.stream().filter(e -> e.getId() == id).findFirst();
    }

    public List<Livro> buscarLivrosPorTitulo(String termo) {
        if (termo == null || termo.isBlank()) return List.of();
        String t = termo.trim().toLowerCase();
        return livros.stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(t))
                .collect(Collectors.toList());
    }

    public List<Livro> listarLivrosDisponiveis() {
        return livros.stream().filter(Livro::isDisponivel).collect(Collectors.toList());
    }

    // ---------- Empréstimo / Devolução ----------
    public Emprestimo emprestarLivro(int emprestimoId, int livroId, String nomeCliente, LocalDate dataEmprestimo) {
        if (emprestimos.stream().anyMatch(e -> e.getId() == emprestimoId)) {
            throw new IllegalArgumentException("Já existe empréstimo com id=" + emprestimoId);
        }

        Livro livro = buscarLivroPorId(livroId)
                .orElseThrow(() -> new NoSuchElementException("Livro id=" + livroId + " não encontrado."));

        if (!livro.isDisponivel()) {
            throw new IllegalStateException("Livro id=" + livroId + " não está disponível para empréstimo.");
        }

        Emprestimo emprestimo = new Emprestimo(emprestimoId, livro, nomeCliente, dataEmprestimo);
        livro.setDisponivel(false);
        emprestimos.add(emprestimo);
        return emprestimo;
    }

    public void devolverLivro(int emprestimoId, LocalDate dataDevolucao) {
        Emprestimo e = buscarEmprestimoPorId(emprestimoId)
                .orElseThrow(() -> new NoSuchElementException("Empréstimo id=" + emprestimoId + " não encontrado."));

        if (e.isDevolvido()) {
            throw new IllegalStateException("Empréstimo id=" + emprestimoId + " já foi devolvido.");
        }

        e.setDataDevolucao(Objects.requireNonNull(dataDevolucao, "dataDevolucao é obrigatória."));
        e.getLivro().setDisponivel(true);
    }
}
