package um.edu.uy.tads.arraylist;

import java.util.Comparator;

public class MyArrayListImpl<T> implements MyArrayList<T> {
    private Object[] datos;
    private int size;

    public MyArrayListImpl() {
        this(10); // capacidad por defecto
    }

    public MyArrayListImpl(int capacidadInicial) {
        datos = new Object[capacidadInicial];
        size = 0;
    }

    @Override
    public void agregar(T elemento) {
        if (size == datos.length) {
            redimensionar();
        }
        datos[size++] = elemento;
    }

    @Override
    public T get(int indice) {
        if (indice < 0 || indice >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        return (T) datos[indice];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void sort(Comparator<T> comparador) {
        if (size <= 1) return;

        T[] aux = (T[]) new Object[size];
        mergesort(0, size - 1, comparador, aux);
    }

    private void mergesort(int inicio, int fin, Comparator<T> comp, T[] aux) {
        if (inicio >= fin) return;

        int medio = (inicio + fin) / 2;
        mergesort(inicio, medio, comp, aux);
        mergesort(medio + 1, fin, comp, aux);
        merge(inicio, medio, fin, comp, aux);
    }

    private void merge(int inicio, int medio, int fin, Comparator<T> comp, T[] aux) {
        for (int k = inicio; k <= fin; k++) {
            aux[k] = (T) datos[k];
        }

        int i = inicio, j = medio + 1;
        for (int k = inicio; k <= fin; k++) {
            if (i > medio) datos[k] = aux[j++];
            else if (j > fin) datos[k] = aux[i++];
            else if (comp.compare(aux[i], aux[j]) <= 0) datos[k] = aux[i++];
            else datos[k] = aux[j++];
        }
    }


    private void redimensionar() {
        Object[] nuevo = new Object[datos.length * 2];
        for (int i = 0; i < datos.length; i++) {
            nuevo[i] = datos[i];
        }
        datos = nuevo;
    }
}

