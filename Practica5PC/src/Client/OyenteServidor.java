package Client;

import Common.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class OyenteServidor implements Runnable {

    private Usuario datos_cliente;
    private Socket socketCliente;
    private ConcurrentConsole ccout;
    public OyenteServidor(Socket socketCliente,Usuario datos, ConcurrentConsole out) {
        this.datos_cliente = datos;
        this.socketCliente = socketCliente;
        this.ccout = out;
    }

    @Override
    public void run() {
        try (
                ObjectInputStream entrada = new ObjectInputStream(socketCliente.getInputStream());
                ObjectOutputStream salida = new ObjectOutputStream(socketCliente.getOutputStream())
        ) {

            MensajeConexion mensajeConexion = new MensajeConexion(datos_cliente.getNombre());
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
