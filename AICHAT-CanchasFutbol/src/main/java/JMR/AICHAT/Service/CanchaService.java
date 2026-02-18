package JMR.AICHAT.Service;

import JMR.AICHAT.Cancha.Cancha;
import JMR.AICHAT.Cancha.CanchaRepository;
import JMR.AICHAT.DTOs.Inputs.DatosCanchaRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class CanchaService {

    @Autowired
    private CanchaRepository canchaRepository;

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


}
