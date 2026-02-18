package JMR.API.domain;

public class ValidacionExcepcion extends RuntimeException {
    public ValidacionExcepcion(String mensaje) {
        super(mensaje);
    }
}
