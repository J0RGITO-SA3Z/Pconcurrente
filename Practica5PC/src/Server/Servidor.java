package Server;

import Common.ConcurrentConsole;
import Concurrent.*;
import View.ServidorVista;
import java.util.HashMap;
import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor {
    protected final static int LIMITE_USUARIOS = 200;
    protected final static int PUERTO_SERVIDOR = 5000;
    protected final static int BUFFER_SOCKETS_CAPACITY = 5;
    private static Entero num_usuarios_conectados = new Entero();
    private static int num_ids = 0;
    private static BufferPuertos buffer_puertos;
    private static Map<String, Integer> usuarios_conectados = new HashMap<>();
    private static Map<String, List<String>> informacion = new HashMap<>();
    private static Locks lock_map_usuarios = new BakeryLock(LIMITE_USUARIOS);
    private static Locks lock_informacion = new BakeryLock(LIMITE_USUARIOS);
    private static Locks lock_num_usuarios = new LockTicket();
    private static Locks lock_id = new LockTicket(); // Solo puede ser LockTicket
    private static Stack<Integer> idsDisponibles = new Stack<>();

    // Funcion que asigna un id a los nuevos usuarios
    public static int asignarId() {
        if (!idsDisponibles.isEmpty()) {
            // Reutiliza un ID disponible del pool
            return idsDisponibles.pop();
        } else if (num_ids < LIMITE_USUARIOS) {
            // Asigna un nuevo ID si no se ha alcanzado el límite
            return num_ids++;
        } else {
            throw new IllegalStateException("Límite máximo de usuarios alcanzado.");
        }
    }

    public static void main(String[] args) throws IOException {
        // Creamos la consola y la vista para gestionar los mensajes de salida
        ConcurrentConsole ccout = new ConcurrentConsole();
        ServidorVista vista = new ServidorVista(ccout);
        vista.servidorIniciado();

        // Creamos el hilo encargado de escuchar activamente peticiones de conexión
        ServerSocket serverSocket = new ServerSocket(PUERTO_SERVIDOR);

        // Creamos el buffer que usamos para el productor-consumidor
        buffer_puertos = new BufferPuertos(BUFFER_SOCKETS_CAPACITY);

        // Creamos e iniciamos el productor
        Thread hiloProductor = new Thread(new Productor(0,buffer_puertos));
        hiloProductor.start();

        int id_a_asignar;

        while (true) {
            // Si el servidor ha alcanzado el límite de usuarios no acepta más
            if(num_usuarios_conectados.getNum() < LIMITE_USUARIOS) {
                // Acepta al usuario que quiera conectarse
                Socket socketCliente = serverSocket.accept();
                vista.nueva_conexion_cliente(socketCliente.getInetAddress());
                lock_id.takeLock(0); // el 0 es para la interfaz pero no lo usa
                id_a_asignar = asignarId();
                lock_id.releaseLock(0);
                lock_num_usuarios.takeLock(id_a_asignar);
                num_usuarios_conectados.increment();
                lock_num_usuarios.releaseLock(id_a_asignar);
                // Crear un nuevo hilo para manejar al cliente
                Thread hiloCliente = new Thread(new OyenteCliente(socketCliente, usuarios_conectados, informacion, id_a_asignar, num_ids,
                        vista, lock_map_usuarios, lock_num_usuarios,lock_informacion,lock_id, buffer_puertos, num_usuarios_conectados,
                        idsDisponibles));
                hiloCliente.start();
            }
        }
    }
}
