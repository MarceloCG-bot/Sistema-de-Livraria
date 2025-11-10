package br.com.livraria.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa um livro no sistema de livraria.
 * Campos obrigatórios: id, titulo, autor.
 */
public class Livro {

    private final int id;                  // Identificador único (imutável após criação)
    private String titulo;                 // Título do livro
    private Autor autor;                   // Autor do livro (associação)
    private boolean disponivel;            // Indica se está disponível para empréstimo
    private final LocalDateTime dataCadastro;    // Data/hora de cadastro
    private LocalDateTime dataAtualizacao; // Data/hora da última atualização

    public Livro(int id, String titulo, Autor autor) {
        if (id <= 0) throw new IllegalArgumentException("id deve ser positivo.");
        if (titulo == null || titulo.isBlank()) throw new IllegalArgumentException("titulo é obrigatório.");
        if (autor == null) throw new IllegalArgumentException("autor é obrigatório.");

        this.id = id;
        this.titulo = titulo.trim();
        this.autor = autor;
        this.disponivel = true; // padrão: entra disponível
        this.dataCadastro = LocalDateTime.now();
        this.dataAtualizacao = this.dataCadastro;
    }

    private void tocarAtualizacao() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    public int getId() { return id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) throw new IllegalArgumentException("titulo é obrigatório.");
        this.titulo = titulo.trim();
        tocarAtualizacao();
    }

    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) {
        if (autor == null) throw new IllegalArgumentException("autor é obrigatório.");
        this.autor = autor;
        tocarAtualizacao();
    }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
        tocarAtualizacao();
    }

    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Livro)) return false;
        Livro livro = (Livro) o;
        return id == livro.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor=" + (autor != null ? autor.getNome() : "null") +
                ", disponivel=" + disponivel +
                ", dataCadastro=" + dataCadastro +
                ", dataAtualizacao=" + dataAtualizacao +
                '}';
    }
}
