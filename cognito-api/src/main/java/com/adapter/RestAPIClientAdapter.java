package com.adapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class RestAPIClientAdapter {
    private final RestTemplate restTemplate;

    public RestAPIClientAdapter(@Autowired RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Retryable(value = {HttpClientErrorException.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public ResponseEntity<String> callAPI(String endPoint, HttpMethod methodType, String payload
            , MultiValueMap<String, String> headers) throws HttpClientErrorException {

        if (Boolean.FALSE.equals(StringUtils.hasText(endPoint))) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Rest API end point can not be null.");
        }

        if (methodType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Rest API method type can not be null.");
        }

        if ((methodType.equals(HttpMethod.POST)
                || methodType.equals(HttpMethod.PUT)
                || methodType.equals(HttpMethod.PATCH)) && payload == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Payload can not be null for Rest API method " + methodType);
        }

        return restTemplate.exchange(endPoint, methodType, new HttpEntity<>(payload, headers), String.class);
    }
}
