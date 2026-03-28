package JMR.Forum.application.service;


import JMR.Forum.Infrastructure.Dtos.Request.TopicoRequest;
import JMR.Forum.Infrastructure.Dtos.Request.UsuarioRequest;
import JMR.Forum.Infrastructure.Dtos.Response.TopicoResponse;
import JMR.Forum.Infrastructure.persistence.Topico.Mapper.TopicoDTOMapper;
import JMR.Forum.domain.model.Topico;
import JMR.Forum.domain.model.Usuario.Roles;
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

    private boolean puedeEliminar(Usuario user, Topico topico) {
        return user.getId().equals(topico.getAutor().getId())
                || user.getRol() == Roles.ROLE_ADMIN;
    }

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

    public void eliminar(Long id, UsuarioRequest usuarioRequest) {
        Topico topico = topicoRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Topico no encontrado"));
        Usuario user = usuarioRepository.findByNombre(usuarioRequest.nombre())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!puedeEliminar(user, topico)) {
            throw new RuntimeException("No tienes permiso para eliminar este tópico");
        }

        topicoRepository.eliminar(id);


    }
}




