package com.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * The type Phr exception.
 */
@Getter
public class ApplicationException extends RuntimeException {

    private final HttpStatus httpStatusCode;
    private final String message;

    /**
     * Instantiates a new Phr exception.
     *
     * @param message        the message
     * @param httpStatusCode the http status code
     * @param throwable      the throwable
     */
    public ApplicationException(String message, HttpStatus httpStatusCode, Throwable throwable) {
        super(message, throwable);
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    /**
     * Instantiates a new Phr exception.
     *
     * @param message        the message
     * @param httpStatusCode the http status code
     */
    public ApplicationException(String message, HttpStatus httpStatusCode) {
        super(message);
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
