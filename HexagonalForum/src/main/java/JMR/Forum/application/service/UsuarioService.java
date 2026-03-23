package JMR.Forum.application.service;


import JMR.Forum.Infrastructure.Dtos.Request.UsuarioRequest;
import JMR.Forum.Infrastructure.Dtos.Response.UsuarioResponse;
import JMR.Forum.Infrastructure.persistence.Usuario.Mapper.UsuarioDTOMapper;
import JMR.Forum.domain.model.Usuario.Roles;
import JMR.Forum.domain.model.Usuario.Usuario;
import JMR.Forum.domain.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    private UsuarioDTOMapper usuarioDTOMapper;

    private PasswordEncoder passwordEncoder;


    public UsuarioResponse crearUsuario(UsuarioRequest request) {
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

    public UsuarioResponse editarUsuario(Long id, UsuarioRequest request) {
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
