package JMR.AICHAT.SERVICE;


import JMR.AICHAT.CANCHAS.Cancha;
import JMR.AICHAT.CANCHAS.CanchaRepository;
import JMR.AICHAT.RESERVAS.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService {

    private static final int HORA_APERTURA = 8;
    private static final int HORA_CIERRE = 23;
    @Autowired
    private CanchaRepository canchaRepository;
    @Autowired
    private ReservaRepository reservaRepository;

    public Reserva reservarCancha(DatosReserva datos){
        if (datos.canchaId() == null) {
            throw new RuntimeException("el numero de la cancha es obligatorio para reservar");
        }

        Long id = datos.canchaId();
        Cancha cancha = canchaRepository
                .findById(datos.canchaId())
                .orElseThrow(() -> new RuntimeException("Cancha no encontrada para reservar"));
        boolean ocupada = reservaRepository
                .existsByCanchaAndFechaAndHora(cancha, datos.fecha(), datos.hora());

        if (ocupada) {
            throw new RuntimeException("Ese horario ya está ocupado");
        }

        var reserva = new Reserva();
        reserva.setFecha(datos.fecha());
        reserva.setHora(datos.hora());
        reserva.setNombreCliente(datos.nombreCliente());
        reserva.setTelefono(datos.telefono());
        reserva.setCancha(cancha);
        reservaRepository.save(reserva);

        return reserva;
    }

    public Reserva modificarReserva(DatosModificarReserva datos) {

        Reserva reserva = reservaRepository
                .findByTelefonoAndFechaAndHora(
                        datos.telefono(),
                        datos.fechaActual(),
                        datos.horaActual()
                )
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        Cancha cancha = canchaRepository
                .findById(datos.canchaId())
                .orElseThrow(() -> new RuntimeException("no se encontro reserva en esa ID"));

        boolean ocupada = reservaRepository
                .existsByCanchaAndFechaAndHora(
                        cancha,
                        datos.fechaNueva(),
                        datos.horaNueva()
                );

        if (ocupada) {
            throw new RuntimeException("Ese horario ya está ocupado");
        }

        reserva.setFecha(datos.fechaNueva());
        reserva.setHora(datos.horaNueva());
        reserva.setCancha(cancha);

        return reservaRepository.save(reserva);
    }

    public void EliminarReserva(DatosIdentificarReserva datos) {
        Reserva reserva = reservaRepository
                .findByTelefonoAndFechaAndHora(
                        datos.telefono(),
                        datos.fecha(),
                        datos.hora()
                )
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reservaRepository.delete(reserva);
    }

    public List<LocalTime> obtenerHorariosDisponibles(DatosDisponibilidad datos) {

        Cancha cancha = canchaRepository
                .findById(datos.canchaId())
                .orElseThrow(() -> new RuntimeException("Cancha no encontrada"));


        List<LocalTime> todosLosHorarios = new ArrayList<>();
        for (int h = HORA_APERTURA ; h <= HORA_CIERRE; h++) {
            todosLosHorarios.add(LocalTime.of(h, 0));
        }


        List<Reserva> reservas = reservaRepository
                .findByCanchaAndFecha(cancha, datos.fecha());

        List<LocalTime> horariosOcupados = reservas.stream()
                .map(Reserva::getHora)
                .toList();


        todosLosHorarios.removeAll(horariosOcupados);

        return todosLosHorarios;
    }

}


