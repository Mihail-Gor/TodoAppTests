package ru.noname.company.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;
import java.util.Map;

import static ru.noname.company.config.TestConfiguration.password;
import static ru.noname.company.config.TestConfiguration.username;
import static ru.noname.company.util.TestUtils.getJsonString;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestClient {
    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;

    public ResponseEntity<String> getRequest(String url) {
        ResponseEntity<String> response = restTemplate.getForEntity(UriComponentsBuilder.fromHttpUrl(url).toUriString(), String.class);
        log.info("Response:\n{}", response.getBody());

        return response;
    }

    public ResponseEntity<String> postRequest(String url, Map<String, Object> body) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(getJsonString(body), httpHeaders);

        return exchange(UriComponentsBuilder.fromHttpUrl(url).toUriString(),
                HttpMethod.POST, entity);
    }

    public ResponseEntity<String> putRequest(String url, Map<String, Object> body) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(getJsonString(body), httpHeaders);

        return exchange(UriComponentsBuilder.fromHttpUrl(url).toUriString(),
                HttpMethod.PUT, entity);
    }

    public ResponseEntity<String> deleteRequest(String url) {
        String authData = String.format("%s:%s", username, password);
        String base64Credits = Base64.getEncoder().encodeToString(authData.getBytes());

        httpHeaders.add("Authorization", "Basic " + base64Credits);
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);

        return exchange(UriComponentsBuilder.fromHttpUrl(url).toUriString(),
                HttpMethod.DELETE, entity);
    }

    private ResponseEntity<String> exchange(String url, HttpMethod method, HttpEntity<?> httpEntity) {
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(url, method, httpEntity, String.class);
        } catch (HttpClientErrorException e) {
            responseEntity = new ResponseEntity<>(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
        } catch (HttpServerErrorException e) {
            responseEntity = new ResponseEntity<>(e.getResponseBodyAsString(), HttpStatus.INTERNAL_SERVER_ERROR);
            throw e;
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            throw e;
        }
        return responseEntity;
    }

//    private HttpEntity<String> getJsonHttpEntity(Map<String, Object> body) {
//        String bodyAsJsonString;
//
//        try {
//            bodyAsJsonString = objectMapper.writeValueAsString(body);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        return new HttpEntity<>(bodyAsJsonString, httpHeaders);
//    }

//    public <T> ResponseEntity<T> getRequest(String url, Map<QueryParams, String> params, Class<T> clazz) {
//        MultiValueMap<String, String> query = new LinkedMultiValueMap<>();
//        params.forEach((queryParams, s) -> query.add(queryParams.toString(), s));
//        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
//                .queryParams(query);
//
//        ResponseEntity<T> response = restTemplate.getForEntity(uriBuilder.toUriString(), clazz);
//        log.info("Response:\n" + response.getBody());
//
//        return response;
//    }
}