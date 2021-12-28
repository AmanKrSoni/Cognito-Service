package com.utils;

import com.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
public class ResourceValidator {

    public void validateValue(String key, Long value) {
        if (value == null) {
            throw new ApplicationException(key + " is required.", HttpStatus.BAD_REQUEST);
        } else if (value <= 0) {
            throw new ApplicationException(key + " should be greater than zero.", HttpStatus.BAD_REQUEST);
        }
    }

    public void validateValue(String key, Date value) {
        if (value == null) {
            throw new ApplicationException(key + " is required.", HttpStatus.BAD_REQUEST);
        }
    }

    public void validateValue(String key, Double value) {
        if (value == null) {
            throw new ApplicationException(key + " is required.", HttpStatus.BAD_REQUEST);
        } else if (value <= 0d) {
            throw new ApplicationException(key + " should be greater than zero.", HttpStatus.BAD_REQUEST);
        }
    }

    public void validateValue(String key, String value) {
        if (Boolean.FALSE.equals(StringUtils.hasText(value))) {
            throw new ApplicationException(key + " is required.", HttpStatus.BAD_REQUEST);
        }
    }

    public void validateValue(String key, Boolean value) {
        if (value == null) {
            throw new ApplicationException(key + " is required.", HttpStatus.BAD_REQUEST);
        }
    }
}
