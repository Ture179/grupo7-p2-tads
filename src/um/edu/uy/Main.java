package um.edu.uy;

import um.edu.uy.cargadordatos.CargadorDatos;
import um.edu.uy.entities.*;
import um.edu.uy.tads.hashtable.MyHashTable;
import um.edu.uy.tads.hashtable.MyHashTableImpl;

public class Main {
    public static void main(String[] args) {
        long inicio = System.currentTimeMillis();

        MyHashTable<Integer, Pelicula> peliculas = new MyHashTableImpl<>();
        MyHashTable<Integer, Genero> generos = new MyHashTableImpl<>();
        MyHashTable<Integer, Coleccion> colecciones = new MyHashTableImpl<>();
        MyHashTable<Integer, Actor> actores = new MyHashTableImpl<>();
        MyHashTable<Integer, Director> directores = new MyHashTableImpl<>();
        MyHashTable<Integer, Evaluacion> evaluaciones = new MyHashTableImpl<>();
        MyHashTable<Integer, Usuario> usuarios = new MyHashTableImpl<>();


        String pathPeliculas = "resources/movies_metadata.csv";
        String pathCreditos = "resources/credits.csv";
        String pathRatings = "resources/ratings_1mm.csv";

        CargadorDatos cargador = new CargadorDatos();
        cargador.cargarPeliculasDesdeCSV(pathPeliculas, peliculas, generos, colecciones);
        cargador.cargarCreditosDesdeCSV(pathCreditos, peliculas, actores, directores);
        cargador.cargarEvaluacionesDesdeCSV(pathRatings, evaluaciones, usuarios);

        cargador.imprimirResumen(peliculas, generos, colecciones, actores, directores, evaluaciones, usuarios);
        long fin = System.currentTimeMillis();
        System.out.println("Carga completa de los datos en " + (fin - inicio) + " ms");


        UMovie app = new UMovie();

        long inicio1 = System.currentTimeMillis();
        app.top5PeliculasPorIdioma(peliculas, evaluaciones);
        long fin1 = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de la consulta: " + (fin1 - inicio1) + " ms");

        long inicio2 = System.currentTimeMillis();
        app.top10MejorCalificacionMedia(peliculas, evaluaciones);
        long fin2 = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de la consulta: " + (fin2 - inicio2) + " ms");






    }
}
