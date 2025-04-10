import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String id;
    private String direccionIP;
    private List<String> archivosCompartidos;

    public Usuario(String id, String direccionIP) {
        this.id = id;
        this.direccionIP = direccionIP;
        this.archivosCompartidos = new ArrayList<>();
    }
}