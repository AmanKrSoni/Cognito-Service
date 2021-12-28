package com.utils;

import com.constants.AppConstant;
import com.exceptions.HeaderNotFoundException;
import com.exceptions.ApplicationException;
import com.exceptions.TokenVerificationFailedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class CommonUtils {

    private final ObjectMapper objectMapper;

    public CommonUtils() {
        this.objectMapper = new ObjectMapper();
    }

    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

    public ObjectMapper getObjectMapper(){
        return this.objectMapper;
    }

    public String isNotEmptyString(String input){
        if(StringUtils.hasLength(input)){
            return input;
        }
        throw new ApplicationException("value cannot be empty", HttpStatus.BAD_REQUEST);
    }

    public String getHeaderValue(HttpHeaders httpHeaders, String headerName){
        if(!CollectionUtils.isEmpty(httpHeaders.get(headerName))){
            return httpHeaders.get(headerName).get(0);
        }
        throw new HeaderNotFoundException(headerName + " header not found !!");
    }

    public Boolean isSuccessfull(ResponseEntity<String> response){
        if(Boolean.FALSE.equals(response.getStatusCode().is2xxSuccessful())){
            log.error("Body : {}, status : {}", response.getBody(), response.getStatusCode());
            switch (response.getStatusCode()){
                case UNAUTHORIZED: throw new TokenVerificationFailedException(response.getBody());
                default: throw new HttpClientErrorException(response.getStatusCode(), response.getBody());
            }
        }
        return Boolean.TRUE;
    }

    public JSONObject addKeyValueInPayload(JSONObject jsonObject, String key, String value){
        if(ObjectUtils.isEmpty(jsonObject)){
            jsonObject = new JSONObject();
        }
        jsonObject.put(key, value);
        return jsonObject;
    }


    public String toJson(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        }catch (JsonProcessingException ex){
            log.error(AppConstant.LOG_ERROR,ex);
            throw new ApplicationException("Unable to get object as Json error : " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Map<String, Object> readAsMap(String data){
        try {
            return objectMapper.readValue(data, new TypeReference<Map<String, Object>>() {});
        }catch (JsonProcessingException exception){
            log.error(AppConstant.LOG_ERROR,exception);
            throw new ApplicationException(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public <T> T readAsClass(String data, Class<T> clazz) {
        return mapToClass(readAsMap(data), clazz);
    }

    public <T> T mapToClass(Map<String, Object> data, Class<T> clazz) {
        return objectMapper.convertValue(data, clazz);
    }
}
