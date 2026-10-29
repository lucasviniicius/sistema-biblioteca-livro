package com.biblioteca.livro.business.mapper;

import com.biblioteca.livro.business.dtos.LivroRequestDTO;
import com.biblioteca.livro.business.dtos.LivroResponseDTO;
import com.biblioteca.livro.infrastructure.entity.Livro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LivroMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quantidadeDisponivel", source = "quantidadeTotal")
    Livro toEntity(LivroRequestDTO request);

    LivroResponseDTO toResponseDTO(Livro livro);
}
