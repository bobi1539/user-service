package ist.challenge.bobiahmadrival.repository;

import ist.challenge.bobiahmadrival.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query(value = "select username from m_user", nativeQuery = true)
    List<String> listUsername();
}
