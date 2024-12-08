package com.example.litealura.principal;

import com.example.litealura.model.*;
import com.example.litealura.repository.AutorRepository;
import com.example.litealura.repository.LibroRepository;
import com.example.litealura.services.ConsumoApi;
import com.example.litealura.services.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {


    }

    public  void muestraMenu() {
        var json = consumoApi.obtenerDatos(URL_BASE);

        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo 
                    2 - Listar libros registrados
                    3 - Listar autores  registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idiomas
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    buscarLibros();
                    break;
                case 3:
                    buscarAutores();
                    break;
                case 4:
                    listarAutoresVivosEnAnio();
                    break;
                case 5:
                    buscarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

        private DatosLibros getDatosLibro() {
            System.out.println("Ingrese el nombre que desea buscar");
            var titulo = teclado.nextLine();
            var json = consumoApi.obtenerDatos(URL_BASE+"?search="+titulo.replace(" ","+"));
            var datosBusqueda =  convierteDatos.obtenerDatos(json, Datos.class);
            Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                    .filter(l -> l.titulo().toUpperCase().contains(titulo.toUpperCase()))
                    .findFirst();
            System.out.println(json);
            return libroBuscado.orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        }

    private void buscarLibro() {
        DatosLibros datos = getDatosLibro();
        Libro libro = new Libro(datos);
        List<Autor> autores = datos.autores().stream()
                .map(datosAutor -> new Autor(datosAutor))
                .collect(Collectors.toList());
        libro.setAutores(autores);

        autorRepository.saveAll(autores); // Guardar autores primero
        libroRepository.save(libro);
        System.out.println(datos);
    }

    private List<DatosLibros> getDatosLibros() {

        var json = consumoApi.obtenerDatos(URL_BASE);
        var datosBusqueda =  convierteDatos.obtenerDatos(json, Datos.class);
        List<DatosLibros> resultados = datosBusqueda.resultados();

        return resultados;
    }

        private void buscarLibros() {
            List<DatosLibros> listaDatosLibros = getDatosLibros();

            for (DatosLibros datos : listaDatosLibros) {

                Optional<Libro> libroExistente = libroRepository.findByTitulo(datos.titulo());
                if (libroExistente.isPresent()) {
                    continue; // Saltar este libro
                }

                Libro libro = new Libro(datos);
                List<Autor> autores = datos.autores().stream()
                        .map(Autor::new)
                        .collect(Collectors.toList());
                libro.setAutores(autores);
                autores.forEach(autor -> autor.getLibro().add(libro));
                libroRepository.save(libro);
             }
            buscarLibrosR();

    }
    public void buscarAutores() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    public void buscarLibrosR() {
        List<Libro> libros = libroRepository.findAll();
        libros.forEach(System.out::println);
    }

    public void buscarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma que desea buscar");
        var idioma = teclado.nextLine();
        List<Libro> libros = libroRepository.findByIdioma(idioma);
        libros.forEach(System.out::println);
    }

    public void listarAutoresVivosEnAnio() {
        System.out.println("Ingrese el anio que desea buscar");
        int year = teclado.nextInt();
        List<Autor> autoresVivos = autorRepository.findAutoresVivosEnAnio(year);
        autoresVivos.forEach(autor -> {
            System.out.println("Autor: " + autor.getNombre());
            System.out.println("Fecha de nacimiento: " + autor.getFechaDeNacimiento());
            System.out.println("Fecha de fallecimiento: " + autor.getFechaDeFallecimiento());
        });
    }
}
