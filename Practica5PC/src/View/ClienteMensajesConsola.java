package View;

public class ClienteMensajesConsola {
    // Constantes existentes...
    public static final String MENU_PRINCIPAL =
            "------------------ MENÚ PRINCIPAL -------------------\n" +
                    "1. Consultar la información disponible\n" +
                    "2. Descargar la información deseada\n" +
                    "3. Gestionar Listas\n" +
                    "4. Salir\n" +
                    "-----------------------------------------------------";
    public static final String SESION_CERRADA = "Sesión cerrada. ¡Hasta luego!";
    public static final String CONEXION_ENVIADA = "Conexión enviada al servidor.";
    public static final String PEDIR_NOMBRE = "Introduce tu nombre de usuario: ";
    public static final String ERROR_CONEXION = "Error al conectar con el servidor.";
    public static final String ERROR_CERRAR_RECURSOS = "Error al cerrar recursos: ";
    public static final String MENSAJE_DESCONOCIDO = "Tipo de mensaje desconocido.";
    public static final String LISTA_INFORMACION_SOLICITADA = "Lista de informacion solicitada.";
    public static final String LISTA_INFORMACION_RECIBIDA = "Lista de informacion recibida.";
    public static final String CONEXION_EXITOSA = "Conexión establecida con éxito.";
    public static final String OPCION_INVALIDA = "Opción inválida. Inténtalo de nuevo.";
    public static final String EQUIPO_PREPARADO = "Equipo preparado: ";
    public static final String DESCARGA_SOLICITADA = "Descarga Solicitada del archivo: ";

    // Nuevas constantes para gestión de listas
    public static final String GESTION_LISTAS_MENU =
            "------------------ GESTIÓN DE LISTAS -------------------\n" +
                    "1. Ver lista\n" +
                    "2. Crear lista\n" +
                    "3. Modificar lista\n" +
                    "4. Eliminar lista\n" +
                    "0. Cancelar\n" +
                    "-------------------------------------------------------";

    public static final String LISTA_CREADA_EXITOSAMENTE = "Lista creada exitosamente: ";
    public static final String LISTA_YA_EXISTE = "Ya existe una lista con ese nombre.";
    public static final String LISTA_ELIMINADA = "Lista eliminada: ";
    public static final String LISTA_MODIFICADA_EXITOSAMENTE = "Lista modificada exitosamente.";
    public static final String ERROR_AL_CREAR_LISTA = "Error al crear la lista.";
    public static final String ERROR_AL_MODIFICAR_LISTA = "Error al modificar la lista.";
    public static final String ERROR_AL_ELIMINAR_LISTA = "Error al eliminar la lista.";
    public static final String NO_HAY_LISTAS_DISPONIBLES = "No tienes listas disponibles.";
    public static final String SELECCIONA_LISTA_PARA_VER = "Selecciona el número de la lista que deseas ver: ";
    public static final String SELECCIONA_LISTA_PARA_MODIFICAR = "Selecciona el número de la lista que deseas modificar: ";
    public static final String SELECCIONA_LISTA_PARA_ELIMINAR = "Selecciona el número de la lista que deseas eliminar: ";
    public static final String CONTENIDO_LISTA_ACTUAL = "Contenido actual de la lista:";
    public static final String INTRODUCE_NUEVO_CONTENIDO = "Introduce el nuevo contenido de la lista (escribe 'FIN' en una línea nueva para terminar):";
    public static final String ACCION_CANCELADA = "Acción cancelada.";

    private ClienteMensajesConsola() {
        // Evita instanciación accidental
    }
}