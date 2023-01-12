package ist.challenge.bobiahmadrival.service;

import ist.challenge.bobiahmadrival.dto.request.UserRequest;
import ist.challenge.bobiahmadrival.entity.User;

import java.util.List;

public interface UserService {
    void registration(UserRequest registrationUserRequest);
    void login(UserRequest loginUserRequest);
    List<User> listUser();
    void editUser(Long id, UserRequest editUserRequest);
}
