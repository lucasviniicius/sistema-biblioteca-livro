package com.biblioteca.livro.business.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LivroRequestDTO(
        @NotBlank
        @Size(max = 20)
        String isbn,

        @NotBlank
        @Size(max = 150)
        String autor,

        @NotBlank
        @Size(max = 200)
        String titulo,

        @NotBlank
        @Size(max = 100)
        String categoria,

        @NotNull
        Integer anoPublicacao,

        @NotNull
        Integer quantidadeTotal
) {
}
