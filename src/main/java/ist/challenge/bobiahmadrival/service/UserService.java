package ist.challenge.bobiahmadrival.service;

import ist.challenge.bobiahmadrival.dto.request.RegistrationUserRequest;
import ist.challenge.bobiahmadrival.entity.User;

import java.util.List;

public interface UserService {
    void registration(RegistrationUserRequest registrationUserRequest);
    void login(RegistrationUserRequest loginUserRequest);
    List<User> listUser();
}
