package JMR.Forum.application.service;

import JMR.Forum.Infrastructure.Dtos.Request.RespuestaRequest;
import JMR.Forum.Infrastructure.Dtos.Request.UsuarioRequest;
import JMR.Forum.Infrastructure.Dtos.Response.RespuestaResponse;
import JMR.Forum.Infrastructure.persistence.Respuesta.Mapper.RespuestaDTOMapper;
import JMR.Forum.domain.model.Respuesta;
import JMR.Forum.domain.model.Topico;
import JMR.Forum.domain.model.Usuario.Roles;
import JMR.Forum.domain.model.Usuario.Usuario;
import JMR.Forum.domain.repository.RespuestaRepository;
import JMR.Forum.domain.repository.TopicoRepository;
import JMR.Forum.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RespuestaService {

    private final RespuestaRepository respuestaRepository;

    private final RespuestaDTOMapper respuestaDTOMapper;

    private final UsuarioRepository usuarioRepository;

    private final TopicoRepository topicoRepository;

    private boolean puedeEliminar(Usuario user, Respuesta respuesta) {
        return user.getId().equals(respuesta.getAutor().getId())// si es el autor de la respuesta
                || user.getRol() == Roles.ROLE_ADMIN//si es admin
                || user.getId().equals(respuesta.getTopico().getAutor().getId());//si es el autor del topico
    }



    public RespuestaResponse crear(RespuestaRequest request){
        Usuario usuario = usuarioRepository.buscarPorId(request.autorId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Topico topico = topicoRepository.buscarPorId(request.topicoId())
                .orElseThrow(() -> new RuntimeException("Topico no encontrado"));

        Respuesta respuesta = respuestaDTOMapper.toDomain(request,usuario,topico);

        Respuesta res = respuestaRepository.guardar(respuesta);
        return respuestaDTOMapper.toResponse(res);

    }

    public List<RespuestaResponse> listarPorTopico(Long idTopico) {

        return respuestaRepository.buscarTodosPorTopico(idTopico)
                .stream()
                .map(respuestaDTOMapper::toResponse)
                .toList();
    }

    public List<RespuestaResponse> listarPorUsuario(Long idUsuario) {

        return respuestaRepository.buscarTodosPorUsuario(idUsuario)
                .stream()
                .map(respuestaDTOMapper::toResponse)
                .toList();

    }

    public void eliminar(long id) {
        Respuesta respuesta = respuestaRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Respuesta no encontrada"));

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        Usuario user = usuarioRepository.findByNombre(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!puedeEliminar(user, respuesta)) {
            throw new RuntimeException("No tienes permiso para eliminar esta respuesta");
        }

        respuestaRepository.borrar(respuesta);
    }

}
