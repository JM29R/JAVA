package JMR.AICHAT.SERVICE;

import JMR.AICHAT.CANCHAS.Cancha;
import JMR.AICHAT.CANCHAS.CanchaRepository;
import JMR.AICHAT.CANCHAS.DatosCancha;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CanchaService {

    @Autowired
    private CanchaRepository canchaRepository;

    public Cancha crearCancha(DatosCancha datos) {
        Cancha cancha = new Cancha();
        cancha.setNombre(datos.nombre());
        cancha.setTipo(datos.tipo());

        return canchaRepository.save(cancha);
    }

    public Cancha actualizarCancha(Long id, DatosCancha datos) {
        Cancha cancha = canchaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cancha no encontrada"));

        cancha.setNombre(datos.nombre());
        cancha.setTipo(datos.tipo());

        return canchaRepository.save(cancha);
    }


}
