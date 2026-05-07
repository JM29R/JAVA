package JMR.ModularTickets.Users.domain.repository;

import JMR.ModularTickets.Users.domain.Model.users;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    users save(users user);

    List<users> listUsers();

    Optional<users> findById(Long id);

    Optional<users> findByUsername(String username);

    boolean existsById(Long id);

}
