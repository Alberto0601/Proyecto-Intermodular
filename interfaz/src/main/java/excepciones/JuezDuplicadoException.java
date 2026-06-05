package excepciones;

public class JuezDuplicadoException extends Exception {

    public JuezDuplicadoException() {
        super("Error, el juez ya existe");
    }

    public JuezDuplicadoException(String mensaje) {
        super(mensaje);
    }

    public JuezDuplicadoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
