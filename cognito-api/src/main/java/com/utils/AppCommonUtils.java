package com.utils;

import com.adapter.RestAPIClientAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class AppCommonUtils {

    private CommonUtils commonUtils;
    private final RestAPIClientAdapter restAPIClientAdapter;

    @Autowired
    public AppCommonUtils(CommonUtils commonUtils, RestAPIClientAdapter restAPIClientAdapter) {
        this.commonUtils = commonUtils;
        this.restAPIClientAdapter = restAPIClientAdapter;
    }


    public ResponseEntity<String> callAPI(String endPoint, HttpMethod methodType, String payload
            , MultiValueMap<String, String> headers){
        ResponseEntity<String> response  = null;
        try {
            response = restAPIClientAdapter.callAPI(endPoint, methodType, payload, headers);
        } catch (HttpClientErrorException e) {
            log.error("Error : {}, {}", e.getStatusCode(), e.getMessage());
            e.printStackTrace();
            response = ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
        return response;
    }

    public Object buildResponse(final String input){
        List<Object> objects = new ArrayList<>();
        try {
            objects = commonUtils.getObjectMapper().readValue(input, new TypeReference<List<Object>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return objects;
    }


}
