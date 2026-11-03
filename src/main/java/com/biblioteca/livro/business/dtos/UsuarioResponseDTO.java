package com.biblioteca.livro.business.dtos;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        String role
) {}