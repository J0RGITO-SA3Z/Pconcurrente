package Server;

import Common.ConcurrentConsole;

import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor {
    private static ConcurrentConsole ccout = new ConcurrentConsole();
    private static List<Equipo> equiposRegistrados = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        ccout.print("Servidor iniciado. Esperando conexiones...");

        while (true) {
            Socket socketCliente = serverSocket.accept();
            ccout.print("Nuevo cliente conectado: " + socketCliente.getInetAddress());

            // Crear un nuevo hilo para manejar al cliente
            Thread hiloCliente = new Thread(new OyenteServidor(socketCliente, ccout));
            hiloCliente.start();
        }
    }
}
