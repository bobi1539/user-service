package ist.challenge.bobiahmadrival.service.impl;

import ist.challenge.bobiahmadrival.BobiahmadrivalApplication;
import ist.challenge.bobiahmadrival.dto.request.UserRequest;
import ist.challenge.bobiahmadrival.dto.response.UserResponse;
import ist.challenge.bobiahmadrival.entity.User;
import ist.challenge.bobiahmadrival.error.DuplicateException;
import ist.challenge.bobiahmadrival.error.UnAuthorizedException;
import ist.challenge.bobiahmadrival.repository.UserRepository;
import ist.challenge.bobiahmadrival.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void registrationSuccessTest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("otong");
        userRequest.setPassword("password");

        this.userService.registration(userRequest);

        User userOtong = this.userRepository.findByUsername("otong");

        Assertions.assertNotNull(userOtong);
        Assertions.assertEquals(userRequest.getUsername(), userOtong.getUsername());

        this.userRepository.delete(userOtong);
    }

    @Test
    void registrationValidateUserNullAndBlankTest() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            UserRequest userRequest = new UserRequest();
            this.userService.registration(userRequest);
        });
    }

    @Test
    void loginSuccessTest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("ucup");
        userRequest.setPassword("password");

        UserResponse userResponse = this.userService.login(userRequest);
        Assertions.assertEquals(userRequest.getUsername(), userResponse.getUsername());
    }

    @Test
    void loginValidateUserNullOrBlankTest() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            UserRequest userRequest = new UserRequest();
            this.userService.login(userRequest);
        });
    }

    @Test
    void loginUsernameNotExistTest() {
        Assertions.assertThrows(UnAuthorizedException.class, () -> {
            UserRequest userRequest = new UserRequest();
            userRequest.setUsername("notfound");
            userRequest.setPassword("password");

            this.userService.login(userRequest);
        });
    }

    @Test
    void loginPasswordIsWrongTest() {
        Assertions.assertThrows(UnAuthorizedException.class, () -> {
            UserRequest userRequest = new UserRequest();
            userRequest.setUsername("ucup");
            userRequest.setPassword("passwordwrong");

            this.userService.login(userRequest);
        });
    }
}
