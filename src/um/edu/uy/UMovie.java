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




    public static void top5PeliculasPorIdioma(
            MyHashTable<Integer, Pelicula> peliculas,
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
                System.out.printf(" ▶️ ID: %d | Título: %s | Evaluaciones: %d | Idioma: %s\n",
                        p.getId(), p.getTitulo(), cantidad, p.getIdomaOriginal());
            }
            System.out.println();
        }
    }


    public static void top10MejorCalificacionMedia(){




    }



}
