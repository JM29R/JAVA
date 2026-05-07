package JMR.ModularTickets.Users.domain.Model;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class users {

    private Long id;

    private String username;
    private String password;


    @Builder.Default
    private boolean activo = true;

    private Roles rol;

}
