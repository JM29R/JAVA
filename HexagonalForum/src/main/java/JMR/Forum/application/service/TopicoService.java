package JMR.Forum.application.service;


import JMR.Forum.Infrastructure.Dtos.Request.TopicoRequest;
import JMR.Forum.Infrastructure.Dtos.Request.UsuarioRequest;
import JMR.Forum.Infrastructure.Dtos.Response.TopicoResponse;
import JMR.Forum.Infrastructure.persistence.Topico.Mapper.TopicoDTOMapper;
import JMR.Forum.domain.model.Topico;
import JMR.Forum.domain.model.Usuario.Usuario;
import JMR.Forum.domain.repository.TopicoRepository;
import JMR.Forum.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TopicoService {

    private final TopicoRepository topicoRepository;

    private final TopicoDTOMapper topicoMapper;

    private final UsuarioRepository usuarioRepository;

    public TopicoResponse crearTopico(TopicoRequest request) {

        Usuario usuario = usuarioRepository.buscarPorId(request.idAutor())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Topico topico = topicoMapper.toDomain(request);
        topico.setAutor(usuario);
        Topico guardado = topicoRepository.guardar(topico);

        return topicoMapper.toResponse(guardado);
    }

    public List<TopicoResponse> buscarTodos() {
        return topicoRepository.buscarTodos()
                .stream()
                .map(topicoMapper::toResponse)
                .toList();
    }

    public TopicoResponse buscarPorId(Long id) {
        Topico topico = topicoRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Topico no encontrado"));
        return topicoMapper.toResponse(topico);
    }

    public Boolean eliminar(Long id, UsuarioRequest usuarioRequest) {
        Topico topico = topicoRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Topico no encontrado"));
        Usuario user = usuarioRepository.findByNombre(usuarioRequest.nombre())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (user.getId() == topico.getAutor().getId()) {
            topicoRepository.eliminar(id);
            return true;
        }
        return false;
    }
}




