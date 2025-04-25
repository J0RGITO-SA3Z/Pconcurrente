package Server;

import Client.Usuario;
import Common.ConcurrentConsole;

import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor {

    private final static int LIMITE_USUARIOS = 200;
    private static ConcurrentConsole ccout = new ConcurrentConsole();
    private static List<Usuario> equiposRegistrados = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        ccout.print("Servidor iniciado. Esperando conexiones...");

        while (true) {
            Socket socketCliente = serverSocket.accept();
            ccout.print("Nuevo cliente conectado: " + socketCliente.getInetAddress());

            // Crear un nuevo hilo para manejar al cliente
            Thread hiloCliente = new Thread(new OyenteCliente(socketCliente, ccout));
            hiloCliente.start();
        }
    }
}
