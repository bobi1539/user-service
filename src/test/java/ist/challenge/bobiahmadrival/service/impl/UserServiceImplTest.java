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

import java.util.List;

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

    @Test
    void listUserSuccessTest() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("testPassword");

        this.userRepository.save(user);

        List<User> users = this.userService.listUser();

        Assertions.assertNotNull(users);
        Assertions.assertTrue(users.size() > 0);

        this.userRepository.delete(user);
    }

    @Test
    void editUserSuccessTest() {
        User user = new User();
        user.setUsername("editUserTest");
        user.setPassword("passwordTest");

        User userSaved = this.userRepository.save(user);

        Long idForEdit = userSaved.getId();

        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("editUserTestNew");
        userRequest.setPassword("passwordTestNew");

        UserResponse userResponse = this.userService.editUser(idForEdit, userRequest);

        Assertions.assertEquals(userResponse.getId(), idForEdit);
        Assertions.assertEquals(userResponse.getUsername(), userRequest.getUsername());
    }

    @Test
    void editUserIdNotFoundTest() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            Long id = -1L;
            UserRequest userRequest = new UserRequest("test", "test");
            this.userService.editUser(id, userRequest);
        });
    }
}
