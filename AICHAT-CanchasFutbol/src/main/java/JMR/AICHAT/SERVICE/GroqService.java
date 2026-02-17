package JMR.AICHAT.SERVICE;


import JMR.AICHAT.CANCHAS.Cancha;
import JMR.AICHAT.CANCHAS.CanchaRepository;
import JMR.AICHAT.InteligenciaArtificial.DatosModificarAI;
import JMR.AICHAT.InteligenciaArtificial.DatosReservaAI;
import JMR.AICHAT.InteligenciaArtificial.MensajeRequest;
import JMR.AICHAT.RESERVAS.*;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class GroqService {

    @Autowired
    private CanchaRepository canchaRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ReservaService reservaService;

    private  ChatClient chatClient;

    public GroqService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String InterpretarIntencion(MensajeRequest mensajeRequest) {
        String prompt= """
                 Sos un asistente de reservas de canchas de fútbol.
                
                    Detectá la intención del usuario y devolvé SOLO JSON.
                
                    Acciones posibles:
                    - CREAR
                    - CANCELAR
                    - MODIFICAR
                    - CONSULTAR
                    Formato JSON:
                       {
                         "Intencion": ""
                        }
                    
                    
                     Si un dato no está, devolver null.
                
                        Mensaje del usuario: """+mensajeRequest;


        String Intencion = chatClient.prompt()
                .user(prompt)
                .call()
                .content();
        return Intencion;


    }


    public String GestionarReserva(MensajeRequest mensajeRequest, String intencion) {

        switch (intencion) {
            case "CREAR":
                String prompt = """
                        Sos un asistente que extrae datos para reservas de canchas.
                        Devuelve SOLO JSON válido, sin texto adicional, sin explicaciones, sin comillas invertidas.
                                    Reglas:
                                        - Si dice "hoy", usar la fecha de hoy
                                        - Si dice "mañana", usar la fecha de mañana
                                        - Si dice un día de la semana (ej: jueves), calcular la próxima fecha
                                        - Si la hora es "9", interpretarla como 09:00
                                        - Si no especifica cancha, usar canchaId = 1
                                        - Formato de fecha: YYYY-MM-DD
                                        - Formato de hora: HH:mm
                        
                        Convertí el mensaje del usuario a JSON con este formato:
                        
                        {
                          "canchaId": number,
                          "fecha": "YYYY-MM-DD",
                          "hora": "HH:MM"
                        }
                        
                        Si falta un dato, dejalo null.
                        Respondé SOLO el JSON. Sin texto extra.
                        
                        Mensaje:
                        """ + mensajeRequest.mensaje();


                DatosReservaAI datosAI = chatClient.prompt()
                        .user(prompt)
                        .call()
                        .entity(DatosReservaAI.class);

                DatosReserva datos = new DatosReserva(
                        LocalDate.parse(datosAI.fecha()),
                        LocalTime.parse(datosAI.hora()),
                        datosAI.canchaId(),
                        mensajeRequest.nombre(),
                        mensajeRequest.telefono()
                );
                try {
                    Reserva reserva = reservaService.reservarCancha(datos);
                    return "Reserva creada correctamente.";
                } catch (Exception e) {
                    if (e.getMessage().contains("ocupado")) {
                        var datosdisponibles = new DatosDisponibilidad(datos.fecha(), datos.canchaId());
                        List<LocalTime> disponibles = reservaService.obtenerHorariosDisponibles(datosdisponibles);
                        return "Ese horario en esa cancha se encuentra ocupado, los horarios disponibles son: " + disponibles;
                    }
                    if (e.getMessage().contains("reservar")) {


                        return "numero de cancha no encontrado o no existe" ;
                    }

                }


            case "MODIFICAR":
                String prompt2 = """
                        Sos un asistente que extrae datos para modificar reservas.
                        
                        Si el usuario quiere cambiar un turno, devolvé SOLO JSON.
                        
                        Formato:
                        
                        {
                          "canchaId": number,
                          "fechaActual": "YYYY-MM-DD",
                          "horaActual": "HH:MM",
                          "fechaNueva": "YYYY-MM-DD",
                          "horaNueva": "HH:MM"
                        }
                        
                        Reglas:
                        - Si dice un día de la semana, calcular la próxima fecha
                        - Hora "9" → 09:00
                        - Si no menciona cancha → canchaId = 1
                        - Si falta algún dato → null
                        
                        Mensaje""" + mensajeRequest.mensaje();
                DatosModificarAI datosModificar = chatClient.prompt()
                        .user(prompt2)
                        .call()
                        .entity(DatosModificarAI.class);
                var datosModificados = new DatosModificarReserva(
                        mensajeRequest.telefono(),
                        LocalDate.parse(datosModificar.fechaActual()),
                        LocalTime.parse(datosModificar.horaActual()),
                        LocalDate.parse(datosModificar.fechaNueva()),
                        LocalTime.parse(datosModificar.horaNueva()),
                        datosModificar.canchaId(),
                        mensajeRequest.nombre()
                );
                Reserva reservamodificada = reservaService.modificarReserva(datosModificados);
                return "Reserva modificada correctamente.";


            case "CONSULTAR":
                var reservaConsultar = reservaRepository.findByTelefonoAndNombreCliente(mensajeRequest.telefono(),mensajeRequest.nombre());
                if (reservaConsultar == null) {
                    return "No se encontraron reservas";
                }

                return "Tenés una reserva el "
                        + reservaConsultar.getFecha()
                        + " a las "
                        + reservaConsultar.getHora();


            case "CANCELAR":
                var reservaCancelar = reservaRepository.findByTelefonoAndNombreCliente(mensajeRequest.telefono(),mensajeRequest.nombre());
                 var datosCancelar = new DatosIdentificarReserva(
                        reservaCancelar.getFecha(),
                        reservaCancelar.getHora(),
                        reservaCancelar.getTelefono()
                );
                reservaService.EliminarReserva(datosCancelar);
                return "Reserva cancelada";


            default: return "no entendi la solicitud";
        }
    }
}
