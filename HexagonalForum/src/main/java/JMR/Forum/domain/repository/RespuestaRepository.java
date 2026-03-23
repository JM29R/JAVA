package JMR.Forum.domain.repository;

import JMR.Forum.domain.model.Respuesta;

import java.util.List;
import java.util.Optional;

public interface RespuestaRepository {

    Respuesta guardar(Respuesta respuesta);
    Optional<Respuesta> buscarPorId(Long id);
    Optional<Respuesta> buscarPorUsuario(Long idautor);
    List<Respuesta> buscarTodosPorTopico(Long idTopico);
    void borrar(Respuesta respuesta);
    List<Respuesta> buscarTodosPorUsuario(Long idautor);

}
