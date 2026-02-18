package JMR.AICHAT.Mensaje;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "mensajes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String telefono;

    @Column(columnDefinition = "TEXT")
    private String mensajeUsuario;

    @Column(columnDefinition = "TEXT")
    private String respuestaSistema;

    private String intencion;

    private LocalDateTime fechaHora;

}
