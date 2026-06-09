package excepciones;

public class JuezException extends Exception {

    public JuezException() {
        super("Error, el juez ya existe");
    }

    public JuezException(String mensaje) {
        super(mensaje);
    }

    public JuezException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
