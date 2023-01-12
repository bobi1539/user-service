package ist.challenge.bobiahmadrival.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Slf4j
public class RestHandler {

    private RestTemplate restTemplate;

    public RestHandler(){
        this.restTemplate = new RestTemplate();
    }

    private final HttpHeaders headers = new HttpHeaders();
    public ResponseEntity<?> getRequest(String url, MultiValueMap<String, String> parameters){

        try {

            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                    .queryParams(parameters);
            UriComponents uriComponents = builder.build().encode();

            log.info("Sending GET request to : " + uriComponents.toUri());
            log.info("Payload : " + uriComponents.getQuery());

            ResponseEntity<String> response = restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, entity, String.class);

            log.info("Receive response : " + response);
            return response;

        } catch (Exception e){
            log.error("Error while get request : " + e.getMessage());
            return null;
        }

    }
}
