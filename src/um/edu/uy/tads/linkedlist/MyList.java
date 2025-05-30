package um.edu.uy.tads.linkedlist;

public interface MyList<T> {
    void add(T value);

    T get(int position);

    boolean contains(T value);

    void remove(T value);

    int size();

    Nodo<T> getFirst();

    boolean isEmpty();
}
