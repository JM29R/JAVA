package JMR.Forum.Infrastructure.Dtos.Response;

public record  UsuarioResponse(
        Long idUser,
        String nombre,
        String role,
        boolean activo
) {
}
