package um.edu.uy;

import um.edu.uy.cargadordatos.CargadorDatos;
import um.edu.uy.entities.*;
import um.edu.uy.tads.hashtable.MyHashTable;
import um.edu.uy.tads.hashtable.MyHashTableImpl;
import um.edu.uy.tads.linkedlist.MyLinkedListImpl;
import um.edu.uy.tads.linkedlist.MyList;

public class Main {
    public static void main(String[] args) {
        long inicio = System.currentTimeMillis();

        MyHashTable<Integer, Pelicula> peliculas = new MyHashTableImpl<>();
        MyHashTable<Integer, Genero> generos = new MyHashTableImpl<>();
        MyHashTable<Integer, Coleccion> colecciones = new MyHashTableImpl<>();
        MyHashTable<Integer, Actor> actores = new MyHashTableImpl<>();
        MyHashTable<Integer, Director> directores = new MyHashTableImpl<>();
        MyHashTable<Integer, Evaluacion> evaluaciones = new MyHashTableImpl<>();
        MyHashTable<String, Boolean> idiomas = new MyHashTableImpl<>();
        MyHashTable<Integer, Usuario> usuarios = new MyHashTableImpl<>();


        String pathPeliculas = "resources/movies_metadata.csv";
        String pathCreditos = "resources/credits.csv";
        String pathRatings = "resources/ratings_1mm.csv";

        CargadorDatos cargador = new CargadorDatos();
        cargador.cargarPeliculasDesdeCSV(pathPeliculas, peliculas, generos, colecciones, idiomas);
        cargador.cargarCreditosDesdeCSV(pathCreditos, peliculas, actores, directores);
        cargador.cargarEvaluacionesDesdeCSV(pathRatings, evaluaciones, usuarios);

        cargador.imprimirResumen(peliculas, generos, colecciones, actores, directores, evaluaciones, idiomas, usuarios);

        long fin = System.currentTimeMillis();
        System.out.println("Carga completa en " + (fin - inicio) + " ms");
    }
}
