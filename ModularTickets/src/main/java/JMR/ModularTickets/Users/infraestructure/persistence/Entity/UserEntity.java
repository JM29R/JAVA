package JMR.ModularTickets.Users.infraestructure.persistence.Entity;


import JMR.ModularTickets.Users.domain.Model.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;


    @Enumerated(EnumType.STRING)
    private Roles role;
}
