package JMR.AICHAT.Cancha;

public class CanchaMapper {

    public static Cancha toEntity(DatosCanchaRequest datos){
        Cancha cancha = new Cancha();
        cancha.setNombre(datos.nombre());
        cancha.setTipo(datos.tipo());
        return cancha;
    }

    public static CanchaResponse toDTO(Cancha cancha){
        return new CanchaResponse(cancha.getId(),  cancha.getNombre(), cancha.getTipo());
    }
}
