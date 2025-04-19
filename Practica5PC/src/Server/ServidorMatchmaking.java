package Server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorMatchmaking {
    private static List<String> equiposRegistrados = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Servidor iniciado. Esperando conexiones...");

        while (true) {
            Socket socketCliente = serverSocket.accept();
            System.out.println("Nuevo cliente conectado: " + socketCliente.getInetAddress());

            // Crear un nuevo hilo para manejar al cliente
            Thread hiloCliente = new Thread(new ManejadorCliente(socketCliente));
            hiloCliente.start();
        }
    }
}
