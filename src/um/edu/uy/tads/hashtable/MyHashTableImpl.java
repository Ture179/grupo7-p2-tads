package um.edu.uy.tads.hashtable;

import um.edu.uy.exceptions.ElementoYaExistente;
import um.edu.uy.tads.linkedlist.MyLinkedListImpl;
import um.edu.uy.tads.linkedlist.MyList;

import java.util.ArrayList;
import java.util.List;

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
    private int capacidad;
    private int tamano;

    public MyHashTableImpl() {
        this.capacidad = 1000000;
        this.tamano = 0;
        this.tabla = new MyList[capacidad];
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new MyLinkedListImpl<>();
        }
    }

    private int hash(K clave) {
        return Math.abs(clave.hashCode()) % capacidad;
    }

    @Override
    public void insertar(K clave, V valor) throws ElementoYaExistente {

        int indice = hash(clave);
        MyList<Entrada<K, V>> lista = tabla[indice];

        for (int i = 0; i < lista.size(); i++) {
            Entrada<K, V> entrada = lista.get(i);
            if (entrada.clave.equals(clave)) {
                    throw new ElementoYaExistente("La clave ya existe.");
            }
        }

        lista.add(new Entrada<>(clave, valor));
        tamano++;
    }

    @Override
    public boolean contieneClave(K clave) {
        return obtener(clave) != null;
    }

    @Override
    public V obtener(K clave) {
        int index = hash(clave);
        MyList<Entrada<K, V>> bucket = tabla[index];

        for (int i = 0; i < bucket.size(); i++) {
            Entrada<K, V> entrada = bucket.get(i);
            if (entrada.clave.equals(clave)) {
                return entrada.valor;
            }
        }

        return null;
    }

    @Override
    public void borrar(K clave) {

        int indice = hash(clave);
        MyList<Entrada<K, V>> bucket = tabla[indice];

        for (int i = 0; i < bucket.size(); i++) {
            Entrada<K, V> entrada = bucket.get(i);
            if (entrada.clave.equals(clave)) {
                bucket.remove(entrada);
                tamano--;
                return;
            }
        }

    }

    @Override
    public int size() {
        return tamano;
    }

    @Override
    public List<V> obtenerElementos() {
        List<V> elementos = new ArrayList<>();
        for (MyList<Entrada<K, V>> bucket : tabla) {
            for (int i = 0; i < bucket.size(); i++) {
                elementos.add(bucket.get(i).valor);
            }
        }
        return elementos;
    }

}
