package um.edu.uy.cargadordatos;

import org.json.JSONArray;
import org.json.JSONObject;
import um.edu.uy.entities.Coleccion;
import um.edu.uy.entities.Genero;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.exceptions.ElementoYaExistente;
import um.edu.uy.tads.hashtable.MyHashTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CargadorDatos {

    public void cargarPeliculasDesdeCSV(String path, MyHashTable<Integer, Pelicula> peliculas, MyHashTable<Integer, Genero> generos, MyHashTable<Integer, Coleccion> colecciones) {
        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linea = br.readLine();

            while ((linea = br.readLine()) != null) {
                String[] columnas = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

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

                    // --- Generos ---
                    String generoJson = columnas[3].trim();
                    if (!generoJson.isEmpty() && generoJson.startsWith("[")) {
                        JSONArray array = new JSONArray(generoJson.replace("'", "\""));
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            int idGenero = obj.getInt("id");
                            String nombre = obj.getString("name");

                            Genero g = generos.obtener(idGenero);
                            if (g == null) {
                                g = new Genero();
                                g.setId(idGenero);
                                g.setNombre(nombre);
                                generos.insertar(idGenero, g);
                            }
                            g.agregarPelicula(id);
                            p.agregarGeneros(idGenero);
                        }
                    }

                    // --- Coleccion ---
                    String coleccionJson = columnas[1].trim();
                    if (!coleccionJson.isEmpty() && coleccionJson.startsWith("{")) {
                        JSONObject obj = new JSONObject(coleccionJson.replace("'", "\""));
                        int idColeccion = obj.getInt("id");
                        String nombre = obj.getString("name");

                        Coleccion c = colecciones.obtener(idColeccion);
                        if (c == null) {
                            c = new Coleccion();
                            c.setId(idColeccion);
                            c.setNombre(nombre);
                            colecciones.insertar(idColeccion, c);
                        }

                        p.setIdColeccion(idColeccion);
                    }


                    peliculas.insertar(id, p);
                    contador++;

                } catch (NumberFormatException | ElementoYaExistente e) {
                    continue;
                }


            }

            System.out.println("Películas cargadas: " + contador);

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
