package excepciones;

public class UsuariosException extends Exception {

    public UsuariosException() {
        super("Error, usuario no insertado");
    }

    public UsuariosException(String mensaje) {
        super(mensaje);
    }

    public UsuariosException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
