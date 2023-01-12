package ist.challenge.bobiahmadrival.controller;

import ist.challenge.bobiahmadrival.constant.Constant;
import ist.challenge.bobiahmadrival.util.RestHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/people")
public class PeopleController {

    /**
     * integrated with another service
     * @param search
     * @return
     */
    @GetMapping
    public ResponseEntity<?> listPeople(@RequestParam(required = false) String search){
        MultiValueMap multiValueMap = new LinkedMultiValueMap();
        multiValueMap.add("search", search);
        RestHandler restHandler = new RestHandler();
        ResponseEntity response = restHandler.getRequest(Constant.URL_PEOPLE_SERVICE, multiValueMap);
        return response;
    }

}
