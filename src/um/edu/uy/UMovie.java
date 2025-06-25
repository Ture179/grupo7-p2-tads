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

        for (String idioma : idiomas) {
            MyArrayList<Pelicula> lista = new MyArrayListImpl<>();

            for (Pelicula p : peliculas.obtenerElementos()) {
                if (idioma.equals(p.getIdomaOriginal())) {
                    lista.agregar(p);
                }
            }

            lista.sort((a, b) -> {
                int evalA = conteo.obtener(a.getId()) != null ? conteo.obtener(a.getId()) : 0;
                int evalB = conteo.obtener(b.getId()) != null ? conteo.obtener(b.getId()) : 0;
                return Integer.compare(evalB, evalA);
            });

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

        MyArrayList<Pelicula> lista = new MyArrayListImpl<>();
        for (Pelicula p : peliculas.obtenerElementos()) {
            int id = p.getId();
            if (sumas.contieneClave(id) && conteos.contieneClave(id) && conteos.obtener(id) > 100) {
                lista.agregar(p);
            }
        }

        lista.sort((a, b) -> {
            double promA = sumas.obtener(a.getId()) / conteos.obtener(a.getId());
            double promB = sumas.obtener(b.getId()) / conteos.obtener(b.getId());
            return Double.compare(promB, promA);
        });

        for (int i = 0; i < Math.min(10, lista.size()); i++) {
            Pelicula p = lista.get(i);
            double promedio = sumas.obtener(p.getId()) / conteos.obtener(p.getId());
            System.out.printf("%d,%s,%.2f\n", p.getId(), p.getTitulo(), promedio);
        }
    }


    public static void top5ColeccionesMasIngresos(MyHashTable<Integer, Pelicula> peliculas,
                                                  MyHashTable<Integer, Coleccion> colecciones) {

        MyArrayList<Coleccion> coleccionesProcesadas = new MyArrayListImpl<>();

        for (Coleccion coleccion : colecciones.obtenerElementos()) {
            long ingresosTotales = 0;
            MyList<Integer> idsPeliculas = coleccion.getPeliculas();

            for (int i = 0; i < idsPeliculas.size(); i++) {
                Pelicula pelicula = peliculas.obtener(idsPeliculas.get(i));
                if (pelicula != null) {
                    ingresosTotales += pelicula.getIngresos();
                }
            }

            Coleccion nuevaColeccion = new Coleccion();
            nuevaColeccion.setId(coleccion.getId());
            nuevaColeccion.setNombre(coleccion.getNombre());
            nuevaColeccion.setPeliculas(idsPeliculas);
            nuevaColeccion.setIngresos(ingresosTotales);

            coleccionesProcesadas.agregar(nuevaColeccion);
        }

        for (Pelicula pelicula : peliculas.obtenerElementos()) {
            if (pelicula.getIdColeccion() == 0 || !colecciones.contieneClave(pelicula.getIdColeccion())) {
                Coleccion coleccionIndividual = new Coleccion();
                coleccionIndividual.setId(pelicula.getId());
                coleccionIndividual.setNombre(pelicula.getTitulo());

                MyList<Integer> listaUnica = new MyLinkedListImpl<>();
                listaUnica.add(pelicula.getId());
                coleccionIndividual.setPeliculas(listaUnica);
                coleccionIndividual.setIngresos(pelicula.getIngresos());

                coleccionesProcesadas.agregar(coleccionIndividual);
            }
        }

        coleccionesProcesadas.sort((a, b) -> Long.compare(b.getIngresos(), a.getIngresos()));

        for (int i = 0; i < Math.min(5, coleccionesProcesadas.size()); i++) {
            Coleccion coleccion = coleccionesProcesadas.get(i);
            System.out.printf("%d,%s,%d,[", coleccion.getId(), coleccion.getNombre(), coleccion.getPeliculas().size());
            for (int j = 0; j < coleccion.getPeliculas().size(); j++) {
                System.out.print(coleccion.getPeliculas().get(j));
                if (j < coleccion.getPeliculas().size() - 1) System.out.print(",");
            }
            System.out.printf("],%d\n", coleccion.getIngresos());
        }
    }

    public static void top10DirectorMejorCalificacion(
            MyHashTable<Integer, Pelicula> peliculas,
            MyHashTable<Integer, Evaluacion> evaluaciones,
            MyHashTable<Integer, Director> directores) {

        MyHashTable<Integer, MyArrayList<Double>> puntajesPorDirector = new MyHashTableImpl<>();
        MyHashTable<Integer, MyArrayList<Integer>> peliculasPorDirector = new MyHashTableImpl<>();

        for (Evaluacion e : evaluaciones.obtenerElementos()) {
            int idPelicula = e.getId_pelicula();
            Pelicula peli = peliculas.obtener(idPelicula);
            if (peli == null) continue;

            int idDirector = peli.getIdDirector();
            if (!directores.contieneClave(idDirector)) continue;

            MyArrayList<Double> listaPuntajes = puntajesPorDirector.obtener(idDirector);
            if(listaPuntajes == null){
                listaPuntajes = new MyArrayListImpl<>();
                try{puntajesPorDirector.insertar(idDirector, listaPuntajes);}
                catch (Exception ignored){};
            }
            listaPuntajes.agregar(e.getPuntaje());

            MyArrayList<Integer> listaPeliculas = peliculasPorDirector.obtener(idDirector);
            if (listaPeliculas == null) {
                listaPeliculas = new MyArrayListImpl<>();
                try { peliculasPorDirector.insertar(idDirector, listaPeliculas); } catch (Exception ignored) {}
            }

            boolean yaAgregada = false;
            for (int i = 0; i < listaPeliculas.size(); i++) {
                if (listaPeliculas.get(i).equals(idPelicula)) {
                    yaAgregada = true;
                    break;
                }
            }

            if (!yaAgregada) {
                listaPeliculas.agregar(idPelicula);
            }
        }

        MyArrayList<Integer> idsValidos = new MyArrayListImpl<>();
        MyHashTable<Integer, Double> medianas = new MyHashTableImpl<>();

        for (Integer idDirector : puntajesPorDirector.obtenerClaves()) {
            MyArrayList<Double> lista = puntajesPorDirector.obtener(idDirector);
            MyArrayList<Integer> pelis = peliculasPorDirector.obtener(idDirector);

            if (lista.size() > 100 && pelis != null && pelis.size() > 1) {
                lista.sort((a, b) -> Double.compare(a, b));
                double mediana;
                int n = lista.size();
                if (n % 2 == 0) {
                    mediana = (lista.get(n / 2 - 1) + lista.get(n / 2)) / 2.0;
                } else {
                    mediana = lista.get(n / 2);
                }

                try {
                    medianas.insertar(idDirector, mediana);
                    idsValidos.agregar(idDirector);
                } catch (Exception ignored) {}
            }
        }

        idsValidos.sort((a, b) -> Double.compare(medianas.obtener(b), medianas.obtener(a)));

        for (int i = 0; i < Math.min(10, idsValidos.size()); i++) {
            int id = idsValidos.get(i);
            Director d = directores.obtener(id);
            double mediana = medianas.obtener(id);
            int cantPelis = peliculasPorDirector.obtener(id).size();
            System.out.printf("%s,%d,%.2f\n", d.getNombre(), cantPelis, mediana);
        }
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

        for (Evaluacion e : evaluaciones.obtenerElementos()) {
            int idUsuario = e.getId_usuario();
            int idPelicula = e.getId_pelicula();
            Pelicula peli = peliculas.obtener(idPelicula);
            if (peli == null) continue;

            MyList<Integer> generosPelicula = peli.getGeneros();
            for (int i = 0; i < generosPelicula.size(); i++) {
                int idGenero = generosPelicula.get(i);

                Integer total = totalGenero.obtener(idGenero);
                if (total == null) {
                    try { totalGenero.insertar(idGenero, 1); } catch (Exception ignored) {}
                } else {
                    try {
                        totalGenero.borrar(idGenero);
                        totalGenero.insertar(idGenero, total + 1);
                    } catch (Exception ignored) {}
                }

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

        MyArrayList<Integer> listaGeneros = new MyArrayListImpl<>();
        for (Integer idGenero : totalGenero.obtenerClaves()) {
            listaGeneros.agregar(idGenero);
        }

        listaGeneros.sort((a, b) -> totalGenero.obtener(b) - totalGenero.obtener(a));
        int limite = Math.min(10, listaGeneros.size());

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
