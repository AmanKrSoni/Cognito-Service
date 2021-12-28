package com.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.Instant;

/**
 * The type Api error response.
 */
@Getter
public class ApiErrorResponse {

    private String message;
    private HttpStatus status;
    private String requestedUrl;
    private String timeStamp = Instant.now().toString();

    /**
     * Instantiates a new Api error response.
     *
     * @param message the message
     * @param status  the status
     */
    public ApiErrorResponse(String message, HttpStatus status) {
        super();
        this.message = message;
        this.status = status;
    }

    /**
     * Instantiates a new Api error response.
     *
     * @param message      the message
     * @param status       the status
     * @param requestedUrl the requested url
     */
    public ApiErrorResponse(String message, HttpStatus status, String requestedUrl) {
        super();
        this.message = message;
        this.status = status;
        this.requestedUrl = requestedUrl;
    }

    @Override
    public String toString() {
        return "ApiErrorResponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", requestedUrl='" + requestedUrl + '\'' +
                '}';
    }
}
