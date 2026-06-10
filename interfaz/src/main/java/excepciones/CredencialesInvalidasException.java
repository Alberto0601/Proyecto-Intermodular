package excepciones;

public class CredencialesInvalidasException extends Exception {
    /**
     * Construye una nueva excepción con un mensaje por defecto:
     * "Credenciales no válidas, inténtelo de nuevo".
     */
    public CredencialesInvalidasException() {

        super("Credenciales no válidas, inténtelo de nuevo");
    }

    /**
     * Construye una nueva excepción con un mensaje de error personalizado.
     * El detalle del mensaje de error que explica la causa específica.
     * @param mensaje
     */
    public CredencialesInvalidasException(String mensaje) {

        super(mensaje);
    }
    /**
     * Construye una nueva excepción con un mensaje de error personalizado y la
     * causa original que provocó este fallo (útil para el encadenamiento de excepciones).
     *
     * @param mensaje
     * @param causa   Otra excepción que provocó el fallo de credenciales.
     */
    public CredencialesInvalidasException(String mensaje, Throwable causa) {

        super(mensaje, causa);
    }
}
