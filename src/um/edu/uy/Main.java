package um.edu.uy;

import um.edu.uy.cargadordatos.CargadorDatos;
import um.edu.uy.entities.*;
import um.edu.uy.tads.hashtable.MyHashTable;
import um.edu.uy.tads.hashtable.MyHashTableImpl;

public class Main {
    public static void main(String[] args) {
        String path = "/Users/acaetano/Downloads/DATASETS v2/movies_metadata.csv";

        MyHashTable<Integer, Pelicula> peliculas = new MyHashTableImpl<>();
        MyHashTable<Integer, Genero> generos = new MyHashTableImpl<>();
        MyHashTable<Integer, Coleccion> colecciones = new MyHashTableImpl<>();

        CargadorDatos cargador = new CargadorDatos();
        cargador.cargarPeliculasDesdeCSV(path, peliculas, generos, colecciones);

        int idDeEjemplo = 862; // Toy Story
        Pelicula p = peliculas.obtener(idDeEjemplo);

        if (p != null) {
            System.out.println("Película encontrada:");
            System.out.println("ID: " + p.getId());
            System.out.println("Título: " + p.getTitulo());
            System.out.println("Idioma original: " + p.getIdomaOriginal());
            System.out.println("Ingresos: " + p.getIngresos());
        } else {
            System.out.println("No se encontró la película con ID " + idDeEjemplo);
        }
    }
}
