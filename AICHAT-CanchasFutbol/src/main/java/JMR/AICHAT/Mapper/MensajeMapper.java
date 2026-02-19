package JMR.AICHAT.Mapper;

import JMR.AICHAT.DTOs.DatosFinalAI;
import JMR.AICHAT.DTOs.DatosMensaje;
import JMR.AICHAT.DTOs.Outputs.MensajeResponse;
import JMR.AICHAT.Mensaje.Mensaje;

import java.time.LocalDateTime;

public  class MensajeMapper {



    public static MensajeResponse toDTO(Mensaje mensaje) {
        var dto = new MensajeResponse(
                mensaje.getId(),
                mensaje.getTelefono(),
                mensaje.getMensajeUsuario(),
                mensaje.getRespuestaSistema(),
                mensaje.getIntencion(),
                mensaje.getFechaHora()
        );
        return dto;
    }

    public static Mensaje toEntity(MensajeResponse datos){
        var mensaje = new Mensaje();
        mensaje.setId(datos.id());
        mensaje.setTelefono(datos.telefono());
        mensaje.setMensajeUsuario(datos.mensajeUsuario());
        mensaje.setRespuestaSistema(datos.respuestaSistema());
        mensaje.setIntencion(datos.intencion());
        mensaje.setFechaHora(datos.fechayhora());
        return mensaje;

    }
    public static DatosMensaje toDatosMensaje(
            DatosFinalAI datosAI,
            String mensajeUsuario,
            String respuestaSistema) {

        return new DatosMensaje(
                datosAI.telefono(),
                mensajeUsuario,
                respuestaSistema,
                datosAI.intencion(),
                LocalDateTime.now()
        );
    }

    public static Mensaje toEntityByDatosMensaje(DatosMensaje datos) {
        var mensaje = new Mensaje();
        mensaje.setTelefono(datos.telefono());
        mensaje.setMensajeUsuario(datos.mensajeUsuario());
        mensaje.setRespuestaSistema(datos.respuestaSistema());
        mensaje.setIntencion(datos.intencion());
        mensaje.setFechaHora(datos.fechayhora());
        return mensaje;

    }
}
