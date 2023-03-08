package com.zero.service.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zero.service.dto.request.UserRequest;
import com.zero.service.entity.User;
import com.zero.service.repository.UserRepository;
import com.zero.service.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void registrationSuccessTest() throws Exception {

        String requestJson = "{\"username\" : \"registrationTest\",\"password\" : \"registrationTest\"}";
        this.mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andDo(print()).andExpect(status().is(201));

        User registrationTest = this.userRepository.findByUsername("registrationTest");
        this.userRepository.delete(registrationTest);
    }

    @Test
    void loginSuccessTest() throws Exception {
        User user = new User();
        user.setUsername("loginSuccessTest");
        user.setPassword("loginSuccessPass");
        User userSaved = this.userRepository.save(user);

        String requestJson = "{\"username\" : \"loginSuccessTest\",\"password\" : \"loginSuccessPass\"}";
        this.mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andDo(print()).andExpect(status().is(200));

        this.userRepository.delete(userSaved);
    }

    @Test
    void listUserSuccessTest() throws Exception {
        User user1 = new User();
        user1.setUsername("user1Test");
        user1.setPassword("user1TestPass");
        User user1Saved = this.userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("user2Test");
        user2.setPassword("user2TestPass");
        User user2Saved = this.userRepository.save(user2);

        this.mockMvc.perform(get("/user")).andDo(print()).andExpect(status().is(200));

        this.userRepository.delete(user1Saved);
        this.userRepository.delete(user2Saved);
    }

    @Test
    void editUserSuccessTest() throws Exception {
        User user1 = new User();
        user1.setUsername("editUserTest");
        user1.setPassword("editUserTest");
        User user1Saved = this.userRepository.save(user1);

        String requestJson = "{\"username\" : \"editUserTest\",\"password\" : \"editUserPass\"}";
        this.mockMvc.perform(put("/user/" + user1Saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andDo(print()).andExpect(status().is(201));

        this.userRepository.delete(user1Saved);
    }
}
