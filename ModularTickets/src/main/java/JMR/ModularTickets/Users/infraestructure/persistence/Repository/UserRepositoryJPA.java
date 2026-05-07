package JMR.ModularTickets.Users.infraestructure.persistence.Repository;


import JMR.ModularTickets.Users.domain.Model.users;
import JMR.ModularTickets.Users.domain.repository.UserRepository;
import JMR.ModularTickets.Users.infraestructure.persistence.Entity.UserEntity;
import JMR.ModularTickets.Users.infraestructure.persistence.Mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRepositoryJPA implements UserRepository {

    private final UserMapper userMapper;
    private final SpringDataUserRepository repository;

    @Override
    public users save(users user) {
        UserEntity Entity = userMapper.ToEntity(user);
        UserEntity saved = repository.save(Entity);

        return userMapper.toDomain(saved);
    }

    @Override
    public Optional<users> findById(Long id) {
        return repository.findById(id)
                .map(userMapper::toDomain);
    }

    @Override
    public List<users> listUsers() {
        return repository.findAll()
                .stream()
                .map(userMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public Optional<users> findByUsername(String username) {
        return repository.findByUsername(username)
                .map(userMapper::toDomain);
    }
}
