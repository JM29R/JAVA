package JMR.Forum.application.service;


import JMR.Forum.Infrastructure.Dtos.Request.UsuarioLoginRequest;
import JMR.Forum.Infrastructure.Dtos.Response.UsuarioResponse;
import JMR.Forum.Infrastructure.persistence.Usuario.Mapper.UsuarioDTOMapper;
import JMR.Forum.domain.model.Usuario.Roles;
import JMR.Forum.domain.model.Usuario.Usuario;
import JMR.Forum.domain.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    private UsuarioDTOMapper usuarioDTOMapper;

    private PasswordEncoder passwordEncoder;

    private boolean puedeCrear(UsuarioLoginRequest usuarioRequest) {
        Optional<Usuario> userOpt = usuarioRepository.findByNombre(usuarioRequest.nombre());

        if (userOpt.isEmpty()) {
            return true;
        }

        Usuario user = userOpt.get();
        return !user.isActivo();

    }


    public UsuarioResponse crearUsuario(UsuarioLoginRequest request) {
        if (!puedeCrear(request)) {
            throw new RuntimeException("Ya existe un usuario activo con ese nombre");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(request.nombre());
        usuario.setPassword(passwordEncoder.encode(request.password()));
        usuario.setActivo(true);
        usuario.setRol(Roles.ROLE_USER);
        Usuario nuevo = usuarioRepository.guardar(usuario);
        System.out.println("GUARDADO ACTIVO: " + nuevo.isActivo());

        return usuarioDTOMapper.toResponse(nuevo);
    }

    public UsuarioResponse BuscarporID(Long id) {
        Usuario user = usuarioRepository.buscarPorId(id)
                .orElseThrow(()-> new RuntimeException("Usuario no encontrado"));
        return  usuarioDTOMapper.toResponse(user);
    }

    public List<UsuarioResponse> listar() {
        return usuarioRepository.listar()
                .stream()
                .map(usuarioDTOMapper::toResponse)
                .toList();
    }

    public boolean ActivoporID(Long id) {
        Usuario user = usuarioRepository.buscarPorId(id)
                .orElseThrow(()-> new RuntimeException("Usuario no encontrado"));
        return user.isActivo();
    }

    public UsuarioResponse editarUsuario(Long id, UsuarioLoginRequest request) {
        Usuario usuario = usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(request.nombre());
        usuario.setPassword(passwordEncoder.encode(request.password()));

        Usuario actualizado = usuarioRepository.guardar(usuario);

        return usuarioDTOMapper.toResponse(actualizado);
    }

    public UsuarioResponse eliminarUsuario(Long id) {
        Usuario user = usuarioRepository.buscarPorId(id)
                .orElseThrow(()-> new RuntimeException("Usuario no encontrado"));
        user.setActivo(false);
       Usuario eliminado = usuarioRepository.guardar(user);
        return usuarioDTOMapper.toResponse(eliminado);
    }



}
