package ist.challenge.bobiahmadrival.service.impl;

import ist.challenge.bobiahmadrival.dto.request.RegistrationUserRequest;
import ist.challenge.bobiahmadrival.entity.User;
import ist.challenge.bobiahmadrival.error.DuplicateException;
import ist.challenge.bobiahmadrival.error.UnAuthorizedException;
import ist.challenge.bobiahmadrival.repository.UserRepository;
import ist.challenge.bobiahmadrival.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public void registration(RegistrationUserRequest registrationUserRequest) {
        if(!this.validateUser(registrationUserRequest)){
            throw new NullPointerException();
        }
        User userFromDB = userRepository.findByUsername(registrationUserRequest.getUsername());
        if(userFromDB != null){
            throw new DuplicateException();
        }
        User userSave = new User();
        userSave.setUsername(registrationUserRequest.getUsername());
        userSave.setPassword(registrationUserRequest.getPassword());
        userRepository.save(userSave);

    }

    @Override
    public void login(RegistrationUserRequest loginUserRequest) {
        if(!this.validateUser(loginUserRequest)){
            throw new NullPointerException();
        }
        User userFromDB = userRepository.findByUsername(loginUserRequest.getUsername());
        if(userFromDB == null){
            throw new UnAuthorizedException();
        }
        if(!loginUserRequest.getPassword().equals(userFromDB.getPassword())){
            throw new UnAuthorizedException();
        }
    }

    @Override
    public List<User> listUser() {
        return null;
    }

    private boolean validateUser(RegistrationUserRequest userRequest){
        if(userRequest.getUsername() == null || userRequest.getUsername().trim().equals("")){
            return false;
        }
        if(userRequest.getPassword() == null || userRequest.getPassword().trim().equals("")){
            return false;
        }
        return true;
    }
}
