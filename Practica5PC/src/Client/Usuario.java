package Client;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String nombre;


    public Usuario(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre;
    }
}
