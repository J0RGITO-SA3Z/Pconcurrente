package Almacen;

import java.util.Random;

public class Producto {
    // Atributo para almacenar el ID del producto
    private int id;

    // Constructor que genera un ID aleatorio
    public Producto(int id_t) {
        this.id = id_t;
    }

    // Método toString para representar el objeto como String
    @Override
    public String toString() {
        return "Almacen.Producto{id='" + id + "'}";
    }

}
