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
            reader.readNext();

            while ((columnas = reader.readNext()) != null) {
                if (columnas.length < 19) continue;

                try {
                    String idStr = columnas[5].trim();
                    int id = Integer.parseInt(idStr);

                    String titulo = columnas[18].trim();
                    String idioma = columnas[7].trim();
                    long ingresos = 0;
                    if(!columnas[13].isEmpty()) {
                        ingresos = Long.parseLong(columnas[13].trim());
                    }

                    Pelicula p = new Pelicula();
                    p.setId(id);
                    p.setTitulo(titulo);
                    p.setIdomaOriginal(idioma);
                    p.setIngresos(ingresos);


                    // Géneros
                    try {
                        String generoJson = columnas[3].trim();
                        if (!generoJson.isEmpty() && generoJson.startsWith("[")) {
                            generoJson = sanitizeJson(generoJson);

                            JSONArray array = new JSONArray(generoJson);
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
                    } catch (Exception ignored) {}


                    // Colección
                    try {
                        String coleccionJson = columnas[1].trim();
                        if (!coleccionJson.isEmpty() && coleccionJson.startsWith("{")) {
                            coleccionJson = sanitizeJson(coleccionJson);

                            JSONObject obj = new JSONObject(coleccionJson);
                            int idColeccion = obj.getInt("id");
                            String nombre = obj.getString("name");

                            Coleccion c = colecciones.obtener(idColeccion);
                            if (c == null) {
                                c = new Coleccion(idColeccion, nombre, new MyLinkedListImpl<>());
                                colecciones.insertar(idColeccion, c);
                            }
                            c.getPeliculas().add(id);
                            p.setIdColeccion(idColeccion);

                        }
                    } catch (Exception ignored) {}

                    peliculas.insertar(id, p);

                } catch (Exception ignored) {
                }
            }

        } catch (Exception ignored) {}
    }

    public void cargarCreditosDesdeCSV(String path,
                                       MyHashTable<Integer, Pelicula> peliculas,
                                       MyHashTable<Integer, Actor> actores,
                                       MyHashTable<Integer, Director> directores) {

        try {
            CSVReader reader = new CSVReader(new FileReader(path));
            String[] columnas;
            reader.readNext();

            while ((columnas = reader.readNext()) != null) {
                if (columnas.length < 3) continue;

                String castJsonCrudo = columnas[0].trim();
                String crewJsonCrudo = columnas[1].trim();

                try {
                    int idPelicula = Integer.parseInt(columnas[2].trim());
                    Pelicula peli = peliculas.obtener(idPelicula);
                    if (peli == null) continue;


                    // Cast
                    if (!castJsonCrudo.isEmpty() && castJsonCrudo.startsWith("[")) {
                        try {
                            String castJson = sanitizeJson(castJsonCrudo);
                            JSONArray castArray = new JSONArray(castJson);

                            for (int i = 0; i < castArray.length(); i++) {
                                JSONObject obj = castArray.getJSONObject(i);
                                if (!obj.has("id")) continue;

                                int idActor = obj.getInt("id");
                                String nombre = obj.optString("name", null);
                                if (nombre == null) continue;

                                Actor actor = actores.obtener(idActor);
                                if (actor == null) {
                                    actor = new Actor();
                                    actor.setId(idActor);
                                    actor.setNombre(nombre);
                                    actores.insertar(idActor, actor);
                                }

                                actor.agregarPelicula(idPelicula);
                                peli.agregarActores(idActor);
                            }
                        } catch (Exception ignored) {}
                    }

                    // Crew
                    if (!crewJsonCrudo.isEmpty() && crewJsonCrudo.startsWith("[")) {
                        try {
                            String crewJson = sanitizeJson(crewJsonCrudo);
                            JSONArray crewArray = new JSONArray(crewJson);

                            for (int i = 0; i < crewArray.length(); i++) {
                                JSONObject obj = crewArray.getJSONObject(i);
                                String job = obj.optString("job", "");
                                if (!job.equalsIgnoreCase("Director")) continue;

                                int idDirector = obj.getInt("id");
                                String nombre = obj.optString("name", null);
                                if (nombre == null) continue;

                                Director director = directores.obtener(idDirector);
                                if (director == null) {
                                    director = new Director();
                                    director.setId(idDirector);
                                    director.setNombre(nombre);
                                    directores.insertar(idDirector, director);
                                }

                                peli.setIdDirector(idDirector);
                                break;
                            }
                        } catch (Exception ignored) {}
                    }

                } catch (Exception ignored) {}
            }

        } catch (Exception ignored) {}

    }


    public void cargarEvaluacionesDesdeCSV(String path,
                                           MyHashTable<Integer, Evaluacion> evaluaciones,
                                           MyHashTable<Integer, Usuario> usuarios) {
        try {
            CSVReader reader = new CSVReader(new FileReader(path));
            String[] columnas;
            reader.readNext();

            int contador = 0;

            while ((columnas = reader.readNext()) != null) {
                if (columnas.length < 4) continue;
                if (columnas[0].trim().isEmpty() || columnas[1].trim().isEmpty()
                        || columnas[2].trim().isEmpty() || columnas[3].trim().isEmpty()) continue;

                try {
                    int id_usuario = Integer.parseInt(columnas[0].trim());
                    int id_pelicula = Integer.parseInt(columnas[1].trim());
                    double puntaje = Double.parseDouble(columnas[2].trim());
                    long fecha = Long.parseLong(columnas[3].trim());

                    Evaluacion e = new Evaluacion();
                    e.setId_usuario(id_usuario);
                    e.setId_pelicula(id_pelicula);
                    e.setPuntaje(puntaje);
                    e.setFecha(fecha);

                    evaluaciones.insertar(contador++, e);

                    if (!usuarios.contieneClave(id_usuario)) {
                        Usuario u = new Usuario();
                        u.setId(id_usuario);
                        usuarios.insertar(id_usuario, u);
                    }

                } catch (Exception ignored) {}
            }

        } catch (Exception ignored) {}
    }

    public void imprimirResumen(MyHashTable<Integer, Pelicula> peliculas,
                                MyHashTable<Integer, Genero> generos,
                                MyHashTable<Integer, Coleccion> colecciones,
                                MyHashTable<Integer, Actor> actores,
                                MyHashTable<Integer, Director> directores,
                                MyHashTable<Integer, Evaluacion> evaluaciones,
                                MyHashTable<Integer, Usuario> usuarios) {

        System.out.println("Películas cargadas: " + peliculas.size());
        System.out.println("Géneros diferentes: " + generos.size());
        System.out.println("Colecciones diferentes: " + colecciones.size());
        System.out.println("Actores diferentes: " + actores.size());
        System.out.println("Directores diferentes: " + directores.size());
        System.out.println("Evaluaciones cargadas: " + evaluaciones.size());
        System.out.println("Usuarios únicos: " + usuarios.size());
    }

    private String sanitizeJson(String raw) {
        return raw
                .replaceAll("(?<=\\{|,|\\[)\\s*'([^']+?)'\\s*:", "\"$1\":")
                .replaceAll(":\\s*'([^']*?)'(?=,|}|\\])", ":\"$1\"")
                .replaceAll(": None", ": null")
                .replaceAll(": none", ": null")
                .replaceAll(": undefined", ": null")
                .replaceAll(": True", ": true")
                .replaceAll(": true", ": true")
                .replaceAll(": False", ": false")
                .replaceAll(": false", ": false")
                .replaceAll(",\\s*]", "]")
                .replaceAll(",\\s*}", "}")
                .replaceAll("\\\\u0000", "")
                .replaceAll("“", "\"")
                .replaceAll("”", "\"")
                .replaceAll("‘", "\"")
                .replaceAll("’", "\"");
    }
}
