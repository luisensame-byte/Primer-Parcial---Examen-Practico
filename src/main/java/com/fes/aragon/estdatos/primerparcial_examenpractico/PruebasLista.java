package com.fes.aragon.estdatos.primerparcial_examenpractico;

public class PruebasLista {
    static int ok = 0, fallos = 0, n = 0;

    static void prueba(String nombre, String inicial, String operacion, String esperado, String obtenido, ListaReproduccion l) {
        n++;
        boolean pasa = esperado.equals(obtenido) && (l == null || l.verificarConsistencia());
        if (pasa) ok++; else fallos++;
        System.out.println("P" + n + " " + nombre);
        System.out.println("   Estado inicial : " + inicial);
        System.out.println("   Operación      : " + operacion);
        System.out.println("   Esperado       : " + esperado);
        System.out.println("   Obtenido       : " + obtenido + (l != null ? "  | tamaño=" + l.tamanio() + ", enlaces consistentes=" + l.verificarConsistencia() : ""));
        System.out.println("   Resultado      : " + (pasa ? "PASA" : "FALLA") + "\n");
    }

    static String id(Cancion c) { return c == null ? "null" : String.valueOf(c.getId()); }

    /** Lista con ids 1..m agregados al final. */
    static ListaReproduccion crear(int m) {
        ListaReproduccion l = new ListaReproduccion();
        for (int i = 1; i <= m; i++) l.agregarAlFinal("T" + i, "A" + i, 100 + i);
        return l;
    }

    public static void main(String[] args) {
        ListaReproduccion l;

        l = new ListaReproduccion();
        String r = id(l.cancionActual()) + "," + id(l.avanzar()) + "," + id(l.retroceder()) + "," + id(l.eliminarActual())
                + "," + id(l.buscar(1)) + "," + l.seleccionar(1) + "," + id(l.eliminarPorId(1)) + "," + l.tamanio();
        prueba("Operaciones sobre lista vacía", "lista vacía", "actual, avanzar, retroceder, eliminarActual, buscar, seleccionar, eliminarPorId",
                "null,null,null,null,null,false,null,0", r, l);

        l = new ListaReproduccion();
        l.agregarAlFinal("T1", "A1", 100);
        prueba("Inserción de la primera canción", "lista vacía", "agregarAlFinal(T1)", "actual=1, recorrido=1", "actual=" + id(l.cancionActual()) + ", recorrido=" + l.idsRecorrido(true), l);

        l = crear(1);
        r = id(l.avanzar()) + "," + id(l.retroceder());
        prueba("Navegación con un solo nodo", "[1], actual=1", "avanzar; retroceder", "1,1", r, l);

        l = new ListaReproduccion();
        l.agregarAlFinal("A", "x", 10);   // id1
        l.agregarAlInicio("B", "x", 10);  // id2
        l.agregarAlFinal("C", "x", 10);   // id3
        l.agregarAlInicio("D", "x", 10);  // id4
        prueba("Inserciones al inicio y al final", "lista vacía", "fin(1), inicio(2), fin(3), inicio(4)",
                "adelante=4 2 1 3, atrás=3 1 2 4, actual=1", "adelante=" + l.idsRecorrido(true) + ", atrás=" + l.idsRecorrido(false) + ", actual=" + id(l.cancionActual()), l);

        l = crear(6);
        prueba("Recorrido con 6 canciones", "[1..6]", "idsRecorrido adelante y atrás", "1 2 3 4 5 6 | 6 5 4 3 2 1",
                l.idsRecorrido(true) + " | " + l.idsRecorrido(false), l);

        l = crear(5);
        l.seleccionar(5);
        String a = id(l.avanzar());
        l.seleccionar(1);
        String b = id(l.retroceder());
        prueba("Avance desde la última y retroceso desde la primera", "[1..5]", "actual=5, avanzar; actual=1, retroceder", "1,5", a + "," + b, l);

        l = crear(5);
        l.eliminarPorId(1);
        prueba("Eliminar primer nodo", "[1..5], actual=1", "eliminarPorId(1)", "2 3 4 5, actual=2",
                l.idsRecorrido(true) + ", actual=" + id(l.cancionActual()), l);
        l = crear(5);
        l.eliminarPorId(5);
        prueba("Eliminar último nodo", "[1..5], actual=1", "eliminarPorId(5)", "1 2 3 4, actual=1",
                l.idsRecorrido(true) + ", actual=" + id(l.cancionActual()), l);
        l = crear(5);
        l.eliminarPorId(3);
        prueba("Eliminar nodo intermedio (actual se conserva)", "[1..5], actual=1", "eliminarPorId(3)", "1 2 4 5 | 5 4 2 1, actual=1",
                l.idsRecorrido(true) + " | " + l.idsRecorrido(false) + ", actual=" + id(l.cancionActual()), l);

        l = crear(4);
        l.seleccionar(2);
        l.eliminarActual();
        prueba("Eliminar la canción actual (pasa a la siguiente)", "[1..4], actual=2", "eliminarActual", "1 3 4, actual=3",
                l.idsRecorrido(true) + ", actual=" + id(l.cancionActual()), l);
        l = crear(3);
        l.seleccionar(3);
        l.eliminarActual();
        prueba("Eliminar actual siendo el último (pasa al primero)", "[1..3], actual=3", "eliminarActual", "1 2, actual=1",
                l.idsRecorrido(true) + ", actual=" + id(l.cancionActual()), l);
        l = crear(1);
        l.eliminarActual();
        prueba("Eliminar la única canción", "[1]", "eliminarActual", "[], actual=null, tamaño 0",
                l.idsRecorrido(true) + ", actual=" + id(l.cancionActual()) + ", tamaño " + l.tamanio(), l);
        l.agregarAlFinal("N", "N", 5);
        prueba("Reinsertar tras vaciar", "lista vacía (tras eliminar)", "agregarAlFinal", "recorrido=2, actual=2",
                "recorrido=" + l.idsRecorrido(true) + ", actual=" + id(l.cancionActual()), l);

        l = crear(3);
        String antes = l.idsRecorrido(true);
        String res = id(l.buscar(99)) + "," + id(l.eliminarPorId(99)) + "," + l.seleccionar(99) + "," + l.idsRecorrido(true).equals(antes);
        prueba("Búsqueda/eliminación/selección de ID inexistente", "[1 2 3]", "buscar(99), eliminarPorId(99), seleccionar(99)", "null,null,false,true", res, l);

        l = crear(3);
        System.out.println("   (salida de la reproducción simulada, k=7)");
        l.reproducir(7);
        prueba("Reproducción simulada con k > n", "[1 2 3], actual=1", "reproducir(7) (posición final: 1+7 mod 3 = 2)", "actual=2", "actual=" + id(l.cancionActual()), l);

        l = crear(3);
        String e1, e2, e3, e4, e5;
        try { l.agregarAlFinal("X", "Y", 0); e1 = "no lanzó"; } catch (IllegalArgumentException e) { e1 = "excepción"; }
        try { l.agregarAlFinal("X", "Y", -5); e2 = "no lanzó"; } catch (IllegalArgumentException e) { e2 = "excepción"; }
        try { l.agregarAlFinal("  ", "Y", 5); e3 = "no lanzó"; } catch (IllegalArgumentException e) { e3 = "excepción"; }
        try { l.reproducir(0); e4 = "no lanzó"; } catch (IllegalArgumentException e) { e4 = "excepción"; }
        try { l.reproducir(-2); e5 = "no lanzó"; } catch (IllegalArgumentException e) { e5 = "excepción"; }
        prueba("Entradas inválidas (duración 0/negativa, título vacío, k<=0)", "[1 2 3]", "agregar con datos inválidos; reproducir(0), reproducir(-2)",
                "excepción,excepción,excepción,excepción,excepción, lista=1 2 3", e1 + "," + e2 + "," + e3 + "," + e4 + "," + e5 + ", lista=" + l.idsRecorrido(true), l);

        System.out.println("=== Resumen: " + ok + " pasan, " + fallos + " fallan, de " + (ok + fallos) + " ===");
        if (fallos > 0) System.exit(1);
    }
}

