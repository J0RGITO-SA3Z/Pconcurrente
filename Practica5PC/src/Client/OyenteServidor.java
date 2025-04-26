package Client;

import Common.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;


public class OyenteServidor implements Runnable {

    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private Usuario datos_cliente;
    private Socket socketCliente;
    private ConcurrentConsole ccout;

    private volatile boolean ejecutando;


    public OyenteServidor(Socket socketCliente,Usuario datos, ConcurrentConsole out,ObjectOutputStream salida,ObjectInputStream entrada) {
        try {
            this.salida = salida;
            salida.flush();
            this.entrada = entrada;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.datos_cliente = datos;
        this.socketCliente = socketCliente;
        this.ccout = out;
        this.ejecutando = true;
    }

    @Override
    public void run() {
        try{
            // Confirmación del servidor
            Mensaje mensaje = (Mensaje) entrada.readObject();
            MensajeConfConexion confirmacion = (MensajeConfConexion) mensaje;
            ccout.print("Conexión establecida con exito. ");

            while(ejecutando){
                    mensaje = (Mensaje) entrada.readObject();
                    switch (mensaje.getTipo()) {
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

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            //MANDAR MENSAJE DE DESCONEXION AL SERVIDOR
            cerrarRecursos();

        }
    }

    public void detener() {
        ejecutando = false; // Cambia la bandera para detener el hilo
    }

    private void cerrarRecursos() {
        try {
            if (socketCliente != null) socketCliente.close();
        } catch (IOException e) {
            ccout.print("Error al cerrar recursos: " + e.getMessage());
        }
    }
}
