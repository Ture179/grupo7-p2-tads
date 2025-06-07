package um.edu.uy.cargadordatos;

import com.opencsv.CSVReader;

import org.json.JSONArray;
import org.json.JSONObject;
import um.edu.uy.entities.*;
import um.edu.uy.tads.hashtable.MyHashTable;
import um.edu.uy.tads.linkedlist.MyLinkedListImpl;
import java.io.FileReader;

public class CargadorDatos {

    public void cargarPeliculasDesdeCSV(String path,
                                        MyHashTable<Integer, Pelicula> peliculas,
                                        MyHashTable<Integer, Genero> generos,
                                        MyHashTable<Integer, Coleccion> colecciones) {

        try {


            CSVReader reader = new CSVReader(new FileReader(path));

            String[] columnas;
            reader.readNext(); // Encabezado

            while ((columnas = reader.readNext()) != null) {
                if (columnas.length < 19) continue;

                try {
                    int id = Integer.parseInt(columnas[5].trim());
                    String titulo = columnas[18].trim();
                    String idioma = columnas[7].trim();
                    long ingresos = Long.parseLong(columnas[13].trim());

                    Pelicula p = new Pelicula();
                    p.setId(id);
                    p.setTitulo(titulo);
                    p.setIdomaOriginal(idioma);
                    p.setIngresos(ingresos);

                    // --- Géneros ---
                    String generoJson = columnas[3].trim();
                    if (!generoJson.isEmpty() && generoJson.startsWith("[")) {
                        JSONArray array = new JSONArray(generoJson.replace("'", "\""));
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            int idGenero = obj.getInt("id");
                            String nombre = obj.getString("name");

                            Genero g = generos.obtener(idGenero);
                            if (g == null) {
                                g = new Genero(idGenero, nombre, new MyLinkedListImpl<>());
                                generos.insertar(idGenero, g);
                            }
                            g.agregarPelicula(id);
                            p.agregarGeneros(idGenero);
                        }
                    }

                    // --- Colección ---
                    String coleccionJson = columnas[1].trim();
                    if (!coleccionJson.isEmpty() && coleccionJson.startsWith("{")) {
                        JSONObject obj = new JSONObject(coleccionJson.replace("'", "\""));
                        int idColeccion = obj.getInt("id");
                        String nombre = obj.getString("name");

                        Coleccion c = colecciones.obtener(idColeccion);
                        if (c == null) {
                            c = new Coleccion(idColeccion, nombre);
                            colecciones.insertar(idColeccion, c);
                        }
                        p.setIdColeccion(idColeccion);
                    }

                    peliculas.insertar(id, p);

                } catch (Exception e) {
                }
            }

            System.out.println("Películas cargadas: " + peliculas.size());

        } catch (Exception e) {
            System.out.println("Error al leer el archivo de películas: " + e.getMessage());
        }
    }

    public void cargarCreditosDesdeCSV(String path,
                                       MyHashTable<Integer, Pelicula> peliculas,
                                       MyHashTable<Integer, Actor> actores) {
        int totalCreditos = 0;

        try {


            CSVReader reader = new CSVReader(new FileReader(path));

            String[] columnas;
            reader.readNext(); // Encabezado

            while ((columnas = reader.readNext()) != null) {
                if (columnas.length < 3) continue;

                try {
                    int idPelicula = Integer.parseInt(columnas[2].trim());
                    Pelicula peli = peliculas.obtener(idPelicula);
                    if (peli == null) continue;

                    // --- Actores ---
                    String castJson = columnas[0].trim();
                    if (!castJson.isEmpty() && castJson.startsWith("[")) {
                        JSONArray castArray = new JSONArray(castJson.replace("'", "\""));
                        for (int i = 0; i < castArray.length(); i++) {
                            JSONObject obj = castArray.getJSONObject(i);
                            int idActor = obj.getInt("id");
                            String nombre = obj.getString("name");

                            Actor actor = actores.obtener(idActor);
                            if (actor == null) {
                                actor = new Actor();
                                actor.setId(idActor);
                                actor.setNombre(nombre);
                                actores.insertar(idActor, actor);
                            }

                            actor.agregarPelicula(idPelicula);
                            peli.agregarActores(idActor);
                            totalCreditos++;
                        }
                    }

                    // --- Director ---
                    String crewJson = columnas[1].trim();
                    if (!crewJson.isEmpty() && crewJson.startsWith("[")) {
                        JSONArray crewArray = new JSONArray(crewJson.replace("'", "\""));
                        for (int i = 0; i < crewArray.length(); i++) {
                            JSONObject obj = crewArray.getJSONObject(i);
                            if (obj.has("job") && "Director".equalsIgnoreCase(obj.getString("job"))) {
                                int idDirector = obj.getInt("id");
                                peli.setIdDirector(idDirector);
                                break;
                            }
                        }
                    }

                } catch (Exception e) {
                    // línea malformada, ignorar
                }
            }

            System.out.println("Créditos cargados: " + totalCreditos);

        } catch (Exception e) {
            System.out.println("Error al leer el archivo de créditos: " + e.getMessage());
        }
    }
}

