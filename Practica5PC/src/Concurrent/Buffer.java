package Concurrent;

public interface Buffer {
    /**
     * Almacena (como ultimo) un producto en el almac´en. Si no hay
     * hueco el proceso que ejecute el m´etodo bloquear´a hasta que lo
     * haya.
     */
    public void almacenar(int producto);
    /**
     * Extrae el primer producto disponible. Si no hay productos el
     * proceso que ejecute el m´etodo bloquear´a hasta que se almacene un
     * dato.
     */
    public int extraer();
}
