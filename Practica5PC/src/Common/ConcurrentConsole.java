package Common;

import java.util.List;
import java.util.Map;

/**
 * Clase ConcurrentConsole: Controla el acceso concurrente a la consola.
 *
 * En aplicaciones multihilo, múltiples hilos pueden intentar escribir en la consola simultáneamente,
 * lo que puede resultar en mensajes mezclados o interrupciones inesperadas. Esta clase utiliza un
 * monitor implícito (mediante el modificador `synchronized`) para garantizar que solo un hilo pueda
 * acceder a la consola a la vez. De esta manera, se evitan condiciones de carrera y se asegura que
 * los mensajes se impriman de forma ordenada y legible.
 */
public class ConcurrentConsole {

    /**
     * Método sincronizado para escribir un mensaje en la consola.
     *
     * Este método permite imprimir un mensaje en la consola de manera segura. El uso del modificador
     * `synchronized` garantiza que solo un hilo pueda ejecutar este método a la vez. Si otro hilo
     * intenta acceder mientras el monitor está bloqueado, deberá esperar hasta que el hilo actual
     * termine de usar el recurso compartido (la consola).
     *
     * @param message El mensaje que se desea imprimir en la consola.
     */
    public synchronized void print(String message) {
        System.out.println(message);
    }

    /**
     * Método sincronizado para escribir un mensaje formateado en la consola.
     *
     * Similar al método `print`, pero permite formatear el mensaje utilizando una plantilla y
     * argumentos variables. Esto es útil cuando se necesita personalizar el formato del mensaje
     * antes de imprimirlo. Al igual que `print`, este método está sincronizado para evitar conflictos
     * entre hilos concurrentes.
     *
     * @param format La plantilla de formato del mensaje (similar a `String.format`).
     * @param args   Los argumentos que se insertarán en la plantilla de formato.
     */
    public synchronized void printf(String format, Object... args) {
        System.out.printf(format, args);
    }

    public synchronized void printmap(Map<String, List<String>> mapa) {
        for (String clave : mapa.keySet()) {
            List<String> valores = mapa.get(clave); // Obtener la lista de valores asociada a la clave
            if (valores != null && !valores.isEmpty()) {
                System.out.print(clave + " - "); // Imprimir la clave
                for (int i = 0; i < valores.size(); i++) {
                    System.out.print(valores.get(i)); // Imprimir cada valor de la lista
                    if (i < valores.size() - 1) {
                        System.out.print(", "); // Separar valores con coma, excepto el último
                    }
                }
                System.out.println(); // Nueva línea después de imprimir todos los valores de la lista
            } else {
                System.out.println(clave + " - (Sin valores)"); // Manejar el caso de una lista vacía
            }
        }
    }
}
