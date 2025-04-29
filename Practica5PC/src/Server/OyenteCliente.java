package Server;

import Common.*;
import Concurrent.BakeryLock;
import Concurrent.BufferPuertos;
import Concurrent.Entero;
import Concurrent.LockRompeEmpate;
import View.ServidorVista;
import java.io.*;
import java.util.Map;
import java.net.*;

public class OyenteCliente implements Runnable {
    private Socket socketCliente;
    private ServidorVista vista;
    private BufferPuertos buffer_sockets;
    private Map<String, Integer> usuarios; // Mapa compartido
    private Map<String, String> productos_usuarios;
    private Entero num_usuarios_conectados = new Entero();
    private BakeryLock lock_map_usuarios;
    private BakeryLock lock_map_productos;
    private LockRompeEmpate lock_num_usuarios;
    private LockRompeEmpate lock_log;
    private int num_usuario;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private boolean ejecutando;

    public OyenteCliente(Socket socketCliente, Map<String, Integer> datos, Map<String, String> productos, int num_usuario, ServidorVista out, BakeryLock lock_map,
                         LockRompeEmpate lock_num_usuarios, BufferPuertos buffer_sockets, Entero num_usuarios_conectados) {
        try {
            salida = new ObjectOutputStream(socketCliente.getOutputStream());
            salida.flush();
            entrada = new ObjectInputStream(socketCliente.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.socketCliente = socketCliente;
        this.vista = out;
        this.usuarios = datos;
        this.lock_map_usuarios = lock_map;
        this.lock_num_usuarios = lock_num_usuarios;
        this.num_usuario = num_usuario;
        this.productos_usuarios = productos;
        this.buffer_sockets = buffer_sockets;
        this.num_usuarios_conectados = num_usuarios_conectados;
        this.ejecutando = true;
    }

    private void cerrarRecursos() {
        try {
            if (entrada != null) entrada.close();
            if (salida != null) salida.close();
            if (socketCliente != null) socketCliente.close();
        } catch (IOException e) {
            vista.error_cerrar_recursos(e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while (ejecutando) {
                System.out.println("RUN OYENTECLIENTE");
                // Leer mensaje del cliente
                Mensaje mensaje = (Mensaje) entrada.readObject();
                vista.mensajeRecibido(mensaje.getTipo().toString());
                int puerto_asignado;
                switch (mensaje.getTipo()) {
                    case CONEXION:
                        MensajeConexion conexion = (MensajeConexion) mensaje;
                        vista.equipoConectado(conexion.getNombre());
                        puerto_asignado = buffer_sockets.extraer();
                        // Registrar equipo en la lista de usuarios
                        // Mandar mensaje de confirmación
                        lock_map_usuarios.takeLock(this.num_usuario);
                        this.usuarios.put(conexion.getNombre(), puerto_asignado);
                        lock_map_usuarios.releaseLock(this.num_usuario);


                        MensajeConfConexion confirmacion = new MensajeConfConexion(conexion.getNombre(), puerto_asignado);
                        salida.writeObject(confirmacion);
                        salida.flush();

                        break;
                    case PUERTO_P2P_NO_DISPONIBLE:

                        puerto_asignado = buffer_sockets.extraer();
                        mensaje = new MensajeAsignarNuevoPuerto(puerto_asignado);
                        salida.writeObject(mensaje);
                        salida.flush();

                        break;
                    case LISTA_USUARIOS:
                        MensajeSolicitudLista listaProductos = (MensajeSolicitudLista) mensaje;

                        vista.listaUsuariosSolicitada();
                        // Enviar lista de usuarios disponibles

                        break;
                    case SOLICITUD_DESCARGA:
                        MensajeSolicitudDescarga descarga = (MensajeSolicitudDescarga) mensaje;
                        vista.descarga_archivo_solicitada(descarga.getNombreArchivo(), descarga.getNombre());
                        // Enviar lista de usuarios disponibles


                        break;

                    case PREPARADO:
                        MensajePreparado preparado = (MensajePreparado) mensaje;
                        vista.usuario_preparado(preparado.getNombre());
                        // Notificar al cliente receptor
                        break;

                    case CERRAR_SESION:
                        MensajeCerrarSesion cierre = (MensajeCerrarSesion) mensaje;

                        lock_map_usuarios.takeLock(this.num_usuario);
                        this.usuarios.remove(cierre.getNombre());
                        lock_map_usuarios.releaseLock(this.num_usuario);
                        /*
                        lock_num_usuarios.takeLock(this.num_usuario);
                        this.num_usuarios_conectados.decrement();
                        lock_num_usuarios.releaseLock(this.num_usuario);
                        */


                        mensaje = new MensajeConfCierre();
                        salida.writeObject(mensaje);
                        salida.flush();
                        ejecutando = false;
                        vista.cierre_conexion(cierre.getNombre());
                        break;

                    default:
                        vista.tipoMensajeDesconocido();
                }
            }
        } catch (EOFException | SocketException e) {
            vista.cliente_desconectado_abruptamente();
            lock_num_usuarios.takeLock(this.num_usuario);
            this.num_usuarios_conectados.decrement();
            lock_num_usuarios.releaseLock(this.num_usuario);
            ejecutando = false; // Detener el bucle
        } catch (Exception e) {
                e.printStackTrace();
        } finally {
            cerrarRecursos(); // Cerrar recursos
        }
    }
}
