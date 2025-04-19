package Client;

import java.io.Serializable;

class EquipoRival implements Serializable {
    private String nombre;
    private int puntos;

    public EquipoRival(String nombre, int puntos) {
        this.nombre = nombre;
        this.puntos = puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    @Override
    public String toString() {
        return "Equipo: " + nombre + ", Puntos: " + puntos;
    }
}
