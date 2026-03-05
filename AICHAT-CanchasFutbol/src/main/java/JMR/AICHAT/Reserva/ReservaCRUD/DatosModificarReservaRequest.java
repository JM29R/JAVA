package JMR.AICHAT.Reserva.ReservaCRUD;


import java.time.LocalDate;
import java.time.LocalTime;

public record DatosModificarReservaRequest(
        LocalDate fecha,
        LocalTime hora,
        Long canchaId,
        String nombreCliente,
        String telefono
) {}