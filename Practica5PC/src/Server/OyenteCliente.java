package Server;

import Common.*;
import java.io.*;
import java.net.*;

public class OyenteCliente implements Runnable {
    private Socket socketCliente;
    private ConcurrentConsole ccout;
    public OyenteCliente(Socket socketCliente, ConcurrentConsole out) {
        this.socketCliente = socketCliente;
        this.ccout = out;
    }

    @Override
    public void run() {
        try (
                ObjectInputStream entrada = new ObjectInputStream(socketCliente.getInputStream());
                ObjectOutputStream salida = new ObjectOutputStream(socketCliente.getOutputStream())
        ) {
            // Leer mensaje del cliente
            Mensaje mensaje = (Mensaje) entrada.readObject();
           ccout.print("Mensaje recibido: " + mensaje.getTipo());

            switch (mensaje.getTipo()) {
                case "CONEXION":
                    MensajeConexion conexion = (MensajeConexion) mensaje;
                    ccout.print("Equipo conectado: " + conexion.getNombre());
                    // Registrar equipo en la lista de usuarios
                    MensajeConfConexion confirmacion = new MensajeConfConexion(conexion.getNombre());
                    salida.writeObject(confirmacion);



                    break;

                case "LISTA_USUARIOS":
                    MensajeListaUsuarios listaUsuarios = (MensajeListaUsuarios) mensaje;
                    ccout.print("Lista de usuarios solicitada.");
                    // Enviar lista de usuarios disponibles




                    break;

                case "PREPARADO":
                    MensajePreparado preparado = (MensajePreparado) mensaje;
                    ccout.print("Equipo preparado: " + preparado.getNombreEquipo());
                    // Notificar al cliente receptor
                    break;

                default:
                    ccout.print("Tipo de mensaje desconocido.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
