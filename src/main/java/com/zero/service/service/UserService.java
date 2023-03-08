package com.zero.service.service;

import com.zero.service.dto.request.UserRequest;
import com.zero.service.entity.User;
import com.zero.service.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse registration(UserRequest registrationUserRequest);
    UserResponse login(UserRequest loginUserRequest);
    List<User> listUser();
    UserResponse editUser(Long id, UserRequest editUserRequest);
}
