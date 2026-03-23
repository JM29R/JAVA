package JMR.Forum.domain.model.Usuario;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
    private Long id;
    private String nombre;
    private String password;

    @Builder.Default
    private boolean activo = true;

    private Roles rol;



}
