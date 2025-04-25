package Client;

import Common.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

            MensajeConexion mensajeConexion = new MensajeConexion(nombreEquipo, puntos);
            salida.writeObject(mensajeConexion);
            salida.flush();
            ccout.print("Conexión enviada al servidor.");

            Mensaje mensaje = (Mensaje) entrada.readObject();
            MensajeConfConexion confirmacion = (MensajeConfConexion) mensaje;
            ccout.print("Conexión establecida con exito. ");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
