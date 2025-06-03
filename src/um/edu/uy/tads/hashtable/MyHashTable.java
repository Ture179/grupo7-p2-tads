package um.edu.uy.tads.hashtable;

import um.edu.uy.exceptions.ElementoYaExistente;

public interface MyHashTable<K, V> {

    void insertar(K clave, V valor) throws ElementoYaExistente;

    boolean contieneClave(K clave);

    V obtener(K clave);

    void borrar(K clave);
}
