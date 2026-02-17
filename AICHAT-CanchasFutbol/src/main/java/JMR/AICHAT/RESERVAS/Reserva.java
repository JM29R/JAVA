package JMR.AICHAT.RESERVAS;


import JMR.AICHAT.CANCHAS.Cancha;
import JMR.AICHAT.CANCHAS.CanchaRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private LocalTime hora;

    private String nombreCliente;
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "cancha_id")
    private Cancha cancha;

}


