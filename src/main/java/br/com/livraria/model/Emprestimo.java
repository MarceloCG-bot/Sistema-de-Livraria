package br.com.livraria.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Representa um empréstimo de livro no sistema de livraria.
 * Campos obrigatórios: id, livro, nomeCliente, dataEmprestimo.
 * dataDevolucao pode ser nula se o livro ainda não foi devolvido.
 */
public class Emprestimo {

    private final int id;              // Identificador único do empréstimo
    private Livro livro;               // Livro emprestado
    private String nomeCliente;        // Nome do cliente que pegou o livro
    private LocalDate dataEmprestimo;  // Data do empréstimo
    private LocalDate dataDevolucao;   // Data da devolução (pode ser nula)

    public Emprestimo(int id, Livro livro, String nomeCliente, LocalDate dataEmprestimo) {
        if (id <= 0) throw new IllegalArgumentException("id deve ser positivo.");
        if (livro == null) throw new IllegalArgumentException("livro é obrigatório.");
        if (nomeCliente == null || nomeCliente.isBlank()) throw new IllegalArgumentException("nomeCliente é obrigatório.");
        if (dataEmprestimo == null) throw new IllegalArgumentException("dataEmprestimo é obrigatória.");

        this.id = id;
        this.livro = livro;
        this.nomeCliente = nomeCliente.trim();
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = null; // ainda não devolvido por padrão
    }

    public int getId() { return id; }

    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) {
        if (livro == null) throw new IllegalArgumentException("livro é obrigatório.");
        this.livro = livro;
    }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) {
        if (nomeCliente == null || nomeCliente.isBlank()) throw new IllegalArgumentException("nomeCliente é obrigatório.");
        this.nomeCliente = nomeCliente.trim();
    }

    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        if (dataEmprestimo == null) throw new IllegalArgumentException("dataEmprestimo é obrigatória.");
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public boolean isDevolvido() {
        return dataDevolucao != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Emprestimo)) return false;
        Emprestimo that = (Emprestimo) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "id=" + id +
                ", livro=" + (livro != null ? livro.getTitulo() : "null") +
                ", nomeCliente='" + nomeCliente + '\'' +
                ", dataEmprestimo=" + dataEmprestimo +
                ", dataDevolucao=" + (dataDevolucao != null ? dataDevolucao : "PENDENTE") +
                '}';
    }
}
