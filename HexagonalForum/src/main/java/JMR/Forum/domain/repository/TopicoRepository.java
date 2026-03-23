package JMR.Forum.domain.repository;

import JMR.Forum.domain.model.Topico;

import java.util.List;
import java.util.Optional;

public interface TopicoRepository {

    Topico guardar(Topico topico);

    Optional<Topico> buscarPorId(Long id);

    List<Topico> buscarTodos();

    void eliminar(Long id);


}
