package excepciones;

public class ParticipanteDuplicadoException extends Exception {

    public ParticipanteDuplicadoException() {
        super("Error, participante duplicado");
    }

    public ParticipanteDuplicadoException(String mensaje) {
        super(mensaje);
    }

    public ParticipanteDuplicadoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
