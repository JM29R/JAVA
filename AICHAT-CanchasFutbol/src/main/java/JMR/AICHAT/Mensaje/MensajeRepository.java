package JMR.AICHAT.Mensaje;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    // ultimos 10
    List<Mensaje> findTop10ByTelefonoOrderByFechaHoraDesc(String telefono);

    //todos de un telefono
    List<Mensaje> findByTelefonoOrderByFechaHoraAsc(String telefono);

    // Buscar mensajes por intención
    List<Mensaje> findByIntencion(String intencion);

    // Borrar historial de un teléfono
    void deleteByTelefono(String telefono);
}
