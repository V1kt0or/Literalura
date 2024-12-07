package com.example.litealura.repository;

import com.example.litealura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository <Libro, Long> {
    Optional<Libro> findByTituloContainsIgnoreCase(String nombreSerie);

    Optional<Libro> findByTitulo(String titulo);

    @Query("SELECT l FROM Libro l JOIN l.idiomas idiomas WHERE idiomas = :idioma")
    List<Libro> findByIdioma(String idioma);

    @Query("SELECT l FROM Libro l JOIN FETCH l.autores")
    List<Libro> findAllWithAutores();


}
