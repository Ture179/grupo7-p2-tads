package um.edu.uy;

import um.edu.uy.cargadordatos.CargadorDatos;
import um.edu.uy.entities.*;
        import um.edu.uy.tads.hashtable.MyHashTable;
import um.edu.uy.tads.hashtable.MyHashTableImpl;

public class Main {
    public static void main(String[] args) {
        String pathPeliculas = "/Users/acaetano/Downloads/DATASETS v2/movies_metadata.csv";
        String pathCreditos = "/Users/acaetano/Downloads/DATASETS v2/credits.csv";

        MyHashTable<Integer, Pelicula> peliculas = new MyHashTableImpl<>();
        MyHashTable<Integer, Genero> generos = new MyHashTableImpl<>();
        MyHashTable<Integer, Coleccion> colecciones = new MyHashTableImpl<>();
        MyHashTable<Integer, Actor> actores = new MyHashTableImpl<>();

        CargadorDatos cargador = new CargadorDatos();

        long inicio = System.currentTimeMillis();

        cargador.cargarPeliculasDesdeCSV(pathPeliculas, peliculas, generos, colecciones);
        cargador.cargarCreditosDesdeCSV(pathCreditos, peliculas, actores);

        long fin = System.currentTimeMillis();
        System.out.println("Carga completa en " + (fin - inicio) + " ms");

        // Prueba de impresión
        int idDeEjemplo = 862; // Toy Story
        Pelicula p = peliculas.obtener(idDeEjemplo);

        if (p != null) {
            System.out.println("\nPelícula encontrada:");
            System.out.println("ID: " + p.getId());
            System.out.println("Título: " + p.getTitulo());
            System.out.println("Idioma original: " + p.getIdomaOriginal());
            System.out.println("Ingresos: " + p.getIngresos());
            System.out.println("Colección: " + p.getIdColeccion());
            System.out.print("Géneros: ");
            for (int i = 0; i < p.getGeneros().size(); i++) {
                System.out.print(p.getGeneros().get(i) + " ");
            }
            System.out.print("\nActores: ");
            for (int i = 0; i < p.getActores().size(); i++) {
                System.out.print(p.getActores().get(i) + " ");
            }
            System.out.println("\nDirector ID: " + p.getIdDirector());
        } else {
            System.out.println("No se encontró la película con ID " + idDeEjemplo);
        }
    }
}
