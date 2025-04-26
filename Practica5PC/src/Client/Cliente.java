package Client;

import Common.MensajeListaUsuarios;

import java.io.*;
import java.net.*;
import java.util.*;
import Common.*;

import java.util.Scanner;

public class Cliente {

    private Usuario datos_cliente;
    private Socket socket;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private ConcurrentConsole ccout;

    public Cliente(String host, int puerto) throws IOException {
        this.socket = new Socket(host, puerto);
        this.salida = new ObjectOutputStream(socket.getOutputStream());
        this.salida.flush();
        this.entrada = new ObjectInputStream(socket.getInputStream());
        this.ccout = new ConcurrentConsole();
    }

    public void iniciar() {
        try {
            Scanner scanner = new Scanner(System.in);

            ccout.print("Introduce tu nombre de usuario: ");
            String nombre = scanner.nextLine();
            this.datos_cliente = new Usuario(nombre);

            // Enviar solicitud de conexión
            MensajeConexion mensajeConexion = new MensajeConexion(datos_cliente.getNombre());
            salida.writeObject(mensajeConexion);
            salida.flush();
            ccout.print("Conexión enviada al servidor.");


            Thread hiloCliente = new Thread(new OyenteServidor(socket,datos_cliente, ccout,salida,entrada));
            hiloCliente.start();

            Boolean salir = false;
            // Paso 2: Leer respuesta del servidor
            while (!salir) {
                mostrarMenu();
                String opcion = scanner.nextLine();
                switch (opcion) {
                    case "1":
                        opcion_solicitarListas();
                        break;
                    case "2":
                        opcion_descargar();
                        break;
                    case "3":
                        opcion_ModificarListas();
                        break;
                    case "4":
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
        System.out.println("3. Modificar Listas");
        System.out.println("4. Salir");
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

    private void opcion_solicitarListas(){

    }

    private void opcion_descargar(){

    }

    private void opcion_ModificarListas(){

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