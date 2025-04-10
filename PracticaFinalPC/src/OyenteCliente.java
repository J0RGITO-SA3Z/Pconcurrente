import Mensajes.Mensaje;
import Mensajes.MensajeRespuesta;
import Mensajes.MensajeSolicitud;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class OyenteCliente implements Runnable {
    private final Socket socket;
    private final Servidor servidor;

    public OyenteCliente(Socket socket, Servidor servidor) {
        this.socket = socket;
        this.servidor = servidor;
    }

    @Override
    public void run() {
        try (ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream())) {

            Mensaje mensaje = (Mensaje) entrada.readObject();
            if (mensaje.getTipo() == 1) { // Solicitud de fichero
                MensajeSolicitud solicitud = (MensajeSolicitud) mensaje;
                String contenido = leerFichero(solicitud.getNombreFichero());
                salida.writeObject(new MensajeRespuesta(contenido));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String leerFichero(String nombre) {
        // Lógica para leer el fichero (ejemplo simplificado)
        return "Contenido de " + nombre;
    }
}
