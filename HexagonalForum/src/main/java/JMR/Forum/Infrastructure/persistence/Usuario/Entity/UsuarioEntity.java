package JMR.Forum.Infrastructure.persistence.Usuario.Entity;

import JMR.Forum.domain.model.Usuario.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String password;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;


    @Enumerated(EnumType.STRING)
    private Roles role;



}
