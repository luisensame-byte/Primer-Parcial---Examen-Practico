package com.fes.aragon.estdatos.primerparcial_examenpractico;

import java.util.Scanner;

/** Menú de interacción. Separado de la estructura de datos. */
public class Main {
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        ListaReproduccion lista = new ListaReproduccion();
        int op;
        do {
            imprimirMenu();
            op = leerEntero("Opción: ");
            switch (op) {
                case 1: agregar(lista, true); break;
                case 2: agregar(lista, false); break;
                case 3: {
                    int id = leerEntero("ID a buscar: ");
                    Cancion c = lista.buscar(id);
                    System.out.println(c == null ? mensajeNoEncontrada(lista, id) : "Encontrada: " + c);
                    break;
                }
                case 4: {
                    int id = leerEntero("ID a seleccionar: ");
                    System.out.println(lista.seleccionar(id)
                            ? "Canción actual: " + lista.cancionActual() : mensajeNoEncontrada(lista, id));
                    break;
                }
                case 5: {
                    int id = leerEntero("ID a eliminar: ");
                    Cancion c = lista.eliminarPorId(id);
                    System.out.println(c == null ? mensajeNoEncontrada(lista, id) : "Eliminada: " + c);
                    break;
                }
                case 6: {
                    Cancion c = lista.eliminarActual();
                    System.out.println(c == null ? "La lista está vacía." : "Eliminada: " + c);
                    break;
                }
                case 7: {
                    Cancion c = lista.cancionActual();
                    System.out.println(c == null ? "La lista está vacía." : "Actual: " + c);
                    break;
                }
                case 8: {
                    Cancion c = lista.avanzar();
                    System.out.println(c == null ? "La lista está vacía." : "Ahora: " + c);
                    break;
                }
                case 9: {
                    Cancion c = lista.retroceder();
                    System.out.println(c == null ? "La lista está vacía." : "Ahora: " + c);
                    break;
                }
                case 10:
                    System.out.println("--- Adelante ---");
                    lista.mostrarAdelante();
                    System.out.println("--- Atrás ---");
                    lista.mostrarAtras();
                    break;
                case 11:
                    System.out.println("Cantidad de canciones: " + lista.tamanio());
                    break;
                case 12: {
                    int k = leerEntero("¿Cuántas canciones reproducir? (k > 0): ");
                    if (k <= 0) System.out.println("k debe ser un entero positivo.");
                    else lista.reproducir(k);
                    break;
                }
                case 13: System.out.println("¡Hasta luego!"); break;
                default: System.out.println("Opción inválida (1-13).");
            }
        } while (op != 13);
    }

    private static String mensajeNoEncontrada(ListaReproduccion l, int id) {
        return l.estaVacia() ? "La lista está vacía." : "No existe una canción con ID " + id + ". La lista no cambió.";
    }

    private static void agregar(ListaReproduccion lista, boolean alInicio) {
        System.out.print("Título: ");
        String t = in.nextLine();
        System.out.print("Artista: ");
        String a = in.nextLine();
        int d = leerEntero("Duración (segundos): ");
        try {
            Cancion c = alInicio ? lista.agregarAlInicio(t, a, d) : lista.agregarAlFinal(t, a, d);
            System.out.println("Agregada: " + c);
        } catch (IllegalArgumentException e) {
            System.out.println("Datos inválidos: " + e.getMessage());
        }
    }

    private static void imprimirMenu() {
        System.out.println("\n===== PLAYLIST =====");
        System.out.println(" 1. Agregar al inicio      8. Avanzar");
        System.out.println(" 2. Agregar al final       9. Retroceder");
        System.out.println(" 3. Buscar por ID         10. Mostrar lista (ambos sentidos)");
        System.out.println(" 4. Seleccionar por ID    11. Cantidad de canciones");
        System.out.println(" 5. Eliminar por ID       12. Reproducción simulada (k)");
        System.out.println(" 6. Eliminar la actual    13. Salir");
        System.out.println(" 7. Ver canción actual");
    }

    private static int leerEntero(String msg) {
        while (true) {
            System.out.print(msg);
            if (!in.hasNextLine()) return 13; // fin de entrada: salir
            String s = in.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida: escribe un número entero.");
            }
        }
    }
}

