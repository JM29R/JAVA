package JMR.AICHAT.Cancha;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CanchaService {

    private final CanchaRepository canchaRepository;
    public CanchaService(CanchaRepository canchaRepository) {
        this.canchaRepository = canchaRepository;
    }

    public Cancha crearCancha(DatosCanchaRequest datos) {
        Cancha cancha = new Cancha();
        cancha.setNombre(datos.nombre());
        cancha.setTipo(datos.tipo());

        return canchaRepository.save(cancha);
    }

    public Cancha actualizarCancha(Long id, DatosCanchaRequest datos) {
        Cancha cancha = canchaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cancha no encontrada"));

        cancha.setNombre(datos.nombre());
        cancha.setTipo(datos.tipo());

        return canchaRepository.save(cancha);
    }

    public List<Cancha> listarCanchas() {
        return canchaRepository.findAll();
    }


}
