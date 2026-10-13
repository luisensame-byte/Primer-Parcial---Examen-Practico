package com.fes.aragon.estdatos.primerparcial_examenpractico;

public class ListaReproduccion {
    private Nodo inicio;
    private Nodo fin;
    private Nodo actual;
    private int tamanio;
    private int siguienteId = 1; // contador incremental para ids únicos

    public boolean estaVacia() { return tamanio == 0; }
    public int tamanio() { return tamanio; }

    // ---------- Inserción: O(1) ----------
    public Cancion agregarAlInicio(String titulo, String artista, int duracion) {
        Nodo nuevo = new Nodo(new Cancion(siguienteId, titulo, artista, duracion));
        siguienteId++; // solo se consume el id si la canción fue válida
        if (tamanio == 0) {
            insertarPrimero(nuevo);
        } else {
            nuevo.siguiente = inicio;
            nuevo.anterior = fin;
            inicio.anterior = nuevo;
            fin.siguiente = nuevo;
            inicio = nuevo;
        }
        tamanio++;
        return nuevo.cancion;
    }

    public Cancion agregarAlFinal(String titulo, String artista, int duracion) {
        Nodo nuevo = new Nodo(new Cancion(siguienteId, titulo, artista, duracion));
        siguienteId++;
        if (tamanio == 0) {
            insertarPrimero(nuevo);
        } else {
            nuevo.anterior = fin;
            nuevo.siguiente = inicio;
            fin.siguiente = nuevo;
            inicio.anterior = nuevo;
            fin = nuevo;
        }
        tamanio++;
        return nuevo.cancion;
    }

    private void insertarPrimero(Nodo n) {
        n.siguiente = n;
        n.anterior = n;
        inicio = n;
        fin = n;
        actual = n; // la primera canción es la actual
    }

    // ---------- Consulta y navegación: O(1) ----------
    public Cancion cancionActual() {
        return actual == null ? null : actual.cancion;
    }

    public Cancion avanzar() {
        if (actual == null) return null;
        actual = actual.siguiente;
        return actual.cancion;
    }

    public Cancion retroceder() {
        if (actual == null) return null;
        actual = actual.anterior;
        return actual.cancion;
    }

    // ---------- Búsqueda: O(n) ----------
    /** Localiza el nodo por id visitando a lo más n nodos (condición de término circular). */
    private Nodo buscarNodo(int id) {
        if (tamanio == 0) return null;
        Nodo p = inicio;
        for (int i = 0; i < tamanio; i++) {
            if (p.cancion.getId() == id) return p;
            p = p.siguiente;
        }
        return null;
    }

    public Cancion buscar(int id) {
        Nodo n = buscarNodo(id);
        return n == null ? null : n.cancion;
    }

    public boolean seleccionar(int id) {
        Nodo n = buscarNodo(id);
        if (n == null) return false;
        actual = n;
        return true;
    }

    // ---------- Eliminación ----------
    /** O(n): búsqueda + desenlace O(1). */
    public Cancion eliminarPorId(int id) {
        Nodo n = buscarNodo(id);
        if (n == null) return null;
        return desenlazar(n);
    }

    /** O(1): la referencia al nodo ya está disponible. */
    public Cancion eliminarActual() {
        if (actual == null) return null;
        return desenlazar(actual);
    }

    /** Desenlaza un nodo ya localizado en O(1) y repara inicio, fin y actual. */

    private Cancion desenlazar(Nodo n) {
        Cancion c = n.cancion;
        if (tamanio == 1) {
            inicio = null;
            fin = null;
            actual = null;
        } else {
            Nodo ant = n.anterior;   // se conservan referencias antes de cambiar enlaces
            Nodo sig = n.siguiente;
            ant.siguiente = sig;
            sig.anterior = ant;
            if (n == inicio) inicio = sig;
            if (n == fin) fin = ant;
            if (n == actual) actual = sig; // la que le seguía
        }
        n.anterior = null; // el nodo eliminado ya no referencia la lista
        n.siguiente = null;
        tamanio--;
        return c;
    }

    // ---------- Recorridos: O(n) ----------
    public void mostrarAdelante() {
        if (tamanio == 0) { System.out.println("(La lista está vacía)"); return; }
        Nodo p = inicio;
        for (int i = 0; i < tamanio; i++) {
            System.out.println((p == actual ? " ▶ " : "   ") + p.cancion);
            p = p.siguiente;
        }
    }

    public void mostrarAtras() {
        if (tamanio == 0) { System.out.println("(La lista está vacía)"); return; }
        Nodo p = fin;
        for (int i = 0; i < tamanio; i++) {
            System.out.println((p == actual ? " ▶ " : "   ") + p.cancion);
            p = p.anterior;
        }
    }

    /** Ids separados por espacio, para pruebas. Vacía => "[]". */
    public String idsRecorrido(boolean adelante) {
        if (tamanio == 0) return "[]";
        StringBuilder sb = new StringBuilder();
        Nodo p = adelante ? inicio : fin;
        for (int i = 0; i < tamanio; i++) {
            if (i > 0) sb.append(' ');
            sb.append(p.cancion.getId());
            p = adelante ? p.siguiente : p.anterior;
        }
        return sb.toString();
    }

    // ---------- Reproducción simulada: O(k) ----------
    public void reproducir(int k) {
        if (k <= 0) throw new IllegalArgumentException("k debe ser un entero positivo.");
        if (actual == null) { System.out.println("No hay canciones para reproducir."); return; }
        for (int i = 0; i < k; i++) {
            System.out.println("♪ Reproduciendo: " + actual.cancion);
            actual = actual.siguiente;
        }
    }

    // ---------- Verificación de invariantes: O(n) ----------
    public boolean verificarConsistencia() {
        if (tamanio == 0)
            return inicio == null && fin == null && actual == null;
        if (inicio == null || fin == null || actual == null) return false;
        if (fin.siguiente != inicio || inicio.anterior != fin) return false;
        if (tamanio == 1)
            return inicio == fin && inicio.siguiente == inicio && inicio.anterior == inicio && actual == inicio;
        boolean actualEncontrado = false;
        Nodo p = inicio;
        for (int i = 0; i < tamanio; i++) {
            if (p.siguiente == null || p.anterior == null) return false;
            if (p.siguiente.anterior != p || p.anterior.siguiente != p) return false;
            if (p == actual) actualEncontrado = true;
            p = p.siguiente;
        }
        if (p != inicio) return false; // tras n pasos se debe cerrar el ciclo
        if (!actualEncontrado) return false;
        // el último recorrido hacia adelante debe terminar en fin
        Nodo q = inicio;
        for (int i = 0; i < tamanio - 1; i++) q = q.siguiente;
        return q == fin;
    }
}

