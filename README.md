Proyecto de Estructuras de Datos (FES Aragón, UNAM). Gestor de lista de reproducción en consola implementado con una **lista doblemente enlazada circular** propia, sin usar `LinkedList`, `ArrayList`, arreglos ni otras colecciones. La reproducción es simulada: solo se muestran en consola los datos de la canción actual.

## Integrantes
- Luis Enrique Sánchez Mejía
- Contreras de la Riva Eric Donovan
- Leal Alarcon Alejandro
- Morado Hernández Angel Sebastian 

## Requisitos
- **Java 17 o superior** (probado con Java 21).
- No se necesitan librerías externas.

## Estructura del proyecto
| Archivo | Responsabilidad |
|---|---|
| `Cancion.java` | Datos de la canción: id, título, artista y duración en segundos (> 0) |
| `Nodo.java` | Nodo con la canción y las referencias `anterior` y `siguiente` |
| `ListaReproduccion.java` | Lista circular: `inicio`, `fin`, `actual` y contador de elementos |
| `Main.java` | Menú de consola (separado de la estructura de datos) |
| `PruebasLista.java` | Pruebas reproducibles de funcionamiento |
| `reporte_tecnico.md` | Fuente del reporte técnico (se entrega en PDF) |

## Compilación y ejecución
Desde la carpeta donde están los archivos:

```
javac *.java
java Main
```

Para ejecutar las pruebas:

```
java PruebasLista
```

## Menú
| Opción | Acción |
|---|---|
| 1 | Agregar una canción al inicio |
| 2 | Agregar una canción al final |
| 3 | Buscar una canción por ID |
| 4 | Seleccionar una canción por ID (pasa a ser la actual) |
| 5 | Eliminar una canción por ID |
| 6 | Eliminar la canción actual |
| 7 | Consultar la canción actual |
| 8 | Avanzar a la siguiente canción |
| 9 | Retroceder a la canción anterior |
| 10 | Mostrar la lista completa en ambos sentidos |
| 11 | Consultar la cantidad de canciones |
| 12 | Simular la reproducción de `k` canciones |
| 13 | Salir |

## Reglas de funcionamiento
- Los IDs se generan automáticamente con un contador incremental (1, 2, 3, ...).
- La primera canción insertada se convierte en la actual; agregar más no la cambia.
- Al eliminar la canción actual, la nueva actual es la que le seguía.
- Al avanzar desde la última se regresa a la primera, y al retroceder desde la primera se llega a la última.
- Buscar o eliminar un ID inexistente informa la situación sin modificar la lista.
- Las operaciones sobre una lista vacía muestran un mensaje claro, sin errores.
- Entradas inválidas (duración ≤ 0, título o artista vacío, `k` ≤ 0, texto en lugar de número) se rechazan con un mensaje.

## Complejidad temporal (n = cantidad de canciones)
| Operación | Complejidad |
|---|---|
| Agregar al inicio o al final | O(1) |
| Consultar la canción actual | O(1) |
| Avanzar o retroceder | O(1) |
| Eliminar la canción actual | O(1) |
| Consultar la cantidad | O(1) |
| Buscar o seleccionar por ID | O(n) |
| Eliminar por ID | O(n) |
| Mostrar todas las canciones | O(n) |
| Simular `k` reproducciones | O(k) |

La justificación detallada está en el reporte técnico.

## Pruebas
`PruebasLista` ejecuta 16 pruebas que cubren lista vacía, primera inserción, un solo nodo, inserciones en ambos extremos, recorridos con 6 canciones, navegación circular, eliminación del primero, último, intermedio, actual y única canción, ID inexistente, `k` mayor que la cantidad de canciones y entradas inválidas. Cada prueba imprime el estado inicial, la operación, el resultado esperado y el obtenido, y verifica el tamaño y la consistencia de los enlaces. Al final muestra un resumen (`16 pasan, 0 fallan`).

## Ejemplo de uso
```
===== PLAYLIST =====
Opción: 2
Título: Bohemian Rhapsody
Artista: Queen
Duración (segundos): 354
Agregada: [#1] Bohemian Rhapsody - Queen (354 s)
```
