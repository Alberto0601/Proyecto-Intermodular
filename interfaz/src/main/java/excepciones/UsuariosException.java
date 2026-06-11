package excepciones;

public class UsuariosException extends Exception {
    /**
     * Construye una nueva excepción con un mensaje por defecto:
     * "Error, usuario no insertado".
     */
    public UsuariosException() {
        super("Error, usuario no insertado");
    }
    /**
     * Construye una nueva excepción con un mensaje de error personalizado.
     * @param mensaje
     */
    public UsuariosException(String mensaje) {
        super(mensaje);
    }
    /**
     * Construye una nueva excepción con un mensaje de error personalizado
     * y la causa original del fallo
     * @param mensaje
     * @param causa Otra excepción que provocó el fallo de credenciales.
     */
    public UsuariosException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
