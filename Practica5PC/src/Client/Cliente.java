package Client;

import Common.MensajeListaUsuarios;

import java.io.*;
import java.net.*;
import java.util.*;
import Common.*;
import Server.OyenteServidor;

import java.util.Scanner;

public class Cliente {
    private Socket socket;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private ConcurrentConsole ccout;

    public Cliente(String host, int puerto) throws IOException {
        this.socket = new Socket(host, puerto);
        this.salida = new ObjectOutputStream(socket.getOutputStream());
        this.entrada = new ObjectInputStream(socket.getInputStream());
        this.ccout = new ConcurrentConsole();
    }

    public void iniciar() {
        try {
            Scanner scanner = new Scanner(System.in);

            // Paso 1: Enviar mensaje de conexión al servidor
            ccout.print("Introduce el nombre de tu equipo: ");
            String nombreEquipo = scanner.nextLine();
            ccout.print("Introduce los puntos de habilidad de tu equipo: ");
            int puntos = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            Thread hiloCliente = new Thread(new OyenteCliente(socket, ccout));
            hiloCliente.start();

            Boolean salir = false;
            // Paso 2: Leer respuesta del servidor
            while (!salir) {
                mostrarMenu();
                String opcion = scanner.nextLine();
                switch (opcion) {
                    case "1":
                       /* MensajeListaUsuarios lista = new MensajeListaUsuarios();
                        salida.writeObject(mensajeConexion);

                        Mensaje mensaje = (Mensaje) entrada.readObject();
                        MensajeListaUsuarios listaUsuarios = (MensajeListaUsuarios) mensaje;
                        ccout.print("Usuarios disponibles:");
                        for (String usuario : listaUsuarios.getNombresEquipos()) {
                            System.out.println("- " + usuario);
                        }
                        break;
*/
                        break;
                    case "2":
                        //MensajeConexion mensajeConexion = new MensajeConexion(nombreEquipo, puntos);
                        //salida.writeObject(mensajeConexion);
                        //Mensaje mensaje = (Mensaje) entrada.readObject();



                        break;
                    case "3":
                        salir = true;
                        System.out.println("Sesión cerrada. ¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor, intente de nuevo.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos();
        }
    }

    /**
     * Muestra el menú principal.
     */
    private static void mostrarMenu() {
        System.out.println("------------------ MENÚ PRINCIPAL -------------------");
        System.out.println("1. Consultar la información disponible");
        System.out.println("2. Descargar la información deseada");
        System.out.println("3. Salir del sistema");
        System.out.println("-----------------------------------------------------");
    }

    private void cerrarRecursos() {
        try {
            if (salida != null) salida.close();
            if (entrada != null) entrada.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Cliente cliente = new Cliente("localhost", 5000);
            cliente.iniciar();
        } catch (IOException e) {
            System.err.println("Error al conectar con el servidor: " + e.getMessage());
        }
    }
}