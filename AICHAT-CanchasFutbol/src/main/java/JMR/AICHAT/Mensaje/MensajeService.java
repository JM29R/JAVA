package JMR.AICHAT.Mensaje;

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
