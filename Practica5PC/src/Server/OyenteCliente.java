package Server;

import Common.*;
import Concurrent.*;
import View.ServidorVista;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.net.*;
import java.util.Stack;

public class OyenteCliente implements Runnable {
    private Socket socketCliente;
    private ServidorVista vista;
    private BufferPuertos buffer_sockets;
    private Map<String, Integer> usuarios; // Mapa compartido
    private Map<String, List<String>> informacion;
    private Stack<Integer> idsDisponibles;
    private Entero num_usuarios_conectados = new Entero();
    private Locks lock_map_usuarios;
    private Locks lock_map_informacion;
    private Locks lock_num_usuarios;
    private Locks lock_id;
    private int id_usuario;
    private int num_ids;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private boolean ejecutando;
    public OyenteCliente(Socket socketCliente, Map<String, Integer> datos, Map<String, List<String>> informacion, int id_usuario, int num_ids, ServidorVista out, Locks lock_map,
                         Locks lock_num_usuarios, Locks lock_informacion, Locks lock_id, BufferPuertos buffer_sockets, Entero num_usuarios_conectados, Stack<Integer> idsDisponibles) {
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
        this.lock_map_informacion = lock_informacion;
        this.lock_id = lock_id;
        this.idsDisponibles = idsDisponibles;
        this.id_usuario = id_usuario;
        this.num_ids = num_ids;
        this.informacion = informacion;
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

    private void enviarListaInformacion(){
        lock_map_informacion.takeLock(id_usuario);
        Map<String, List<String>> copia = new HashMap<String, List<String>>(informacion);
        lock_map_informacion.releaseLock(id_usuario);
        MensajeLista mensajeLista = new MensajeLista(copia);
        try {
            salida.writeObject(mensajeLista);
            salida.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Funcion que  libera los ids de los usuarios que se desconectan
    public void liberarId(int id) {
        if (id >= 0 && id < this.num_ids) {
            idsDisponibles.push(id); // Añade el ID al pool
            System.out.println("ID liberado: " + id);
        } else {
            throw new IllegalArgumentException("ID inválido: " + id);
        }
    }

    @Override
    public void run() {
        try {
            while (ejecutando) {
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
                        lock_map_usuarios.takeLock(id_usuario);
                        this.usuarios.put(conexion.getNombre(), puerto_asignado);
                        lock_map_usuarios.releaseLock(id_usuario);


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
                    case INFORMACION_CLIENTE:
                        MensajeInformacionCiente informacionCiente = (MensajeInformacionCiente) mensaje;
                        lock_map_informacion.takeLock(id_usuario);
                        informacion.put(informacionCiente.getNombre(), informacionCiente.getDatos());
                        lock_map_informacion.releaseLock(id_usuario);
                        // Enviar lista de usuarios disponibles


                        break;
                    case SOLICITUD_LISTA:
                        MensajeSolicitudLista solicitudLista = (MensajeSolicitudLista) mensaje;
                        vista.listaInformacionSolicitada();
                        // Enviar lista de información
                        enviarListaInformacion();

                        break;
                    case SOLICITUD_DESCARGA:
                        MensajeSolicitudDescarga descarga = (MensajeSolicitudDescarga) mensaje;
                        // Obtener los datos del mensaje
                        String usuarioSolicitante = descarga.getNombre();
                        String propietarioArchivo = descarga.getPropietario();
                        String nombreArchivo = descarga.getNombreArchivo();
                        // Verificar si el propietario existe en el mapa
                        if (!informacion.containsKey(propietarioArchivo)) {
                            vista.error_descarga();
                            break;
                        }
                        // Obtener la lista de archivos del propietario
                        List<String> archivosPropietario = informacion.get(propietarioArchivo);
                        // Verificar si el archivo solicitado existe en la lista del propietario
                        if (archivosPropietario == null || !archivosPropietario.contains(nombreArchivo)) {
                            vista.error_descarga(); //mandariamos excepción
                            break;
                        }



                        // Si tanto el usuario como el archivo existen, proceder con la descarga
                        vista.descarga_archivo_solicitada(nombreArchivo, propietarioArchivo);
                        // Enviar lista de usuarios disponibles


                        break;

                    case PREPARADO:
                        MensajePreparado preparado = (MensajePreparado) mensaje;
                        vista.usuario_preparado(preparado.getNombre());
                        // Notificar al cliente receptor
                        break;

                    case CERRAR_SESION:
                        MensajeCerrarSesion cierre = (MensajeCerrarSesion) mensaje;

                        lock_map_usuarios.takeLock(id_usuario);
                        this.usuarios.remove(cierre.getNombre());
                        lock_map_usuarios.releaseLock(id_usuario);

                        lock_num_usuarios.takeLock(id_usuario);
                        this.num_usuarios_conectados.decrement();
                        lock_num_usuarios.releaseLock(id_usuario);

                        lock_map_informacion.takeLock(id_usuario);
                        this.informacion.remove(cierre.getNombre());
                        lock_map_informacion.releaseLock(id_usuario);

                        lock_id.takeLock(0); // el 0 es para la interfaz pero no lo usa
                        liberarId(id_usuario);
                        lock_id.releaseLock(0);


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
            lock_num_usuarios.takeLock(this.id_usuario);
            this.num_usuarios_conectados.decrement();
            lock_num_usuarios.releaseLock(this.id_usuario);
            lock_id.takeLock(0); // el 0 es para la interfaz pero no lo usa
            liberarId(id_usuario);
            lock_id.releaseLock(0);
            ejecutando = false; // Detener el bucle
        } catch (Exception e) {
                e.printStackTrace();
        } finally {
            cerrarRecursos(); // Cerrar recursos
        }
    }
}
