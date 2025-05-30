package um.edu.uy.entities;

import java.util.Objects;

public class Evaluacion {
    private int id_usuario;
    private int id_pelicula;
    private double puntaje;
    private long fecha;

    public Evaluacion(int id_usuario, int id_pelicula, double puntaje, long fecha) {
        this.id_usuario = id_usuario;
        this.id_pelicula = id_pelicula;
        this.puntaje = puntaje;
        this.fecha = fecha;
    }

    public Evaluacion() {}

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_pelicula() {
        return id_pelicula;
    }

    public void setId_pelicula(int id_pelicula) {
        this.id_pelicula = id_pelicula;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(double puntaje) {
        this.puntaje = puntaje;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Evaluacion that = (Evaluacion) o;
        return id_usuario == that.id_usuario && id_pelicula == that.id_pelicula;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_usuario, id_pelicula);
    }
}
