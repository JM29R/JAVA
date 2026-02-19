package JMR.AICHAT.Mensaje;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    // Obtener historial de un teléfono específico (últimos 10 mensajes)
    List<Mensaje> findTop10ByTelefonoOrderByFechaHoraDesc(String telefono);

    // Obtener historial completo de un teléfono
    List<Mensaje> findByTelefonoOrderByFechaHoraAsc(String telefono);

    // Buscar mensajes por intención
    List<Mensaje> findByIntencion(String intencion);

    // Borrar historial de un teléfono (útil para testing)
    void deleteByTelefono(String telefono);
}
