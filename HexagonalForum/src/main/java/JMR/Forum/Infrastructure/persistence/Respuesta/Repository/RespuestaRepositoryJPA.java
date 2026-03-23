package JMR.Forum.Infrastructure.persistence.Respuesta.Repository;

import JMR.Forum.Infrastructure.persistence.Respuesta.Entity.RespuestaEntity;
import JMR.Forum.Infrastructure.persistence.Respuesta.Mapper.RespuestaMapper;
import JMR.Forum.domain.model.Respuesta;
import JMR.Forum.domain.repository.RespuestaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RespuestaRepositoryJPA implements RespuestaRepository {

    private final RespuestaMapper respuestaMapper;
    private final SpringDataRespuestaRepository repository;

    @Override
    public Respuesta guardar(Respuesta respuesta) {
        RespuestaEntity entity = respuestaMapper.toEntity(respuesta);
        repository.save(entity);
        return respuestaMapper.toDomain(entity);

    }

    @Override
    public Optional<Respuesta> buscarPorId(Long id) {
        return repository.findById(id)
                .map(respuestaMapper::toDomain);
    }

    @Override
    public Optional<Respuesta> buscarPorUsuario(Long idautor) {
        RespuestaEntity entity = repository.findByAutor_Id(idautor);
        return Optional.ofNullable(respuestaMapper.toDomain(entity));


    }

    @Override
    public List<Respuesta> buscarTodosPorTopico(Long idTopico) {
        return repository.findListByTopico_Id(idTopico)
                .stream()
                .map(respuestaMapper::toDomain)
                .toList();
    }

    @Override
    public void borrar(Respuesta respuesta) {
        repository.deleteById(respuesta.getId());
    }

    @Override
    public List<Respuesta> buscarTodosPorUsuario(Long idautor) {
        return repository.findListByAutor_Id(idautor)
                .stream()
                .map(respuestaMapper::toDomain)
                .toList();
    }
}
