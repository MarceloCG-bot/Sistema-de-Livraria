package br.com.livraria.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Representa um autor de livros no sistema.
 * Campos obrigatórios: id, nome, dataNascimento.
 */
public class Autor {

    private final int id;               // Identificador único (imutável após criação)
    private String nome;                // Nome completo do autor
    private LocalDate dataNascimento;   // Data de nascimento do autor

    public Autor(int id, String nome, LocalDate dataNascimento) {
        if (id <= 0) {
            throw new IllegalArgumentException("id deve ser positivo.");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("nome é obrigatório.");
        }
        if (dataNascimento == null) {
            throw new IllegalArgumentException("dataNascimento é obrigatória.");
        }
        this.id = id;
        this.nome = nome.trim();
        this.dataNascimento = dataNascimento;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("nome é obrigatório.");
        }
        this.nome = nome.trim();
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        if (dataNascimento == null) {
            throw new IllegalArgumentException("dataNascimento é obrigatória.");
        }
        this.dataNascimento = dataNascimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Autor)) return false;
        Autor autor = (Autor) o;
        return id == autor.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                '}';
    }
}
