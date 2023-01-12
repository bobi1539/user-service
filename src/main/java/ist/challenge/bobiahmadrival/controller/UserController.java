package ist.challenge.bobiahmadrival.controller;

import ist.challenge.bobiahmadrival.dto.request.UserRequest;
import ist.challenge.bobiahmadrival.dto.response.ABaseResponse;
import ist.challenge.bobiahmadrival.dto.response.ResponseWithData;
import ist.challenge.bobiahmadrival.entity.User;
import ist.challenge.bobiahmadrival.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> registration(@RequestBody UserRequest userRequest){
        // try{
            userService.registration(userRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ABaseResponse(201, "Registration Successfully"));
        // } catch (NullPointerException e){
        // log.error(e.getMessage());
        // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        //         .body(new ABaseResponse(400, "Username or Password cannot be null or blank"));
        // } catch (DuplicateException e){
        // log.error(e.getMessage());
        // return ResponseEntity.status(HttpStatus.CONFLICT)
        //      .body(new ABaseResponse(409, "Username Already In Use"));
        // }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest loginRequest){
        userService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ABaseResponse(200, "Login Successfully"));
    }

    @GetMapping
    public ResponseEntity<?> listUser(){
        List<User> users = userService.listUser();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWithData<List<User>>(200, "Success", users));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> editUser(@PathVariable(value = "id") Long id, @RequestBody UserRequest request){
        userService.editUser(id, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ABaseResponse(201, "User has been edited successfully"));
    }
}
