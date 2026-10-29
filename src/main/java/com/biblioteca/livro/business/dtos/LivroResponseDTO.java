package com.biblioteca.livro.business.dtos;

public record LivroResponseDTO(
        Long id,
        String isbn,
        String autor,
        String titulo,
        String categoria,
        Integer anoPublicacao,
        Integer quantidadeTotal,
        Integer quantidadeDisponivel
) {
}
