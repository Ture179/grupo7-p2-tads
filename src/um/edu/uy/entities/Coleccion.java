package um.edu.uy.entities;

import um.edu.uy.tads.linkedlist.MyLinkedListImpl;
import um.edu.uy.tads.linkedlist.MyList;

import java.util.Objects;

public class Coleccion {
    private int id;
    private String nombre;
    private MyList<Integer> peliculas;

    public Coleccion(int id, String nombre, MyList<Integer> peliculas) {
        this.id = id;
        this.nombre = nombre;
        this.peliculas = peliculas;
    }

    public Coleccion() {
        this.peliculas = new MyLinkedListImpl<>();
    }

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
        Coleccion coleccion = (Coleccion) o;
        return getId() == coleccion.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
