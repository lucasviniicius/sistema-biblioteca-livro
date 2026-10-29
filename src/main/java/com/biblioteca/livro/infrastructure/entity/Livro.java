package com.biblioteca.livro.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "isbn", length = 20)
    private String isbn;

    @Column(name = "autor", length = 150)
    private String autor;

    @Column(name = "titulo", length = 200)
    private String titulo;

    @Column(name = "categoria", length = 100)
    private String categoria;

    @Column(name = "ano_publicacao")
    private Integer anoPublicacao;

    @Column(name = "quantidade_total")
    private Integer quantidadeTotal;

    @Column(name = "quantidade_disponivel")
    private Integer quantidadeDisponivel;
}
