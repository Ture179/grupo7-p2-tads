package um.edu.uy;

import um.edu.uy.entities.*;
import um.edu.uy.tads.arraylist.MyArrayList;
import um.edu.uy.tads.arraylist.MyArrayListImpl;
import um.edu.uy.tads.hashtable.MyHashTable;
import um.edu.uy.tads.hashtable.MyHashTableImpl;
import um.edu.uy.tads.linkedlist.MyLinkedListImpl;
import um.edu.uy.tads.linkedlist.MyList;

import java.util.Date;



public class UMovie {
    private MyHashTable<Integer, Pelicula> peliculas;
    private MyHashTable<Integer, Genero> generos;
    private MyHashTable<Integer, Coleccion> colecciones;
    private MyHashTable<Integer, Actor> actores;
    private MyHashTable<Integer, Director> directores;
    private MyHashTable<Integer, Usuario> usuarios;
    private MyHashTable<Integer, Evaluacion> evaluaciones;

    public static void top5PeliculasPorIdioma(MyHashTable<Integer, Pelicula> peliculas,
                                              MyHashTable<Integer, Evaluacion> evaluaciones) {

        String[] idiomas = {"en", "fr", "it", "es", "pt"};
        MyHashTable<Integer, Integer> conteo = new MyHashTableImpl<>();

        // Contar evaluaciones por ID de película
        for (Evaluacion e : evaluaciones.obtenerElementos()) {
            int idPelicula = e.getId_pelicula();
            Integer actual = conteo.obtener(idPelicula);
            if (actual == null) {
                try {
                    conteo.insertar(idPelicula, 1);
                } catch (Exception ignored) {}
            } else {
                try {
                    conteo.borrar(idPelicula);
                    conteo.insertar(idPelicula, actual + 1);
                } catch (Exception ignored) {}
            }
        }

        // Para cada idioma: obtener y mostrar top 5
        for (String idioma : idiomas) {
            MyArrayList<Pelicula> lista = new MyArrayListImpl<>();

            for (Pelicula p : peliculas.obtenerElementos()) {
                if (idioma.equals(p.getIdomaOriginal())) {
                    lista.agregar(p);
                }
            }

            // Ordenar por cantidad de evaluaciones (desc)
            lista.sort((a, b) -> {
                int evalA = conteo.obtener(a.getId()) != null ? conteo.obtener(a.getId()) : 0;
                int evalB = conteo.obtener(b.getId()) != null ? conteo.obtener(b.getId()) : 0;
                return Integer.compare(evalB, evalA);
            });

            System.out.println("Top 5 películas en idioma: " + idioma.toUpperCase());
            for (int i = 0; i < Math.min(5, lista.size()); i++) {
                Pelicula p = lista.get(i);
                int cantidad = conteo.obtener(p.getId()) != null ? conteo.obtener(p.getId()) : 0;
                System.out.printf("%d,%s,%d,%s\n", p.getId(), p.getTitulo(), cantidad, p.getIdomaOriginal());
            }
            System.out.println();
        }
    }


    public static void top10MejorCalificacionMedia(MyHashTable<Integer, Pelicula> peliculas,
                                                   MyHashTable<Integer, Evaluacion> evaluaciones) {

        MyHashTable<Integer, Double> sumas = new MyHashTableImpl<>();
        MyHashTable<Integer, Integer> conteos = new MyHashTableImpl<>();

        // Acumular sumas y conteos
        for (Evaluacion e : evaluaciones.obtenerElementos()) {
            int idPelicula = e.getId_pelicula();
            double puntaje = e.getPuntaje();

            Double sumaActual = sumas.obtener(idPelicula);
            Integer cantidadActual = conteos.obtener(idPelicula);

            try {
                if (sumaActual == null) {
                    sumas.insertar(idPelicula, puntaje);
                    conteos.insertar(idPelicula, 1);
                } else {
                    sumas.borrar(idPelicula);
                    conteos.borrar(idPelicula);
                    sumas.insertar(idPelicula, sumaActual + puntaje);
                    conteos.insertar(idPelicula, cantidadActual + 1);
                }
            } catch (Exception ignored) {}
        }

        // Crear lista de películas que tienen evaluaciones
        MyArrayList<Pelicula> lista = new MyArrayListImpl<>();
        for (Pelicula p : peliculas.obtenerElementos()) {
            int id = p.getId();
            if (sumas.contieneClave(id) && conteos.contieneClave(id)) {
                lista.agregar(p);
            }
        }

        // Ordenar por promedio descendente
        lista.sort((a, b) -> {
            double promA = sumas.obtener(a.getId()) / conteos.obtener(a.getId());
            double promB = sumas.obtener(b.getId()) / conteos.obtener(b.getId());
            return Double.compare(promB, promA);
        });

        // Imprimir top 10
        System.out.println("Top 10 películas con mejor calificación media:");
        for (int i = 0; i < Math.min(10, lista.size()); i++) {
            Pelicula p = lista.get(i);
            double promedio = sumas.obtener(p.getId()) / conteos.obtener(p.getId());
            System.out.printf("%d,%s,%.2f\n", p.getId(), p.getTitulo(), promedio);
        }
    }


    public static void top5ColeccionesMasIngresos(MyHashTable<Integer, Pelicula> peliculas,
                                                  MyHashTable<Integer, Coleccion> colecciones) {

        MyArrayList<Coleccion> todas = new MyArrayListImpl<>();

        // Clonar colecciones con ingresos calculados
        for (Coleccion c : colecciones.obtenerElementos()) {
            long ingresosTotales = 0;
            MyList<Integer> ids = c.getPeliculas();
            for (int i = 0; i < ids.size(); i++) {
                Pelicula p = peliculas.obtener(ids.get(i));
                if (p != null) {
                    ingresosTotales += p.getIngresos();
                }
            }

            Coleccion nueva = new Coleccion();
            nueva.setId(c.getId());
            nueva.setNombre(c.getNombre());
            nueva.setPeliculas(ids);
            nueva.setIngresos(ingresosTotales);

            todas.agregar(nueva);
        }

        // Películas sin colección (colecciones individuales)
        for (Pelicula p : peliculas.obtenerElementos()) {
            if (p.getIdColeccion() == 0 || !colecciones.contieneClave(p.getIdColeccion())) {
                Coleccion fake = new Coleccion();
                fake.setId(p.getId()); // Se trata como colección propia
                fake.setNombre(p.getTitulo());
                MyList<Integer> unica = new MyLinkedListImpl<>();
                unica.add(p.getId());
                fake.setPeliculas(unica);
                fake.setIngresos(p.getIngresos());
                todas.agregar(fake);
            }
        }

        // Ordenar por ingresos
        todas.sort((a, b) -> Long.compare(b.getIngresos(), a.getIngresos()));

        // Imprimir
        System.out.println("Top 5 colecciones con más ingresos:");
        for (int i = 0; i < Math.min(5, todas.size()); i++) {
            Coleccion c = todas.get(i);
            System.out.printf("%d,%s,%d,[", c.getId(), c.getNombre(), c.getPeliculas().size());
            for (int j = 0; j < c.getPeliculas().size(); j++) {
                System.out.print(c.getPeliculas().get(j));
                if (j < c.getPeliculas().size() - 1) System.out.print(",");
            }
            System.out.printf("],%d\n", c.getIngresos());
        }
    }

    public static void top10DirectorMejorCalificacion(MyHashTable<Integer, Pelicula> peliculas, MyHashTable<Integer, Evaluacion> evaluaciones, MyHashTable<Integer, Director> directores){

    }


    public static void actorMasVistoPorMes(MyHashTable<Integer, Evaluacion> evaluaciones,
                                           MyHashTable<Integer, Pelicula> peliculas,
                                           MyHashTable<Integer, Actor> actores) {

        MyHashTable<String, Integer> conteoCalificaciones = new MyHashTableImpl<>();
        MyHashTable<String, MyArrayList<Integer>> peliculasVistas = new MyHashTableImpl<>();

        for (Evaluacion e : evaluaciones.obtenerElementos()) {
            Date fecha = new Date(e.getFecha() * 1000L);
            int mes = fecha.getMonth() + 1;

            int idPelicula = e.getId_pelicula();
            Pelicula p = peliculas.obtener(idPelicula);
            if (p == null) continue;

            MyList<Integer> actoresPelicula = p.getActores();

            for (int i = 0; i < actoresPelicula.size(); i++) {
                Integer idActor = actoresPelicula.get(i);
                String clave = mes + "-" + idActor;

                // Calificaciones
                Integer actual = conteoCalificaciones.obtener(clave);
                if (actual == null) {
                    try {
                        conteoCalificaciones.insertar(clave, 1);
                    } catch (Exception ignored) {}
                } else {
                    try {
                        conteoCalificaciones.borrar(clave);
                        conteoCalificaciones.insertar(clave, actual + 1);
                    } catch (Exception ignored) {}
                }

                // Películas únicas
                MyArrayList<Integer> lista = peliculasVistas.obtener(clave);
                if (lista == null) {
                    lista = new MyArrayListImpl<>();
                    try {
                        peliculasVistas.insertar(clave, lista);
                    } catch (Exception ignored) {}
                }

                if (!lista.contains(idPelicula)) {
                    lista.agregar(idPelicula);
                }
            }
        }

        // Mostrar el actor más calificado por mes
        for (int mes = 1; mes <= 12; mes++) {
            int maxCalificaciones = -1;
            String mejorClave = null;

            for (String clave : conteoCalificaciones.obtenerClaves()) {
                if (!clave.startsWith(mes + "-")) continue;

                Integer cantidad = conteoCalificaciones.obtener(clave);
                if (cantidad != null && cantidad > maxCalificaciones) {
                    maxCalificaciones = cantidad;
                    mejorClave = clave;
                }
            }

            if (mejorClave != null) {
                String[] partes = mejorClave.split("-");
                int idActor = Integer.parseInt(partes[1]);
                Actor actor = actores.obtener(idActor);
                MyArrayList<Integer> pelis = peliculasVistas.obtener(mejorClave);
                int cantidadPelis = pelis != null ? pelis.size() : 0;

                System.out.printf("%d,%s,%d,%d\n", mes, actor.getNombre(), cantidadPelis, maxCalificaciones);
            }
        }
    }


    public static void topUsuariosPorGenero(MyHashTable<Integer, Evaluacion> evaluaciones,
                                            MyHashTable<Integer, Pelicula> peliculas,
                                            MyHashTable<Integer, Genero> generos) {

        MyHashTable<Integer, Integer> totalGenero = new MyHashTableImpl<>();
        MyHashTable<String, Integer> usuarioGenero = new MyHashTableImpl<>();

        // Paso 1: recorrer todas las evaluaciones
        for (Evaluacion e : evaluaciones.obtenerElementos()) {
            int idUsuario = e.getId_usuario();
            int idPelicula = e.getId_pelicula();
            Pelicula peli = peliculas.obtener(idPelicula);
            if (peli == null) continue;

            MyList<Integer> generosPelicula = peli.getGeneros();
            for (int i = 0; i < generosPelicula.size(); i++) {
                int idGenero = generosPelicula.get(i);

                // contar popularidad del género
                Integer total = totalGenero.obtener(idGenero);
                if (total == null) {
                    try { totalGenero.insertar(idGenero, 1); } catch (Exception ignored) {}
                } else {
                    try {
                        totalGenero.borrar(idGenero);
                        totalGenero.insertar(idGenero, total + 1);
                    } catch (Exception ignored) {}
                }

                // contar evaluaciones por usuario y género
                String clave = idUsuario + "-" + idGenero;
                Integer cuenta = usuarioGenero.obtener(clave);
                if (cuenta == null) {
                    try { usuarioGenero.insertar(clave, 1); } catch (Exception ignored) {}
                } else {
                    try {
                        usuarioGenero.borrar(clave);
                        usuarioGenero.insertar(clave, cuenta + 1);
                    } catch (Exception ignored) {}
                }
            }
        }

        // Paso 2: Obtener los top 10 géneros más evaluados
        MyArrayList<Integer> listaGeneros = new MyArrayListImpl<>();
        for (Integer idGenero : totalGenero.obtenerClaves()) {
            listaGeneros.agregar(idGenero);
        }

        listaGeneros.sort((a, b) -> totalGenero.obtener(b) - totalGenero.obtener(a));
        int limite = Math.min(10, listaGeneros.size());

        // Paso 3: Para cada género top, buscar el usuario que más evaluó
        System.out.println("Usuarios más activos por género (top 10 géneros):");
        for (int i = 0; i < limite; i++) {
            int idGenero = listaGeneros.get(i);
            Genero g = generos.obtener(idGenero);
            if (g == null) continue;

            int max = -1;
            int mejorUsuario = -1;

            for (String clave : usuarioGenero.obtenerClaves()) {
                if (clave.endsWith("-" + idGenero)) {
                    String[] partes = clave.split("-");
                    int idUsuario = Integer.parseInt(partes[0]);
                    int cuenta = usuarioGenero.obtener(clave);
                    if (cuenta > max) {
                        max = cuenta;
                        mejorUsuario = idUsuario;
                    }
                }
            }

            System.out.printf("%d,%s,%d\n", mejorUsuario, g.getNombre(), max);
        }
    }



    public MyHashTable<Integer, Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(MyHashTable<Integer, Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public MyHashTable<Integer, Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(MyHashTable<Integer, Genero> generos) {
        this.generos = generos;
    }

    public MyHashTable<Integer, Coleccion> getColecciones() {
        return colecciones;
    }

    public void setColecciones(MyHashTable<Integer, Coleccion> colecciones) {
        this.colecciones = colecciones;
    }

    public MyHashTable<Integer, Actor> getActores() {
        return actores;
    }

    public void setActores(MyHashTable<Integer, Actor> actores) {
        this.actores = actores;
    }

    public MyHashTable<Integer, Director> getDirectores() {
        return directores;
    }

    public void setDirectores(MyHashTable<Integer, Director> directores) {
        this.directores = directores;
    }

    public MyHashTable<Integer, Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(MyHashTable<Integer, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public MyHashTable<Integer, Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(MyHashTable<Integer, Evaluacion> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }
}
