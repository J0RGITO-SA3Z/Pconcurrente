package Server;

import java.io.Serializable;

public class Equipo implements Serializable {
    private String nombre;
    private int puntos;
    private String estado; // "DISPONIBLE", "EN_PARTIDA", "DESCONECTADO"
    private String direccionIP;

    public Equipo(String nombre, int puntos, String direccionIP) {
        this.nombre = nombre;
        this.puntos = puntos;
        this.estado = "DISPONIBLE"; // Por defecto, el equipo está disponible
        this.direccionIP = direccionIP;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDireccionIP() {
        return direccionIP;
    }

    public void setDireccionIP(String direccionIP) {
        this.direccionIP = direccionIP;
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "nombre='" + nombre + '\'' +
                ", puntos=" + puntos +
                ", estado='" + estado + '\'' +
                ", direccionIP='" + direccionIP + '\'' +
                '}';
    }
}
