package ist.challenge.bobiahmadrival.service.impl;

import ist.challenge.bobiahmadrival.constant.Constant;
import ist.challenge.bobiahmadrival.dto.request.UserRequest;
import ist.challenge.bobiahmadrival.entity.User;
import ist.challenge.bobiahmadrival.error.DuplicateException;
import ist.challenge.bobiahmadrival.error.UnAuthorizedException;
import ist.challenge.bobiahmadrival.repository.UserRepository;
import ist.challenge.bobiahmadrival.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public void registration(UserRequest registrationUserRequest) {
        if(!this.validateUser(registrationUserRequest)){
            throw new NullPointerException(Constant.USERNAME_OR_PASSWORD_CANNOT_BE_NULL);
        }
        User userFromDB = userRepository.findByUsername(registrationUserRequest.getUsername());
        if(userFromDB != null){
            throw new DuplicateException(Constant.USERNAME_ALREADY_IN_USE);
        }
        User userSave = new User();
        userSave.setUsername(registrationUserRequest.getUsername());
        userSave.setPassword(registrationUserRequest.getPassword());
        userRepository.save(userSave);

    }

    @Override
    public void login(UserRequest loginUserRequest) {
        if(!this.validateUser(loginUserRequest)){
            throw new NullPointerException(Constant.USERNAME_OR_PASSWORD_CANNOT_BE_NULL);
        }
        User userFromDB = userRepository.findByUsername(loginUserRequest.getUsername());
        if(userFromDB == null){
            throw new UnAuthorizedException(Constant.USERNAME_OR_PASSWORD_IS_WRONG);
        }
        if(!loginUserRequest.getPassword().equals(userFromDB.getPassword())){
            throw new UnAuthorizedException(Constant.USERNAME_OR_PASSWORD_IS_WRONG);
        }
    }

    @Override
    public List<User> listUser() {
        List<User> users = userRepository.findAll();
        if(users.size() == 0){
            throw new NullPointerException(Constant.NO_USER_IN_LIST);
        }
        return users;
    }

    @Override
    public void editUser(Long id, UserRequest editUserRequest) {
        Optional<User> userFromDB = userRepository.findById(id);
        if(userFromDB.isEmpty()){
            throw new NullPointerException("User with id " + id + " not exist");
        }
        User user = userFromDB.get();
        if(isUsernameExist(editUserRequest, user)){
            throw new DuplicateException(Constant.USERNAME_ALREADY_IN_USE);
        }

        if(user.getPassword().equals(editUserRequest.getPassword())){
            throw new NullPointerException(Constant.PASSWORD_CANNOT_SAME_TO_PREVIOUS);
        }

        user.setUsername(editUserRequest.getUsername());
        user.setPassword(editUserRequest.getPassword());
        userRepository.save(user);
    }

    private boolean validateUser(UserRequest userRequest){
        if(userRequest.getUsername() == null || userRequest.getUsername().trim().equals("")){
            return false;
        }
        if(userRequest.getPassword() == null || userRequest.getPassword().trim().equals("")){
            return false;
        }
        return true;
    }

    private boolean isUsernameExist(UserRequest userRequest, User userFromDB){
        // find all user
        List<String> listUsername = userRepository.listUsername();
        // remove user in list
        listUsername.remove(userFromDB.getUsername());
        for(String username : listUsername){
            if(username.equals(userRequest.getUsername())){
                return true;
            }
        }
        return false;
    }
}
