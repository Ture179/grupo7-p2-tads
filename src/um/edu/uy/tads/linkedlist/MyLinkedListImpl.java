package um.edu.uy.tads.linkedlist;

public class MyLinkedListImpl<T> implements MyList<T> {
    private Nodo<T> head;

    @Override
    public void add(T value) {
        Nodo<T> nuevo = new Nodo<>(value);
        if (head == null) {
            head = nuevo;
        } else {
            Nodo<T> actual = head;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
    }

    @Override
    public T get(int position) {
        if (position < 0) return null;

        Nodo<T> actual = head;
        int contador = 0;
        while (actual != null) {
            if (contador == position) {
                return actual.getValor();
            }
            actual = actual.getSiguiente();
            contador++;
        }
        return null;
    }

    @Override
    public boolean contains(T value) {
        Nodo<T> actual = head;
        while (actual != null) {
            if (actual.getValor().equals(value)) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    @Override
    public void remove(T value) {
        if (head == null) return;

        if (head.getValor().equals(value)) {
            head = head.getSiguiente();
            return;
        }

        Nodo<T> actual = head;
        while (actual.getSiguiente() != null && !actual.getSiguiente().getValor().equals(value)) {
            actual = actual.getSiguiente();
        }

        if (actual.getSiguiente() != null) {
            actual.setSiguiente(actual.getSiguiente().getSiguiente());
        }
    }

    @Override
    public int size() {
        int contador = 0;
        Nodo<T> actual = head;
        while (actual != null) {
            contador++;
            actual = actual.getSiguiente();
        }
        return contador;
    }

    @Override
    public Nodo<T> getFirst() {
        return head;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }


}
