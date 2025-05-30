package um.edu.uy.entities;

import um.edu.uy.tads.linkedlist.*;
import java.util.Objects;

public class Actor {
    private int id;
    private String nombre;
    private MyList<Integer> peliculas;

    public Actor(int id, String nombre, MyList<Integer> peliculas) {
        this.id = id;
        this.nombre = nombre;
        this.peliculas = new MyLinkedListImpl<>();
    }

    public Actor() {}

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

    public MyList<Integer> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(MyList<Integer> peliculas) {
        this.peliculas = peliculas;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return getId() == actor.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    public void agregarPelicula(Integer pelicula) {
        if (pelicula != null && !peliculas.contains(pelicula)) {
            peliculas.add(pelicula);
        }
    }

}
