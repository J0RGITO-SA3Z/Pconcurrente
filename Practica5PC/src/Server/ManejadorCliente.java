package Server;

import Common.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class ManejadorCliente implements Runnable {
    private Socket socketCliente;

    public ManejadorCliente(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }

    @Override
    public void run() {
        try (
                ObjectInputStream entrada = new ObjectInputStream(socketCliente.getInputStream());
                ObjectOutputStream salida = new ObjectOutputStream(socketCliente.getOutputStream())
        ) {
            // Leer mensaje del cliente
            Mensaje mensaje = (Mensaje) entrada.readObject();
            System.out.println("Mensaje recibido: " + mensaje.getTipo());

            switch (mensaje.getTipo()) {
                case "CONEXION":
                    MensajeConexion conexion = (MensajeConexion) mensaje;
                    System.out.println("Equipo conectado: " + conexion.getNombreEquipo());
                    // Registrar equipo en la lista de usuarios




                    break;

                case "LISTA_USUARIOS":
                    MensajeListaUsuarios listaUsuarios = (MensajeListaUsuarios) mensaje;
                    System.out.println("Lista de usuarios solicitada.");
                    // Enviar lista de usuarios disponibles




                    break;

                case "PREPARADO":
                    MensajePreparado preparado = (MensajePreparado) mensaje;
                    System.out.println("Equipo preparado: " + preparado.getNombreEquipo());
                    // Notificar al cliente receptor
                    break;

                default:
                    System.out.println("Tipo de mensaje desconocido.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
