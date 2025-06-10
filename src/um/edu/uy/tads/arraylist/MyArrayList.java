package um.edu.uy.tads.arraylist;

import java.util.Comparator;

public interface MyArrayList<T> {

        void agregar(T elemento);

        T get(int indice);

        int size();

        boolean isEmpty();

        void sort(Comparator<T> comparador);
}
