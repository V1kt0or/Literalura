package com.example.litealura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;
    private String  fechaDeNacimiento;
    private String  fechaDeFallecimiento;

    @ManyToMany(mappedBy = "autores") // Especifica la clave foránea en la tabla Disciplina
    private List<Libro> libro = new ArrayList<>();

    public Autor() {
    }

    public Autor(DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.fechaDeFallecimiento = datosAutor.fechaDeFallecimiento();
        this.fechaDeNacimiento = datosAutor.fechaDeNacimiento();
    }
    @Override
    public String toString() {
        return "-------\n" +
                "Autor:\n" +
                "nombre: " + nombre + "\n" +
                "fecha de nacimiento: " + (fechaDeNacimiento != null ? fechaDeNacimiento : "N/A") + "\n" +
                "fecha de fallecimiento: " + (fechaDeFallecimiento != null ? fechaDeFallecimiento : "N/A") + "\n";
    }
    public List<Libro> getLibro() {
        return libro;
    }

    public void setLibro(List<Libro> libro) {
        this.libro = libro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(String fechaDeFallecimiento) {
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }
}
