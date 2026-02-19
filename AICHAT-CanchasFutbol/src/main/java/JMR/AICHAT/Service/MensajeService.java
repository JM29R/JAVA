package JMR.AICHAT.Service;

import JMR.AICHAT.DTOs.DatosFinalAI;
import JMR.AICHAT.DTOs.DatosMensaje;
import JMR.AICHAT.DTOs.Inputs.MensajeRequest;
import JMR.AICHAT.Mensaje.Mensaje;
import JMR.AICHAT.Mensaje.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MensajeService {
    @Autowired
    private MensajeRepository mensajeRepository;

    //Guardar

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
