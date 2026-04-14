package JMR.Forum.application.service;


import JMR.Forum.Infrastructure.Dtos.Request.TopicoRequest;
import JMR.Forum.Infrastructure.Dtos.Request.TopicoRequestEdit;
import JMR.Forum.Infrastructure.Dtos.Request.UsuarioLoginRequest;
import JMR.Forum.Infrastructure.Dtos.Request.UsuarioRequest;
import JMR.Forum.Infrastructure.Dtos.Response.TopicoResponse;
import JMR.Forum.Infrastructure.persistence.Topico.Mapper.TopicoDTOMapper;
import JMR.Forum.domain.model.Topico;
import JMR.Forum.domain.model.Usuario.Roles;
import JMR.Forum.domain.model.Usuario.Usuario;
import JMR.Forum.domain.repository.TopicoRepository;
import JMR.Forum.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


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

    private boolean puedeEditar(Usuario user, Topico topico) {
        return user.getId().equals(topico.getAutor().getId());
    }

    public TopicoResponse crearTopico(TopicoRequest request) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Usuario user = usuarioRepository.findByNombre(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Topico topico = topicoMapper.toDomain(request);
        topico.setAutor(user);
        Topico guardado = topicoRepository.guardar(topico);

        return topicoMapper.toResponse(guardado);
    }

    public TopicoResponse editarTopico(TopicoRequestEdit topicoDTO, Long topicoID) {

        Topico topico = topicoRepository.buscarPorId(topicoID)
                .orElseThrow(() -> new RuntimeException("Topico no encontrado"));

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Usuario user = usuarioRepository.findByNombre(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!puedeEditar(user, topico)) {
            throw new RuntimeException("No tienes permiso para editar este tópico");
        }

        topico.setTitulo(topicoDTO.titulo());
        topico.setMensaje(topicoDTO.mensaje());

        Topico editado = topicoRepository.guardar(topico);

        return topicoMapper.toResponse(editado);

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

    public void eliminar(Long id) {
        Topico topico = topicoRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Topico no encontrado"));

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Usuario user = usuarioRepository.findByNombre(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!puedeEliminar(user, topico)) {
            throw new RuntimeException("No tienes permiso para eliminar este tópico");
        }

        topicoRepository.eliminar(id);


    }


}




