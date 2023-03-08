package com.zero.service.repository;

import com.zero.service.entity.User;
import org.junit.jupiter.api.Assertions;
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
        User user = new User();
        user.setUsername("userTest");
        user.setPassword("userTestPassword");
        User userSaved = this.userRepository.save(user);

        String username = user.getUsername();
        User userFindByUsername = this.userRepository.findByUsername(username);
        Assertions.assertEquals(username, userFindByUsername.getUsername());

        this.userRepository.delete(userSaved);
    }

    @Test
    void findByUsernameNotFoundTest() {
        String username = "notfound";
        User user = this.userRepository.findByUsername(username);
        Assertions.assertNull(user);
    }

    @Test
    void listUsernameSuccessTest() {
        User user1 = new User();
        user1.setUsername("userTest1");
        user1.setPassword("userTestPassword1");
        User user1Saved = this.userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("userTest2");
        user2.setPassword("userTestPassword2");
        User user2Saved = this.userRepository.save(user2);

        List<String> listUsername = this.userRepository.listUsername();
        Assertions.assertNotNull(listUsername);
        Assertions.assertTrue(listUsername.size() > 0);

        this.userRepository.delete(user1Saved);
        this.userRepository.delete(user2Saved);
    }
}
