package ist.challenge.bobiahmadrival.repository;

import ist.challenge.bobiahmadrival.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUsernameSuccessTest() {
        String username = "ucup";
        User user = this.userRepository.findByUsername(username);
        Assertions.assertEquals(username, user.getUsername());
    }

    @Test
    void findByUsernameNotFoundTest() {
        String username = "notfound";
        User user = this.userRepository.findByUsername(username);
        Assertions.assertNull(user);
    }

    @Test
    void listUsernameSuccessTest() {
        List<String> listUsername = this.userRepository.listUsername();
        Assertions.assertNotNull(listUsername);
    }
}
