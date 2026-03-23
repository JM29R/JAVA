package JMR.Forum.application.service;

import JMR.Forum.Infrastructure.Dtos.Request.RespuestaRequest;
import JMR.Forum.Infrastructure.Dtos.Response.RespuestaResponse;
import JMR.Forum.Infrastructure.persistence.Respuesta.Mapper.RespuestaDTOMapper;
import JMR.Forum.domain.model.Respuesta;
import JMR.Forum.domain.model.Topico;
import JMR.Forum.domain.model.Usuario.Usuario;
import JMR.Forum.domain.repository.RespuestaRepository;
import JMR.Forum.domain.repository.TopicoRepository;
import JMR.Forum.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RespuestaService {

    private final RespuestaRepository respuestaRepository;

    private final RespuestaDTOMapper respuestaDTOMapper;

    private final UsuarioRepository usuarioRepository;

    private final TopicoRepository topicoRepository;


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

        return respuestaRepository.buscarPorUsuario(idUsuario)
                .stream()
                .map(respuestaDTOMapper::toResponse)
                .toList();

    }

    public void eliminar(long id){
        Respuesta respuesta = respuestaRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Respuesta no encontrada"));

        respuestaRepository.borrar(respuesta);
    }

}
