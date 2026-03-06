package JMR.AICHAT.Mensaje;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MensajeService {

    private final MensajeRepository mensajeRepository;

    public MensajeService(MensajeRepository mensajeRepository) {
        this.mensajeRepository = mensajeRepository;
    }


    public void guardarMensaje(DatosMensaje datos) {
        var mensaje = new Mensaje();
        mensaje.setTelefono(datos.telefono());
        mensaje.setMensajeUsuario(datos.mensajeUsuario());
        mensaje.setRespuestaSistema(datos.respuestaSistema());
        mensaje.setIntencion(datos.intencion());
        mensaje.setFechaHora(datos.fechayhora());
        mensajeRepository.save(mensaje);
    }

}
