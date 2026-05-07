package JMR.ModularTickets.Users.infraestructure.persistence.Mapper;


import JMR.ModularTickets.Users.domain.Model.users;
import JMR.ModularTickets.Users.infraestructure.persistence.Entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {

    public users toDomain(UserEntity userEntity) {
        users user = new users();
        user.setId(userEntity.getId());
        user.setUsername(userEntity.getUsername());
        user.setPassword(userEntity.getPassword());
        user.setActivo(userEntity.getActivo());
        user.setRol(userEntity.getRole());
        return user;
    }

    public UserEntity ToEntity(users user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userEntity.setActivo(user.isActivo());
        userEntity.setRole(user.getRol());
        return userEntity;

    }


}
