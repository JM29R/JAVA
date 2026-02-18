package JMR.AICHAT.Mapper;

import JMR.AICHAT.Cancha.Cancha;
import JMR.AICHAT.DTOs.Inputs.DatosReservaRequest;
import JMR.AICHAT.DTOs.Outputs.ReservaResponse;
import JMR.AICHAT.Reserva.Reserva;

public class ReservaMapper {

    public static Reserva toEntity(DatosReservaRequest dto, Cancha cancha) {
        Reserva reserva = new Reserva();
        reserva.setFecha(dto.fecha());
        reserva.setHora(dto.hora());
        reserva.setNombreCliente(dto.nombreCliente());
        reserva.setTelefono(dto.telefono());
        reserva.setCancha(cancha);
        return reserva;
    }
    public static ReservaResponse toResponse(Reserva reserva) {
        return new ReservaResponse(
                reserva.getId(),
                reserva.getFecha(),
                reserva.getHora(),
                reserva.getNombreCliente(),
                reserva.getTelefono(),
                reserva.getCancha().getId()
        );
    }
}


