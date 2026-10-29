package com.biblioteca.livro.infrastructure.repository;

import com.biblioteca.livro.infrastructure.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    boolean existsByIsbn(String isbn);

    Optional<Livro> findByIsbn(String isbn);
}
