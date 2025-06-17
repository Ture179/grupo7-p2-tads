package um.edu.uy;

import um.edu.uy.cargadordatos.CargadorDatos;
import um.edu.uy.entities.*;
import um.edu.uy.tads.hashtable.MyHashTable;
import um.edu.uy.tads.hashtable.MyHashTableImpl;

import java.util.Scanner;


public class Menu {

    public static void main(String args){

        Scanner scanner = new Scanner(System.in);
        UMovie uMovie = new UMovie();

        while(true){

            System.out.println("Menú principal");
            System.out.println("\nSeleccione la opción que desee:");
            System.out.println("1. Carga de datos");
            System.out.println("2. Ejecutar consultas");
            System.out.println("3. Salir");

            String opcion = scanner.nextLine();

            if(opcion.equals("1")){
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

                long fin = System.currentTimeMillis();
                System.out.println("Carga completa en " + (fin - inicio) + " ms");
            }

            if(opcion.equals("2")){


            }

            if(opcion.equals("3")){
                return;
            }


        }

    }

    public static void menuConsultas(Scanner scanner, UMovie uMovie){
        boolean seguir = true;

        System.out.println("Menú principal");
        System.out.println("1. Top 5 de las películas que más calificaciones por idioma");
        System.out.println("2. Top 10 de las películas que mejor calificación media tienen por parte de los usuarios");
        System.out.println("3. Top 5 de las colecciones que más ingresos generaron");
        System.out.println("4. Top 10 de los directores que mejor calificación tienen");
        System.out.println("5. Actor con más calificaciones recibidas en cada mes del año");
        System.out.println("6. Usuarios con más calificaciones por género");
        System.out.println("7. Salir");

        String opcion = scanner.nextLine();
        while(seguir){
            if(opcion.equals("1")){
                long inicio = System.currentTimeMillis();
                uMovie.top5PeliculasPorIdioma(uMovie.getPeliculas(), uMovie.getEvaluaciones());
     