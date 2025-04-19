package Client;

import Common.MensajeListaUsuarios;

import java.io.*;
import java.net.*;
import java.util.*;
import Common.*;
import java.util.Scanner;

public class Cliente {
    private Socket socket;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;

    public Cliente(String host, int puerto) throws IOException {
        this.socket = new Socket(host, puerto);
        this.salida = new ObjectOutputStream(socket.getOutputStream());
        this.entrada = new ObjectInputStream(socket.getInputStream());
    }

    public void iniciar() {
        try {
            Scanner scanner = new Scanner(System.in);

            // Paso 1: Enviar mensaje de conexión al servidor
            System.out.print("Introduce el nombre de tu equipo: ");
            String nombreEquipo = scanner.nextLine();
            System.out.print("Introduce los puntos de habilidad de tu equipo: ");
            int puntos = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            MensajeConexion mensajeConexion = new MensajeConexion(nombreEquipo, puntos);
            salida.writeObject(mensajeConexion);
            salida.flush();
            System.out.println("Conexión enviada al servidor.");

            // Paso 2: Leer respuesta del servidor
            while (true) {
                try {
                    Mensaje mensaje = (Mensaje) entrada.readObject();
                    System.out.println("Mensaje recibido del servidor: " + mensaje.getTipo());

                    switch (mensaje.getTipo()) {
                        case "LISTA_USUARIOS":
                            MensajeListaUsuarios listaUsuarios = (MensajeListaUsuarios) mensaje;
                            System.out.println("Usuarios disponibles:");
                            for (String usuario : listaUsuarios.getNombresEquipos()) {
                                System.out.println("- " + usuario);
                            }
                            break;

                        case "PREPARADOR_SERVIDOR_CLIENTE_RECEPTOR":
                            MensajePreparadorSCReceptor preparador = (MensajePreparadorSCReceptor) mensaje;
                            System.out.println("El equipo " + preparador.getNombreEquipoEmisor() + " está listo para jugar contigo.");
                            // Aquí podrías agregar lógica para establecer una conexión P2P






                            break;

                        default:
                            System.out.println("Tipo de mensaje desconocido.");
                    }
                } catch (EOFException e) {
                    System.err.println("El servidor cerró la conexión inesperadamente.");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos();
        }
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