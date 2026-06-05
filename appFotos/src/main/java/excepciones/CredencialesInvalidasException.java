package excepciones;

public class CredencialesInvalidasException extends Exception {

    public CredencialesInvalidasException() {

        super("credeciales no validas, inténtelo de nuevo");
    }

    public CredencialesInvalidasException(String mensaje) {

        super(mensaje);
    }

    public CredencialesInvalidasException(String mensaje, Throwable causa) {

        super(mensaje, causa);
    }
}
