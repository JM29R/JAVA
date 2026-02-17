package JMR.AICHAT.RESERVAS;


import JMR.AICHAT.CANCHAS.Cancha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    Optional<Reserva> findByTelefonoAndFechaAndHora(
            String telefono,
            LocalDate fecha,
            LocalTime hora
    );
    boolean existsByCanchaAndFechaAndHora(
            Cancha cancha,
            LocalDate fecha,
            LocalTime hora
    );

    List<Reserva> findByCanchaIdAndFecha(Long canchaId, LocalDate fecha);

    List<Reserva> findByCanchaAndFecha(Cancha cancha, LocalDate fecha);

    Reserva findByTelefonoAndNombreCliente(String telefono, String nombre);
}
