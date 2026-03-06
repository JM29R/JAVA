package JMR.AICHAT.Reserva.ReservaCRUD;


import JMR.AICHAT.Cancha.Cancha;
import JMR.AICHAT.Cancha.CanchaRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Service
public class ReservaService {

    private static final int HORA_APERTURA = 8;
    private static final int HORA_CIERRE = 23;

    private final CanchaRepository canchaRepository;

    private final ReservaRepository reservaRepository;

    //SE USA EN CRUD
    public List<LocalTime> obtenerHorariosDisponibles(DatosDisponibilidadRequest datos) {

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

    //SE USA EN CRUD
    public void eliminarPorId(Long id) {
        reservaRepository.deleteById(id);
    }
    //SE USA EN CRUD
    public Reserva modificarReservaID(Long id, DatosModificarReservaRequest datos) {

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe"));


        Cancha cancha = canchaRepository
                .findById(datos.canchaId())
                .orElseThrow(() -> new RuntimeException("no se encontro cancha en esa ID"));

        boolean ocupada = reservaRepository
                .existsByCanchaAndFechaAndHoraAndIdNot(
                        cancha,
                        datos.fecha(),
                        datos.hora(),
                        id
                );
        if (ocupada) {
            throw new RuntimeException("Ese horario ya está ocupado");
        }

        reserva.setFecha(datos.fecha());
        reserva.setHora(datos.hora());
        reserva.setCancha(cancha);
        reserva.setNombreCliente(datos.nombreCliente());
        reserva.setTelefono(datos.telefono());

        return reservaRepository.save(reserva);


    }



}


