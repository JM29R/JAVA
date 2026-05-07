package JMR.ModularTickets.Users.infraestructure.persistence.Repository;

import JMR.ModularTickets.Users.infraestructure.persistence.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByUsername(String username);
}
