package com.example.litealura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String titulo;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Autor> autores = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "libro_idiomas", joinColumns = @JoinColumn(name = "libro_id"))
    @Column(name = "idioma")
    private List<String> idiomas;
    private Double numeroDeDescargas;

    public  Libro(){

    }

    @Override
    public String toString() {
        return "-------\n" +
                "id=" + id + "\n" +
                ", titulo='" + titulo + '\'' + "\n"+
                ", idiomas=" + idiomas + "\n" +
                ", numeroDeDescargas=" + numeroDeDescargas + "\n"
                ;
    }

    public Libro(DatosLibros datosLibros){
        this.titulo = datosLibros.titulo();
        this.idiomas = datosLibros.idiomas();
        this.numeroDeDescargas = OptionalDouble.of(Double.valueOf(datosLibros.numeroDeDescargas())).orElse(0);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }
}
