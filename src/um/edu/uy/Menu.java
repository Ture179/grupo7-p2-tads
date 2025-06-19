package um.edu.uy;

import um.edu.uy.cargadordatos.CargadorDatos;
import um.edu.uy.entities.*;
import um.edu.uy.tads.hashtable.MyHashTable;
import um.edu.uy.tads.hashtable.MyHashTableImpl;

import java.util.Scanner;


public class Menu {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        UMovie uMovie = new UMovie();

        while (true) {

            System.out.println("Menú principal");
            System.out.println("\nSeleccione la opción que desee:");
            System.out.println("1. Carga de datos");
            System.out.println("2. Ejecutar consultas");
            System.out.println("3. Salir");

            String opcion = scanner.nextLine();

            if (opcion.equals("1")) {
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

                uMovie.setPeliculas(peliculas);
                uMovie.setGeneros(generos);
                uMovie.setColecciones(colecciones);
                uMovie.setActores(actores);
                uMovie.setDirectores(directores);
                uMovie.setEvaluaciones(evaluaciones);
                uMovie.setUsuarios(usuarios);

                long fin = System.currentTimeMillis();
                System.out.println("Carga de datos exitosa, tiempo de ejecución de la carga: " + (fin - inicio) + " ms");
            }

            if (opcion.equals("2")) {
                menuConsultas(scanner, uMovie);

            }

            if (opcion.equals("3")) {
                return;
            }


        }

    }

    public static void menuConsultas(Scanner scanner, UMovie uMovie) {
        boolean seguir = true;


        while (seguir) {

            System.out.println("\n\nMenú principal");
            System.out.println("1. Top 5 de las películas que más calificaciones por idioma");
            System.out.println("2. Top 10 de las películas que mejor calificación media tienen por parte de los usuarios");
            System.out.println("3. Top 5 de las colecciones que más ingresos generaron");
            System.out.println("4. Top 10 de los directores que mejor calificación tienen");
            System.out.println("5. Actor con más calificaciones recibidas en cada mes del año");
            System.out.println("6. Usuarios con más calificaciones por género");
            System.out.println("7. Salir");


            String opcion = scanner.nextLine();
            if (opcion.equals("1")) {
                long inicio1 = System.currentTimeMillis();
                uMovie.top5PeliculasPorIdioma(uMovie.getPeliculas(), uMovie.getEvaluaciones());
                long fin1 = System.currentTimeMillis();
                System.out.println("Tiempo de ejecución de la consulta: " + (fin1 - inicio1) + " ms");
            }
            else if(opcion.equals("2")){
                long inicio2 = System.currentTimeMillis();
                uMovie.top10MejorCalificacionMedia(uMovie.getPeliculas(), uMovie.getEvaluaciones());
                long fin2 = System.currentTimeMillis();
                System.out.println("Tiempo de ejecución de la consulta: " + (fin2 - inicio2) + "ms");
            }

            else if(opcion.equals("3")){
                long inicio3 = System.currentTimeMillis();
                uMovie.top5ColeccionesMasIngresos(uMovie.getPeliculas(), uMovie.getColecciones());
                long fin3 = System.currentTimeMillis();
                System.out.println("Tiempo de ejecución de la consulta: " + (fin3 - inicio3) + "ms");
            }

            else if(opcion.equals("4")){
                long inicio4 = System.currentTimeMillis();
                uMovie.top10DirectorMejorCalificacion(uMovie.getPeliculas(), uMovie.getEvaluaciones(), uMovie.getDirectores());
                long fin4= System.currentTimeMillis();
                System.out.println("Tiempo de ejecución de la consulta: " + (fin4 - inicio4) + "ms");
            }

            else if(opcion.equals("5")){
                long inicio5 = System.currentTimeMillis();
                uMovie.actorMasVistoPorMes(uMovie.getEvaluaciones(), uMovie.getPeliculas(), uMovie.getActores());
                long fin5 = System.currentTimeMillis();
                System.out.println("Tiempo de ejecución de la consulta: " + (fin5 - inicio5) + "ms");
            }

            else if(opcion.equals("6")){
                long inicio6 = System.currentTimeMillis();
                uMovie.topUsuariosPorGenero(uMovie.getEvaluaciones(), uMovie.getPeliculas(), uMovie.getGeneros());
                long fin6 = System.currentTimeMillis();
                System.out.println("Tiempo de ejecución de la consulta: " + (fin6 - inicio6) + "ms");
            }
            else if(opcion.equals("7")){
                return;
            }
        }
    }
}