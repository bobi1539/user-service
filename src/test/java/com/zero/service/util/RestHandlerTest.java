package com.zero.service.util;

import com.zero.service.constant.Constant;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RestHandlerTest {

    @Test
    void getRequestSuccessTest() {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        RestHandler restHandler = new RestHandler();
        ResponseEntity<?> response = restHandler.getRequest(Constant.URL_PEOPLE_SERVICE, parameters);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }
}