package ist.challenge.bobiahmadrival.controller;

import ist.challenge.bobiahmadrival.dto.request.RegistrationUserRequest;
import ist.challenge.bobiahmadrival.dto.response.ABaseResponse;
import ist.challenge.bobiahmadrival.error.DuplicateException;
import ist.challenge.bobiahmadrival.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> registration(@RequestBody RegistrationUserRequest userRequest){
        try{
            userService.registration(userRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ABaseResponse(201, "Registration Successfully"));
        } catch (NullPointerException e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ABaseResponse(400, "Username or Password cannot be null or blank"));
        } catch (DuplicateException e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ABaseResponse(409, "Username Already In Use"));
        }
    }
}
