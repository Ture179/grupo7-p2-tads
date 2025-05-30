package um.edu.uy.entities;

import um.edu.uy.tads.linkedlist.MyLinkedListImpl;
import um.edu.uy.tads.linkedlist.MyList;

import java.util.Objects;

public class Pelicula {
    private int id;
    private String titulo;
    private String idomaOriginal;
    private long ingresos;
    private int idDirector;
    private int idColeccion;
    private MyList<Integer> actores;
    private MyList<Integer> generos;

    public Pelicula(int id, String titulo, String idomaOriginal, long ingresos, int idDirector, int idColeccion, MyList<Integer> actores, MyList<Integer> generos) {
        this.id = id;
        this.titulo = titulo;
        this.idomaOriginal = idomaOriginal;
        this.ingresos = ingresos;
        this.idDirector = idDirector;
        this.idColeccion = idColeccion;
        this.actores = new MyLinkedListImpl<>();
        this.generos = new MyLinkedListImpl<>();
    }

    public Pelicula() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdomaOriginal() {
        return idomaOriginal;
    }

    public void setIdomaOriginal(String idomaOriginal) {
        this.idomaOriginal = idomaOriginal;
    }

    public long getIngresos() {
        return ingresos;
    }

    public void setIngresos(long ingresos) {
        this.ingresos = ingresos;
    }

    public int getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(int idDirector) {
        this.idDirector = idDirector;
    }

    public int getIdColeccion() {
        return idColeccion;
    }

    public void setIdColeccion(int idColeccion) {
        this.idColeccion = idColeccion;
    }

    public MyList<Integer> getActores() {
        return actores;
    }

    public void setActores(MyList<Integer> actores) {
        this.actores = actores;
    }

    public MyList<Integer> getGeneros() {
        return generos;
    }

    public void setGeneros(MyList<Integer> generos) {
        this.generos = generos;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pelicula pelicula = (Pelicula) o;
        return id == pelicula.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public void agregarActores(Integer actor) {
        if (actor != null && !actores.contains(actor)) {
            actores.add(actor);
        }
    }

    public void agregarGeneros(Integer genero) {
        if (genero != null && !generos.contains(genero)) {
            generos.add(genero);
        }
    }
}
