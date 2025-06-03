package um.edu.uy.tads.hashtable;

import um.edu.uy.exceptions.ElementoYaExistente;
import um.edu.uy.tads.linkedlist.MyLinkedListImpl;
import um.edu.uy.tads.linkedlist.MyList;

public class MyHashTableImpl<K,V> implements MyHashTable<K,V> {

    private static class Entrada<K,V>{
        K clave;
        V valor;

        public Entrada(K clave, V valor) {
            this.clave = clave;
            this.valor = valor;
        }
    }

    private MyList<Entrada<K,V>>[] tabla;
    private int tamano = 1000000;

    public MyHashTableImpl() {
        this.tabla = new MyList[tamano];
        for (int i = 0; i < tamano; i++) {
            tabla[i] = new MyLinkedListImpl<>();
        }
    }

    private int hash(K clave) {
        int codigoHash = clave.hashCode();
        return codigoHash % tabla.length;
    }

    @Override
    public void insertar(K clave, V valor) throws ElementoYaExistente {

        int indice = hash(clave);
        MyList<Entrada<K, V>> lista = tabla[indice];

        for (int i = 0; i < lista.size(); i++) {
            Entrada<K, V> entrada = lista.get(i);
            if (entrada.clave.equals(clave)) {
                throw new ElementoYaExistente("La clave '" + clave + "' ya existe.");
            }
        }

        lista.add(new Entrada<>(clave, valor));



    }

    @Override
    public boolean contieneClave(K clave) {
        return false;
    }

    @Override
    public V obtener(K clave) {
        return null;
    }

    @Override
    public void borrar(K clave) {

    }
}
