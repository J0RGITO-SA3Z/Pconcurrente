package Common;

public class MensajePuertoDescarga extends Mensaje{

    String puerto;
    String ip_destino;

    public MensajePuertoDescarga(String tipo) {
        super(tipo);
    }

    public String getIpDestino() {
        return ip_destino;
    }

    public String getPuerto() {
        return puerto;
    }
}
