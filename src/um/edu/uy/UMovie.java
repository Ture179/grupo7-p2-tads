package um.edu.uy;

import um.edu.uy.entities.*;
import um.edu.uy.tads.arraylist.MyArrayList;
import um.edu.uy.tads.arraylist.MyArrayListImpl;
import um.edu.uy.tads.hashtable.MyHashTable;
import um.edu.uy.tads.hashtable.MyHashTableImpl;


public class UMovie {
    private MyHashTable<Integer, Pelicula> peliculas;
    private MyHashTable<Integer, Genero> generos;
    private MyHashTable<Integer, Coleccion> colecciones;
    private MyHashTable<Integer, Actor> actores;
    private MyHashTable<Integer, Director> directores;
    private MyHashTable<Integer, Usuario> usuarios;
    private MyHashTable<Integer, Evaluacion> evaluaciones;

    public static void top5PeliculasPorIdioma(MyHashTable<Integer, Pelicula> peliculas,
                                              MyHashTable<Integer, Evaluacion> evaluaciones) {

        String[] idiomas = {"en", "fr", "it", "es", "pt"};
        MyHashTable<Integer, Integer> conteo = new MyHashTableImpl<>();

        // Contar evaluaciones por ID de película
        for (Evaluacion e : evaluaciones.obtenerElementos()) {
            int idPelicula = e.getId_pelicula();
            Integer actual = conteo.obtener(idPelicula);
            if (actual == null) {
                try {
                    conteo.insertar(idPelicula, 1);
                } catch (Exception ignored) {}
            } else {
                try {
                    conteo.borrar(idPelicula);
                    conteo.insertar(idPelicula, actual + 1);
                } catch (Exception ignored) {}
            }
        }

        // Para cada idioma: obtener y mostrar top 5
        for (String idioma : idiomas) {
            MyArrayList<Pelicula> lista = new MyArrayListImpl<>();

            for (Pelicula p : peliculas.obtenerElementos()) {
                if (idioma.equals(p.getIdomaOriginal())) {
                    lista.agregar(p);
                }
            }

            // Ordenar por cantidad de evaluaciones (desc)
            lista.sort((a, b) -> {
                int evalA = conteo.obtener(a.getId()) != null ? conteo.obtener(a.getId()) : 0;
                int evalB = conteo.obtener(b.getId()) != null ? conteo.obtener(b.getId()) : 0;
                return Integer.compare(evalB, evalA);
            });

            System.out.println("Top 5 películas en idioma: " + idioma.toUpperCase());
            for (int i = 0; i < Math.min(5, lista.size()); i++) {
                Pelicula p = lista.get(i);
                int cantidad = conteo.obtener(p.getId()) != null ? conteo.obtener(p.getId()) : 0;
                System.out.printf(" ▶️ ID: %d | Título: %s | Evaluaciones: %d | Idioma: %s\n", p.getId(), p.getTitulo(), cantidad, p.getIdomaOriginal());
            }
            System.out.println();
        }
    }


    public static void top10MejorCalificacionMedia(MyHashTable<Integer, Pelicula> peliculas,
                                                   MyHashTable<Integer, Evaluacion> evaluaciones) {

        MyHashTable<Integer, Double> sumas = new MyHashTableImpl<>();
        MyHashTable<Integer, Integer> conteos = new MyHashTableImpl<>();

        // Acumular sumas y conteos
        for (Evaluacion e : evaluaciones.obtenerElementos()) {
            int idPelicula = e.getId_pelicula();
            double puntaje = e.getPuntaje();

            Double sumaActual = sumas.obtener(idPelicula);
            Integer cantidadActual = conteos.obtener(idPelicula);

            try {
                if (sumaActual == null) {
                    sumas.insertar(idPelicula, puntaje);
                    conteos.insertar(idPelicula, 1);
                } else {
                    sumas.borrar(idPelicula);
                    conteos.borrar(idPelicula);
                    sumas.insertar(idPelicula, sumaActual + puntaje);
                    conteos.insertar(idPelicula, cantidadActual + 1);
                }
            } catch (Exception ignored) {}
        }

        // Crear lista de películas que tienen evaluaciones
        MyArrayList<Pelicula> lista = new MyArrayListImpl<>();
        for (Pelicula p : peliculas.obtenerElementos()) {
            int id = p.getId();
            if (sumas.contieneClave(id) && conteos.contieneClave(id)) {
                lista.agregar(p);
            }
        }

        // Ordenar por promedio descendente
        lista.sort((a, b) -> {
            double promA = sumas.obtener(a.getId()) / conteos.obtener(a.getId());
            double promB = sumas.obtener(b.getId()) / conteos.obtener(b.getId());
            return Double.compare(promB, promA);
        });

        // Imprimir top 10
        System.out.println("Top 10 películas con mejor calificación media:");
        for (int i = 0; i < Math.min(10, lista.size()); i++) {
            Pelicula p = lista.get(i);
            double promedio = sumas.obtener(p.getId()) / conteos.obtener(p.getId());
            System.out.printf(" ▶️ ID: %d | Título: %s | Calificación media: %.2f\n", p.getId(), p.getTitulo(), promedio);
        }
    }

    public static void top5ColeccionesMasIngresos(){

    }

    public static void top10DirectorMejorCalificacion(MyHashTable<Integer, Pelicula> peliculas, MyHashTable<Integer, Evaluacion> evaluaciones, MyHashTable<Integer, Director> directores){


    }

    


    


    public MyHashTable<Integer, Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(MyHashTable<Integer, Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public MyHashTable<Integer, Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(MyHashTable<Integer, Genero> generos) {
        this.generos = generos;
    }

    public MyHashTable<Integer, Coleccion> getColecciones() {
        return colecciones;
    }

    public void setColecciones(MyHashTable<Integer, Coleccion> colecciones) {
        this.colecciones = colecciones;
    }

    public MyHashTable<Integer, Actor> getActores() {
        return actores;
    }

    public void setActores(MyHashTable<Integer, Actor> actores) {
        this.actores = actores;
    }

    public MyHashTable<Integer, Director> getDirectores() {
        return directores;
    }

    public void setDirectores(MyHashTable<Integer, Director> directores) {
        this.directores = directores;
    }

    public MyHashTable<Integer, Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(MyHashTable<Integer, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public MyHashTable<Integer, Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(MyHashTable<Integer, Evaluacion> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }
}
