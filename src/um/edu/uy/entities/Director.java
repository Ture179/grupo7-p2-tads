package um.edu.uy.entities;

import java.util.Objects;

public class Director {
    private int id;
    private String nombre;

    public Director(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Director() {}

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
        Director director = (Director) o;
        return getId() == director.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
