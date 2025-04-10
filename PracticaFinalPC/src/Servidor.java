import LocksI.LockTicket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

public class Servidor {
    private ServerSocket serverSocket;
    private final Map<String, Usuario> usuariosConectados = new ConcurrentHashMap<>();
    private final Lock lockUsuarios = new LockTicket(true); // Lock justo (Bakery/Ticket)

    public void iniciar(int puerto) {
        try {
            serverSocket = new ServerSocket(puerto);
            while (true) {
                Socket socketCliente = serverSocket.accept();
                OyenteCliente oyente = new OyenteCliente(socketCliente, this);
                new Thread(oyente).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Métodos sincronizados para gestionar usuarios
    public void agregarUsuario(String id, Socket socket) {
        lockUsuarios.lock();
        try {
            usuariosConectados.put(id, new Usuario(id, socket.getInetAddress().toString()));
        } finally {
            lockUsuarios.unlock();
        }
    }
}
