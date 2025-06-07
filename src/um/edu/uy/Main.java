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

        String pathPeliculas = "resources/movies_metadata.csv";
        String pathCreditos = "resources/credits.csv";

        CargadorDatos cargador = new CargadorDatos();
        cargador.cargarPeliculasDesdeCSV(pathPeliculas, peliculas, generos, colecciones);
        cargador.cargarCreditosDesdeCSV(pathCreditos, peliculas, actores);

        long fin = System.currentTimeMillis();
        System.out.println("Carga completa en " + (fin - inicio) + " ms");

    }
}
