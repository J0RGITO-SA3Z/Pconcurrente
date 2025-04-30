package Client;

import Common.*;
import View.ClienteVista;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;


public class OyenteServidor implements Runnable {

    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private Usuario datos_cliente;
    private Socket socketCliente;
    private ClienteVista vista;
    private volatile boolean ejecutando;


    public OyenteServidor(Socket socketCliente,Usuario datos, ClienteVista out,ObjectOutputStream salida,ObjectInputStream entrada) {
        try {
            this.salida = salida;
            salida.flush();
            this.entrada = entrada;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.datos_cliente = datos;
        this.socketCliente = socketCliente;
        this.vista = out;
        this.ejecutando = true;
    }

    @Override
    public void run() {
        try{
            Mensaje mensaje = null;
            while(ejecutando){
                    mensaje = (Mensaje) entrada.readObject();
                    switch (mensaje.getTipo()) {
                        case LISTA_INFORMACION:
                            MensajeLista listaInformacion = (MensajeLista) mensaje;
                            vista.lista_informacion_recibida();
                            // Enviar lista de usuarios disponibles
                            vista.mostrar_lista_informacion(listaInformacion.getinformacion());

                            break;

                        case PREPARADO:
                            MensajePreparado preparado = (MensajePreparado) mensaje;
                            vista.equipo_preparado(preparado.getNombre());
                            // Notificar al cliente receptor
                            break;

                        case CONFIRMACION_CIERRE:
                            detener();
                            break;

                        default:
                            vista.mensaje_desconocido();
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
            vista.error_cerrar_recursos(e.getMessage());
        }
    }
}
