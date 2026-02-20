package JMR.AICHAT.Service;


import JMR.AICHAT.Cancha.Cancha;
import JMR.AICHAT.Cancha.CanchaRepository;
import JMR.AICHAT.ChatAI.AnalisisAI;
import JMR.AICHAT.ChatAI.DatosFinalAI;
import JMR.AICHAT.ChatAI.DatosModificarAI;
import JMR.AICHAT.Mensaje.Mensaje;
import JMR.AICHAT.Mensaje.MensajeRequest;
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

    private String formatearHistorial(List<Mensaje> historial) {
        if (historial == null || historial.isEmpty()) {
            return "No hay historial previo.";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = historial.size() - 1; i >= 0; i--) {
            Mensaje msg = historial.get(i);
            sb.append("Usuario: ").append(msg.getMensajeUsuario()).append("\n");
            sb.append("Asistente: ").append(msg.getRespuestaSistema()).append("\n\n");
        }
        return sb.toString();
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

    public DatosFinalAI AnalizarMensaje(MensajeRequest mensajeRequest, List<Mensaje> historial) {

        String HistorialMsj = formatearHistorial(historial);
        LocalDate hoy = LocalDate.now();

        String prompt = """
        Sos un analizador de mensajes para un sistema de reservas de canchas de fútbol.
                
                    FECHA ACTUAL DEL SISTEMA: %s
                
                    CONTEXTO DE LA CONVERSACIÓN:
                    %s
                
                    MENSAJE ACTUAL DEL USUARIO:
                    "%s"
                
                    TAREA:
                    Analizar el mensaje actual teniendo en cuenta el contexto SOLO si el usuario hace referencia explícita a algo anterior.
                
                    RESPONDÉ ÚNICAMENTE con un JSON válido (sin texto, sin explicaciones, sin markdown).
                
                    FORMATO EXACTO:
                    {
                      "intencion": "CREAR | CANCELAR | MODIFICAR | CONSULTAR | DISPONIBILIDAD",
                      "fecha": "YYYY-MM-DD o null",
                      "hora": "HH:mm o null",
                      "canchaId": number o null
                    }
                
                    INTENCIONES POSIBLES (solo una):
                    - CREAR → quiere reservar una cancha específica
                    - DISPONIBILIDAD → pregunta horarios o quiere reservar sin indicar cancha
                    - MODIFICAR → quiere cambiar fecha, hora o cancha
                    - CANCELAR → quiere cancelar una reserva
                    - CONSULTAR → pregunta qué reservas tiene o información general
                
                    REGLAS DE INTENCIÓN:
                    - Si pregunta por horarios o disponibilidad → DISPONIBILIDAD
                    - Si quiere reservar:
                        - Si menciona cancha → CREAR
                        - Si NO menciona cancha → DISPONIBILIDAD
                    - Si quiere cambiar algo existente → MODIFICAR
                    - Si quiere cancelar → CANCELAR
                    - Si pregunta por sus reservas → CONSULTAR
                
                    REGLAS DE FECHA:
                    - "hoy" → usar la fecha actual del sistema
                    - "mañana" → fecha actual + 1 día
                    - Día de la semana → próxima fecha de ese día
                    - Si el mensaje actual hace referencia explícita a una fecha anterior (ej: "la misma fecha") → usar la del historial
                    - Si no hay fecha clara → null
                    - Formato obligatorio: YYYY-MM-DD
                    - No usar texto como "hoy" o nombres de meses en la respuesta
                
                    REGLAS DE HORA:
                    - "9", "9 hs", "9h" → 09:00
                    - "9:30" → 09:30
                    - Si el mensaje dice "la misma hora" → usar la del historial
                    - Si no menciona hora → null
                    - Formato obligatorio: HH:mm
                
                    REGLAS DE CANCHA:
                    - "cancha 3" → 3
                    - "la misma cancha" → usar la del historial
                    - Si no menciona cancha → null
                    - Nunca asumir una cancha por defecto
                
                    USO DEL HISTORIAL:
                    - Usar datos del historial SOLO si el mensaje actual lo referencia explícitamente:
                      Ejemplos:
                      "la misma"
                      "esa"
                      "cambiame el turno"
                      "la misma hora"
                    - Si no hay referencia explícita → no usar el historial
                
                    REGLA CRÍTICA (ANTI-ALUCINACIÓN):
                    - NO inventes información
                    - Si un dato no aparece en el mensaje actual ni en el historial → null
                    - Nunca supongas fecha, hora o cancha
                
                    VALIDACIÓN FINAL:
                    - Respondé solo el JSON
                    - No agregues texto antes ni después
                    - No agregues campos extra
                    - Verificá que el JSON sea válido
                
                    EJEMPLOS DE RESPUESTA:
                
                    {"intencion":"CREAR","fecha":"2026-02-20","hora":"18:00","canchaId":2}
                    {"intencion":"DISPONIBILIDAD","fecha":"2026-02-21","hora":null,"canchaId":null}
                    {"intencion":"CONSULTAR","fecha":null,"hora":null,"canchaId":null}
                
                    RESPUESTA:
                    """.formatted(hoy, HistorialMsj, mensajeRequest.mensaje());


        AnalisisAI respuesta = chatClient.prompt()
                .user(prompt)
                .call()
                .entity(AnalisisAI.class);
        LocalDate fecha = null;
        LocalTime hora = null;
        //validar fecha
        if (respuesta.fecha() != null && !respuesta.fecha().isBlank()) {
            try {
                fecha = LocalDate.parse(respuesta.fecha());
            } catch (Exception e) {
                System.err.println("Error parseando fecha: " + respuesta.fecha());
            }
        }
        //validar hora
        if (respuesta.hora() != null && !respuesta.hora().isBlank()) {
            try {
                hora = LocalTime.parse(respuesta.hora());
            } catch (Exception e) {
                System.err.println("Error parseando hora: " + respuesta.hora());
            }
        }
        //limpiar intencion
        String intencion = limpiarIntencion(respuesta.intencion());
        Integer canchaId = respuesta.canchaId() != null ? respuesta.canchaId() : -1;
        //validar si la cancha es -1 es porque no puso cancha y consulta
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




