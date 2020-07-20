package engine.Repositories;

import engine.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
}
