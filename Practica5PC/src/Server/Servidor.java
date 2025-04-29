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
    private static int id_usuario = 0;
    private static BufferPuertos buffer_puertos;
    private static Map<String, Integer> usuarios_conectados = new HashMap<>();
    private static Map<String, String> productos_usuarios = new HashMap<>();
    private static BakeryLock lock_map_usuarios = new BakeryLock(LIMITE_USUARIOS);
    private static BakeryLock lock_map_productos = new BakeryLock(LIMITE_USUARIOS);
    private static LockRompeEmpate lock_num_usuarios = new LockRompeEmpate(LIMITE_USUARIOS);

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

        while (true) {
            // Si el servidor ha alcanzado el límite de usuarios no acepta más
            if(num_usuarios_conectados.getNum() < LIMITE_USUARIOS) {
                // Acepta al usuario que quiera conectarse
                Socket socketCliente = serverSocket.accept();
                vista.nueva_conexion_cliente(socketCliente.getInetAddress());
                /*lock_num_usuarios.takeLock(id_usuario);
                num_usuarios_conectados.increment();
                lock_num_usuarios.releaseLock(id_usuario);*/
                id_usuario++;
                // Crear un nuevo hilo para manejar al cliente
                Thread hiloCliente = new Thread(new OyenteCliente(socketCliente, usuarios_conectados, productos_usuarios, id_usuario,
                        vista, lock_map_usuarios, lock_num_usuarios, buffer_puertos, num_usuarios_conectados));
                hiloCliente.start();
            }
        }
    }
}
