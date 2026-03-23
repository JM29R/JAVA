package JMR.Forum.Infrastructure.persistence.Topico.Repository;

import JMR.Forum.Infrastructure.persistence.Topico.Entity.TopicoEntity;
import JMR.Forum.Infrastructure.persistence.Topico.Mapper.TopicoMapper;
import JMR.Forum.domain.model.Topico;
import JMR.Forum.domain.repository.TopicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class TopicoRepositoryJPA implements TopicoRepository {

    private final SpringDataTopicoRepository repository;
    private final TopicoMapper topicoMapper;

    @Override
    public Topico guardar(Topico topico) {

        TopicoEntity entity = topicoMapper.toEntity(topico);

        repository.save(entity);

        return topicoMapper.toDomain(entity);
    }

    @Override
    public Optional<Topico> buscarPorId(Long id) {

        return repository.findById(id)
                .map(topicoMapper::toDomain);
    }

    @Override
    public List<Topico> buscarTodos() {

        return repository.findAll()
                .stream()
                .map(topicoMapper::toDomain)
                .toList();
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
