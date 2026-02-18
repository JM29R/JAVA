package JMR.API.domain.Medico;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;


public interface MedicoRepository extends JpaRepository<Medico, Long> {


    Page<Medico> findAllByActivoTrue(Pageable paginacion);


    @Query(value = """
    SELECT * FROM medicos m
    WHERE m.activo = 1
    AND m.especialidad = :especialidad
    AND m.id NOT IN (
        SELECT c.medico_id FROM consultas c
        WHERE c.fecha = :fecha
    )
    ORDER BY RAND()
    LIMIT 1
""", nativeQuery = true)
    Medico ElegirMedicoDisponible(Especialidad especialidad,  LocalDateTime fecha);

    boolean findActivoById(Long idMedico);
}
