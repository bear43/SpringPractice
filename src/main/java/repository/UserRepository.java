package repository;

import model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    User findByLogin(String login);
    List<User> findByAdmin(boolean admin);
    boolean existsUserByLogin(String login);
}
