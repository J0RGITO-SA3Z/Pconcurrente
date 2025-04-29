package Client;


import java.io.*;
import java.net.*;
import java.util.*;
import Common.*;
import View.ClienteVista;
import java.util.Scanner;

public class Cliente {

    private final static int PUERTO_SERVIDOR = 5000;
    private final static String IP = "localhost"; //SI FUERA OTRA CAMBIAR AQUÍ DE MOMENTO
    private Usuario datos_cliente;
    private Scanner scanner;
    private Socket socket;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private ConcurrentConsole ccout;
    private ClienteVista vista;
    private Boolean salir;
    private int puerto_P2P;
    private ServerSocket socketP2P;
    private GestionListas listas;

    public Cliente(String host, int puerto) throws IOException {
        this.socket = new Socket(host, puerto);
        this.salida = new ObjectOutputStream(socket.getOutputStream());
        this.salida.flush();
        this.entrada = new ObjectInputStream(socket.getInputStream());
        this.ccout = new ConcurrentConsole();
        this.vista = new ClienteVista(this.ccout);
        this.salir = false;
    }

    public void iniciar() {
        try {
            scanner = new Scanner(System.in);

            //Pedimos los datos al cliente
            vista.pedir_nombre_usuario();
            String nombre = scanner.nextLine();
            this.datos_cliente = new Usuario(nombre);

            // Enviar solicitud de conexión
            Mensaje mensaje = new MensajeConexion(datos_cliente.getNombre());
            salida.writeObject(mensaje);
            salida.flush();
            vista.solicitud_conexion_enviada();

            // Confirmamos la conexión con el servidor
            mensaje = (Mensaje) entrada.readObject();
            MensajeConfConexion confirmacion = (MensajeConfConexion) mensaje;

            // Leemos el puerto que nos ha asignado el servidor
            puerto_P2P = confirmacion.getPuerto();

            // Tratamos de crear un server socket con ese puerto
            while (true) {
                try {
                    socketP2P = new ServerSocket(puerto_P2P);
                    // Si llegamos aquí, el bind ha sido exitoso
                    System.out.println("ServerSocket P2P escuchando en el puerto " + puerto_P2P);
                    break;
                } catch (BindException be) {
                    // El puerto ya está en uso
                    System.err.println("Puerto " + puerto_P2P + " no disponible. Por favor, elige otro.");
                    mensaje = new MensajePuertoNoDisponible();
                    salida.writeObject(mensaje);
                    salida.flush();
                    mensaje = (Mensaje) entrada.readObject();
                    MensajeAsignarNuevoPuerto nuevoPuerto = (MensajeAsignarNuevoPuerto)mensaje;
                    puerto_P2P = nuevoPuerto.getPuerto();
                } catch (Exception e) {
                    // Cualquier otro error al crear el socket
                    System.err.println("Error al crear ServerSocket en puerto " + puerto_P2P + ": " + e.getMessage());
                    puerto_P2P = confirmacion.getPuerto();
                }
            }

            // Confirmamos que se ha establecido la conexión con el servidor y que se ha creado el server socket en un puerto permitido
            vista.conexion_exitosa();
            // Fin de la confirmación

            //Creamos el gestor de las listas
            listas = new GestionListas(datos_cliente.getNombre());

            Thread hiloCliente = new Thread(new OyenteServidor(socket,datos_cliente, vista,salida,entrada));
            hiloCliente.start();

            while (!salir) {
                vista.mostrarMenu();
                String opcion = scanner.nextLine();
                switch (opcion) {
                    case "1":
                        opcion_solicitarListas();
                        break;
                    case "2":
                        opcion_descargar();
                        break;
                    case "3":
                        opcion_ModificarListas();
                        break;
                    case "4":
                        opcion_salir();
                        break;
                    default:
                        vista.opcion_invalida();
                }
            }
            hiloCliente.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void opcion_salir() {
        salir = true;
        vista.conexion_cerrada();
        try {
            MensajeCerrarSesion cierre = new MensajeCerrarSesion(datos_cliente.getNombre());
            salida.writeObject(cierre);
            salida.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void opcion_solicitarListas(){
        try {
            MensajeSolicitudLista lista = new MensajeSolicitudLista();
            salida.writeObject(lista);
            salida.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        vista.lista_usuarios_solicitada();
    }

    private void opcion_descargar(){
        String archivo = scanner.nextLine();
        try {
            MensajeSolicitudDescarga lista = new MensajeSolicitudDescarga(datos_cliente.getNombre(),archivo);
            salida.writeObject(lista);
            salida.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        vista.descarga_archivo_solicitada(archivo);
    }

    private void opcion_ModificarListas(){
        listas.gestionarListas();
    }

    public static void main(String[] args) {
        try {
            Cliente cliente = new Cliente(IP, PUERTO_SERVIDOR);
            cliente.iniciar();
        } catch (IOException e) {
            System.err.println("Error al conectar con el servidor: " + e.getMessage());
        }
    }
}