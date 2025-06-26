package um.edu.uy.entities;

import um.edu.uy.tads.linkedlist.MyLinkedListImpl;
import um.edu.uy.tads.linkedlist.MyList;

import java.util.Objects;

public class Genero {
    private int id;
    private String nombre;

    public Genero(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Genero() {
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


}
