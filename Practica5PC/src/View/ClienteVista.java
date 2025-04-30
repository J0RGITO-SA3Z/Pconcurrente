package View;

import Common.ConcurrentConsole;

import java.util.List;
import java.util.Map;

public class ClienteVista {

    private final ConcurrentConsole ccout;

    public ClienteVista(ConcurrentConsole console){
        this.ccout = console;
    }

    public void mostrarMenu() {
        ccout.print(ClienteMensajesConsola.MENU_PRINCIPAL);
    }

    public void conexion_cerrada(){
        ccout.print(ClienteMensajesConsola.SESION_CERRADA);
    }

    public void opcion_invalida(){
        ccout.print(ClienteMensajesConsola.OPCION_INVALIDA);
    }

    public void solicitud_conexion_enviada(){
        ccout.print(ClienteMensajesConsola.CONEXION_ENVIADA);
    }

    public void pedir_nombre_usuario(){
        ccout.print(ClienteMensajesConsola.PEDIR_NOMBRE);
    }

    public void error_puerto_servidor(String error){ccout.print(ClienteMensajesConsola.ERROR_CONEXION + error); }

    public void error_cerrar_recursos(String error){ ccout.print(ClienteMensajesConsola.ERROR_CERRAR_RECURSOS + error); }

    public void mensaje_desconocido(){
        ccout.print(ClienteMensajesConsola.MENSAJE_DESCONOCIDO);
    }

    public void equipo_preparado(String nombreEquipo){ ccout.print(ClienteMensajesConsola.EQUIPO_PREPARADO + nombreEquipo); }

    public void lista_informacion_solicitada(){
        ccout.print(ClienteMensajesConsola.LISTA_INFORMACION_SOLICITADA);
    }

    public void lista_informacion_recibida(){ ccout.print(ClienteMensajesConsola.LISTA_INFORMACION_RECIBIDA); }

    public void conexion_exitosa(){
        ccout.print(ClienteMensajesConsola.CONEXION_EXITOSA);
    }

    public void descarga_archivo_solicitada(String archivo) { ccout.print(ClienteMensajesConsola.DESCARGA_SOLICITADA + archivo); }

    public void mostrar_lista_informacion(Map<String, List<String>> map) { ccout.printmap(map); }

    // Nuevos métodos para gestión de listas
    public void mostrarMenuGestionListas() {
        ccout.print(ClienteMensajesConsola.GESTION_LISTAS_MENU);
    }

    public void listaCreadaExitosamente(String nombreLista) {
        ccout.print(ClienteMensajesConsola.LISTA_CREADA_EXITOSAMENTE + nombreLista);
    }

    public void listaYaExiste() {
        ccout.print(ClienteMensajesConsola.LISTA_YA_EXISTE);
    }

    public void listaEliminada(String nombreLista) {
        ccout.print(ClienteMensajesConsola.LISTA_ELIMINADA + nombreLista);
    }

    public void listaModificadaExitosamente() {
        ccout.print(ClienteMensajesConsola.LISTA_MODIFICADA_EXITOSAMENTE);
    }

    public void errorAlCrearLista() {
        ccout.print(ClienteMensajesConsola.ERROR_AL_CREAR_LISTA);
    }

    public void errorAlModificarLista() {
        ccout.print(ClienteMensajesConsola.ERROR_AL_MODIFICAR_LISTA);
    }

    public void errorAlEliminarLista() {
        ccout.print(ClienteMensajesConsola.ERROR_AL_ELIMINAR_LISTA);
    }

    public void noHayListasDisponibles() {
        ccout.print(ClienteMensajesConsola.NO_HAY_LISTAS_DISPONIBLES);
    }

    public void seleccionaListaParaVer() {
        ccout.print(ClienteMensajesConsola.SELECCIONA_LISTA_PARA_VER);
    }

    public void seleccionaListaParaModificar() {
        ccout.print(ClienteMensajesConsola.SELECCIONA_LISTA_PARA_MODIFICAR);
    }

    public void seleccionaListaParaEliminar() {
        ccout.print(ClienteMensajesConsola.SELECCIONA_LISTA_PARA_ELIMINAR);
    }

    public void contenidoListaActual(String contenido) {
        ccout.print(ClienteMensajesConsola.CONTENIDO_LISTA_ACTUAL + "\n" + contenido);
    }

    public void introduceNuevoContenido() {
        ccout.print(ClienteMensajesConsola.INTRODUCE_NUEVO_CONTENIDO);
    }

    public void accionCancelada() {
        ccout.print(ClienteMensajesConsola.ACCION_CANCELADA);
    }

    public void pedir_propietario_descarga() {
    }

    public void pedir_nombre_archivo() {
    }
}
