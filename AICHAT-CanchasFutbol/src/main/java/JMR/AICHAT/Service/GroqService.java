package JMR.AICHAT.Service;


import JMR.AICHAT.Cancha.Cancha;
import JMR.AICHAT.Cancha.CanchaRepository;
import JMR.AICHAT.DTOs.*;
import JMR.AICHAT.DTOs.Inputs.*;
import JMR.AICHAT.Reserva.*;
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

    private String limpiarIntencion(String raw) {
        if (raw == null) return "";
        String texto = raw.trim().toUpperCase();
        if (texto.contains("DISPONIBILIDAD")) {
            return "DISPONIBILIDAD";
        }
        if (texto.contains("CREAR")) return "CREAR";
        if (texto.contains("MODIFICAR")) return "MODIFICAR";
        if (texto.contains("CANCELAR")) return "CANCELAR";
        if (texto.contains("CONSULTAR")) return "CONSULTAR";
        System.out.println(texto);
        return texto;
    }

    private String GestionarRespuesta(String contexto) {
        String prompt = """
Sos el asistente virtual de reservas de canchas de fútbol.

Respondé:
- en español
- en minúsculas
- de forma amigable y natural
- máximo 2 oraciones

Reglas:
- Usá solo la información del contexto.
- No inventes reservas ni acciones.
- No hagas preguntas genéricas como "¿en qué puedo ayudarte?".
- No agregues información que no esté en el contexto.

Interpretación del contexto:

Si comienza con:
TIPO: DISPONIBILIDAD
→ mostrar los horarios y ofrecer reservar uno.

TIPO: SIN_DISPONIBILIDAD
→ decir que no hay turnos ese día.

TIPO: RESERVA_CREADA
→ confirmar la reserva.

TIPO: RESERVA_CANCELADA
→ confirmar cancelación.

TIPO: ERROR
→ disculparse y pedir intentar nuevamente.

Ejemplo para disponibilidad:
"tengo disponibles 18:00, 19:00 y 20:00. ¿querés que reserve alguno?"

Contexto:
""" + contexto + """

Generá solo el mensaje para el cliente.
""";
        System.out.println(contexto);

        String json = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        String respuesta = limpiarIntencion(json);
        return respuesta.trim().toLowerCase();
    }

    public DatosFinalAI InterpretarIntencion(MensajeRequest mensajeRequest) {
        String prompt = """
                Sos un analizador de mensajes para un sistema de reservas de canchas de fútbol.
                
                Tu tarea:
                Analizar el mensaje del usuario y devolver SOLO un JSON válido con:
                - intencion
                - fecha
                - hora
                - canchaId
                
                El sistema ya maneja el nombre y teléfono del cliente. No los incluyas.
                
                Intenciones posibles (solo una):
                CREAR
                CANCELAR
                MODIFICAR
                CONSULTAR
                DISPONIBILIDAD
                
                Reglas para la intención:
                
                - Si pregunta por horarios o disponibilidad → DISPONIBILIDAD
                - Si quiere reservar o sacar turno:
                    - Si menciona cancha → CREAR
                    - Si NO menciona cancha → DISPONIBILIDAD
                - Si quiere cambiar fecha u hora → MODIFICAR
                - Si quiere cancelar una reserva → CANCELAR
                - Si pregunta qué reserva tiene → CONSULTAR
                
                Reglas de fecha:
                
                - "hoy" → fecha de hoy
                - "mañana" → fecha de mañana
                - Día de la semana → próxima fecha de ese día
                - Formato: YYYY-MM-DD
                - Si no menciona fecha → null
                
                Reglas de hora:
                
                - Si dice "9" → 09:00
                - Formato: HH:mm
                - Si no menciona hora → null
                
                Reglas de cancha:
                
                - Si menciona número de cancha → usar ese número
                - Si NO menciona cancha → null
                - Nunca asumir una cancha por defecto
                
                IMPORTANTE:
                
                - Respondé SOLO JSON
                - No agregues texto ni explicaciones
                - No agregues campos extra
                - No uses listas ni corchetes []
                - Si un dato no está → null
                - No inventes información
                
                Formato exacto:
                
                {
                  "intencion": "DISPONIBILIDAD",
                  "fecha": "YYYY-MM-DD",
                  "hora": "HH:mm",
                  "canchaId": number
                }
                
                Mensaje del usuario:
                
                """ + mensajeRequest.mensaje();
        AnalisisAI respuesta = chatClient.prompt()
                .user(prompt)
                .call()
                .entity(AnalisisAI.class);
        LocalDate fecha = null;
        LocalTime hora = null;
        if (respuesta.fecha() != null && !respuesta.fecha().isBlank()) {
            try {
                fecha = LocalDate.parse(respuesta.fecha());
            } catch (Exception e) {
                fecha = null;
            }
        }
        if (respuesta.hora() != null && !respuesta.hora().isBlank()) {
            try {
                hora = LocalTime.parse(respuesta.hora());
            } catch (Exception e) {
                hora = null;
            }
        }
        String intencion = limpiarIntencion(respuesta.intencion());
        Integer canchaId = respuesta.canchaId() != null ? respuesta.canchaId() : -1;

        if ("CREAR".equals(intencion) && canchaId == -1) {
            intencion = "DISPONIBILIDAD";
        }



        DatosFinalAI datos = new DatosFinalAI(
                intencion,
                fecha,
                hora,
                canchaId,
                mensajeRequest.nombre(),
                mensajeRequest.telefono()
        );

        return datos;
    }
        public String CrearReserva(DatosFinalAI mensajeRequest) {
            Long canchaIdLong = mensajeRequest.canchaId().longValue();
            try {
                DatosReservaRequest datos = new DatosReservaRequest(mensajeRequest.fecha(),mensajeRequest.hora()
                        ,canchaIdLong,mensajeRequest.nombre(), mensajeRequest.telefono());
            Reserva reserva = reservaService.reservarCancha(datos);
            String contexto= "Reserva creada correctamente."+ datos;
            return GestionarRespuesta(contexto);


             } catch (Exception e) {

                    if (e.getMessage().contains("ocupado")) {
                        var datosdisponibles = new DatosDisponibilidadRequest(mensajeRequest.fecha(), canchaIdLong);
                        List<LocalTime> disponibles = reservaService.obtenerHorariosDisponibles(datosdisponibles);
                        String consulta = "Horario ocupado" + disponibles.toString();
                        return GestionarRespuesta(consulta);

                    }
                    if (e.getMessage().contains("reservar")) {
                        String error = "numero de cancha no encontrado o no existe";
                        return GestionarRespuesta(error);
                    }
                    String error2= "Error inesperado al crear la reserva: " + e.getMessage();
                    return GestionarRespuesta(error2);
            }
        }
        public String ModificarReserva(MensajeRequest mensajeRequest){

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
            var datosModificados = new DatosModificarReservaRequest(
                    mensajeRequest.telefono(),
                    LocalDate.parse(datosModificar.fechaActual()),
                    LocalTime.parse(datosModificar.horaActual()),
                    LocalDate.parse(datosModificar.fechaNueva()),
                    LocalTime.parse(datosModificar.horaNueva()),
                    datosModificar.canchaId(),
                    mensajeRequest.nombre()
            );
            try {
                Reserva reservamodificada = reservaService.modificarReserva(datosModificados);
                String reservamod = "Reserva modificada correctamente." + reservamodificada.toString();
                return GestionarRespuesta(reservamod);
            }catch (Exception e) {
                if (e.getMessage().contains("encontrada")) {
                    String error = GestionarRespuesta(e.getMessage());
                    return GestionarRespuesta(error);
                }
                if (e.getMessage().contains("ocupado")) {
                    String error = GestionarRespuesta(e.getMessage());
                    return error;
                }
                if (e.getMessage().contains("cancha")) {
                    String error = GestionarRespuesta(e.getMessage());
                    return error;
                }
                String error2= "Error inesperado al modificar reserva: " + e.getMessage();
                return GestionarRespuesta(error2);

            }
        }
        public String ConsultarReserva(DatosFinalAI mensajeRequest) {
            var reservaConsultar = reservaRepository.findByTelefonoAndNombreCliente(mensajeRequest.telefono(), mensajeRequest.nombre());
            if (reservaConsultar == null) {
                String error= "No se encontraron reservas";
                return GestionarRespuesta(error);
            }

            String reservaok=   "Tenés una reserva el "
                                + reservaConsultar.getFecha()
                                + " a las "
                                + reservaConsultar.getHora();
            return GestionarRespuesta(reservaok);
        }
        public String CancelarReserva(MensajeRequest mensajeRequest){

                try {
                    var reservaCancelar = reservaRepository.findByTelefonoAndNombreCliente(mensajeRequest.telefono(), mensajeRequest.nombre());
                    var datosCancelar = new DatosIdentificarReservaRequest(
                            reservaCancelar.getFecha(),
                            reservaCancelar.getHora(),
                            reservaCancelar.getTelefono()
                    );
                    reservaService.EliminarReserva(datosCancelar);
                    String cancelar = "Reserva cancelada";
                    return GestionarRespuesta(cancelar);
                }catch (Exception e) {
                    if (e.getMessage().contains("encontrada")) {
                        String error = GestionarRespuesta(e.getMessage());
                        return error;
                    }
                    String error = "Error inesperado al cancelar reserva: " + e.getMessage();
                    return GestionarRespuesta(error);
                }
        }
        public String HorariosLibres(DatosFinalAI mensajeRequest){


            Long canchaId = mensajeRequest.canchaId() != null
                    ? mensajeRequest.canchaId().longValue()
                    : -1L;
            LocalDate fecha = mensajeRequest.fecha();

            try {
                if (canchaId == -1L) {

                    List<Cancha> canchas = canchaRepository.findAll();
                    StringBuilder contexto = new StringBuilder();

                    contexto.append("TIPO: DISPONIBILIDAD_GENERAL | FECHA: ")
                            .append(fecha)
                            .append(" | ");

                    boolean hayDisponibilidad = false;

                    for (Cancha cancha : canchas) {

                        DatosDisponibilidadRequest datos = new DatosDisponibilidadRequest(fecha, cancha.getId());
                        List<LocalTime> disponibles = reservaService.obtenerHorariosDisponibles(datos);

                        if (!disponibles.isEmpty()) {
                            hayDisponibilidad = true;
                            contexto.append("CANCHA ")
                                    .append(cancha.getId())
                                    .append(": ")
                                    .append(disponibles)
                                    .append(" | ");
                        }
                    }

                    if (!hayDisponibilidad) {
                        contexto = new StringBuilder("TIPO: SIN_DISPONIBILIDAD | FECHA: " + fecha);
                    }

                    return GestionarRespuesta(contexto.toString());
                }
                else {

                    DatosDisponibilidadRequest datos = new DatosDisponibilidadRequest(fecha, canchaId);
                    List<LocalTime> disponibles = reservaService.obtenerHorariosDisponibles(datos);

                    String contexto;

                    if (disponibles.isEmpty()) {
                        contexto = "TIPO: SIN_DISPONIBILIDAD | FECHA: " + fecha;
                    } else {
                        contexto = "TIPO: DISPONIBILIDAD | FECHA: " + fecha +
                                " | CANCHA: " + canchaId +
                                " | HORARIOS: " + disponibles;
                    }

                    return GestionarRespuesta(contexto);
                }
            } catch (Exception e) {
                String error = "Error inesperado al consultar horarios: " + e.getMessage();
                return GestionarRespuesta(error);
            }
        }






    }




