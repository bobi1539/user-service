package com.zero.service.service.impl;

import com.zero.service.constant.Constant;
import com.zero.service.dto.request.UserRequest;
import com.zero.service.entity.User;
import com.zero.service.dto.response.UserResponse;
import com.zero.service.error.DuplicateException;
import com.zero.service.error.UnAuthorizedException;
import com.zero.service.repository.UserRepository;
import com.zero.service.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        userRequest.setUsername("userTestRegistration");
        userRequest.setPassword("userTestRegistration");

        UserResponse userResponse = this.userService.registration(userRequest);


        Assertions.assertNotNull(userResponse);
        Assertions.assertEquals(userRequest.getUsername(), userResponse.getUsername());

        User userRegistration = this.userRepository.findByUsername(userRequest.getUsername());
        this.userRepository.delete(userRegistration);
    }

    @Test
    void registrationValidateUserNullAndBlankTest() {
        NullPointerException nullPointerException = Assertions.assertThrows(NullPointerException.class, () -> {
            UserRequest userRequest = new UserRequest();
            this.userService.registration(userRequest);
        });
        Assertions.assertTrue(nullPointerException.getMessage().equals(Constant.USERNAME_OR_PASSWORD_CANNOT_BE_NULL));
    }

    @Test
    void registrationUsernameAlreadyInUseTest() {
        User userSave = this.userSave();
        UserRequest userRequest = new UserRequest(userSave.getUsername(), userSave.getPassword());

        DuplicateException duplicateException = Assertions.assertThrows(DuplicateException.class, () -> {
            this.userService.registration(userRequest);
        });
        Assertions.assertTrue(duplicateException.getMessage().equals(Constant.USERNAME_ALREADY_IN_USE));

        this.userRepository.delete(userSave);
    }

    @Test
    void loginSuccessTest() {
        User userSave = this.userSave();

        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(userSave.getUsername());
        userRequest.setPassword(userSave.getPassword());

        UserResponse userResponse = this.userService.login(userRequest);
        Assertions.assertEquals(userRequest.getUsername(), userResponse.getUsername());
        Assertions.assertEquals(userSave.getId(), userResponse.getId());

        this.userRepository.delete(userSave);
    }

    @Test
    void loginValidateUserNullOrBlankTest() {
        NullPointerException nullPointerException = Assertions.assertThrows(NullPointerException.class, () -> {
            UserRequest userRequest = new UserRequest();
            this.userService.login(userRequest);
        });
        Assertions.assertTrue(nullPointerException.getMessage().equals(Constant.USERNAME_OR_PASSWORD_CANNOT_BE_NULL));
    }

    @Test
    void loginUsernameNotExistTest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("notfound");
        userRequest.setPassword("password");

        UnAuthorizedException unAuthorizedException = Assertions.assertThrows(UnAuthorizedException.class, () -> {
            this.userService.login(userRequest);
        });
        Assertions.assertTrue(unAuthorizedException.getMessage().equals(Constant.USERNAME_OR_PASSWORD_IS_WRONG));
    }

    @Test
    void loginPasswordIsWrongTest() {
        User userSave = this.userSave();

        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(userSave.getUsername());
        userRequest.setPassword(userSave.getPassword().concat("wrong"));

        UnAuthorizedException unAuthorizedException = Assertions.assertThrows(UnAuthorizedException.class, () -> {

            this.userService.login(userRequest);
        });
        Assertions.assertTrue(unAuthorizedException.getMessage().equals(Constant.USERNAME_OR_PASSWORD_IS_WRONG));

        this.userRepository.delete(userSave);
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

        this.userRepository.delete(userSaved);
    }

    @Test
    void editUserIdNotFoundTest() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            Long id = -1L;
            UserRequest userRequest = new UserRequest("test", "test");
            this.userService.editUser(id, userRequest);
        });
    }

    @Test
    void editUserUsernameIsExistTest() {
        User user1Saved = this.userSave();
        User user2Saved = this.userSave2();

        UserRequest userRequestEditSaved2 = new UserRequest();
        userRequestEditSaved2.setUsername(user1Saved.getUsername());
        userRequestEditSaved2.setPassword("user2PasswordNew");

        DuplicateException duplicateException = Assertions.assertThrows(DuplicateException.class, () -> {
            this.userService.editUser(user2Saved.getId(), userRequestEditSaved2);
        });
        Assertions.assertTrue(duplicateException.getMessage().equals(Constant.USERNAME_ALREADY_IN_USE));

        this.userRepository.delete(user1Saved);
        this.userRepository.delete(user2Saved);
    }

    @Test
    void editUserPasswordCannotSameWithPreviousTest() {
        User userSave = this.userSave();

        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("userTest");
        userRequest.setPassword("userPassword");


        NullPointerException nullPointerException = Assertions.assertThrows(NullPointerException.class, () -> {
            this.userService.editUser(userSave.getId(), userRequest);
        });

        Assertions.assertTrue(Constant.PASSWORD_CANNOT_SAME_TO_PREVIOUS.equals(nullPointerException.getMessage()));

        this.userRepository.delete(userSave);
    }

    User userSave(){
        User user = new User();
        user.setUsername("userTest");
        user.setPassword("userPassword");
        User userSaved = this.userRepository.save(user);
        return userSaved;
    }

    User userSave2(){
        User user = new User();
        user.setUsername("userTest2");
        user.setPassword("userPassword2");
        User userSaved = this.userRepository.save(user);
        return userSaved;
    }
}
