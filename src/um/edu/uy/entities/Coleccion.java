package um.edu.uy.entities;

import java.util.Objects;

public class Coleccion {
    private int id;
    private String nombre;

    public Coleccion(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Coleccion() {}

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
        Coleccion coleccion = (Coleccion) o;
        return getId() == coleccion.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
