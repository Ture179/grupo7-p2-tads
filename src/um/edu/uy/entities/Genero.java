package um.edu.uy.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Genero {
    private int id;
    private String nombre;
    private List<Integer> peliculas;

    public Genero(int id, String nombre, List<Integer> peliculas) {
        this.id = id;
        this.nombre = nombre;
        this.peliculas = new ArrayList<>();
    }

    public Genero() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Integer> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Integer> peliculas) {
        this.peliculas = peliculas;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Genero genero = (Genero) o;
        return id == genero.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public void agregarPelicula(Integer pelicula) {
        if (pelicula != null && !peliculas.contains(pelicula)) {
            peliculas.add(pelicula);
        }
    }
}
