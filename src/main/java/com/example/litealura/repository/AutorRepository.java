package com.example.litealura.repository;

import com.example.litealura.model.Autor;
import com.example.litealura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository <Autor, Long> {
    Optional<Autor> findByNombreContainsIgnoreCase(String nombreSerie);

    @Query("SELECT a FROM Autor a WHERE a.fechaDeNacimiento <= :year AND (a.fechaDeFallecimiento IS NULL OR a.fechaDeFallecimiento >= :year)")
    List<Autor> findAutoresVivosEnAnio(int year);
}
