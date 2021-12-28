package com.exceptions;

import com.constants.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Global exception handler.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private HttpServletRequest request;

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ex.printStackTrace();
        ApiErrorResponse response = new ApiErrorResponse(ex.getLocalizedMessage(), status, getRequestedUrl());
        log.error(AppConstant.LOG_ERROR,response);
        return ResponseEntity.status(response.getStatus().value()).body(response);
    }

    /**
     * Handle header not found exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler({HeaderNotFoundException.class})
    public ResponseEntity<Object> handleHeaderNotFoundException(HeaderNotFoundException ex, WebRequest request){
        ex.printStackTrace();
        ApiErrorResponse response = new ApiErrorResponse(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST, getRequestedUrl());
        log.error(AppConstant.LOG_ERROR,response);
        return ResponseEntity.status(response.getStatus().value()).body(response);
    }

    /**
     * Handle token verification failed exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler({TokenVerificationFailedException.class})
    public ResponseEntity<Object> handleTokenVerificationFailedException(TokenVerificationFailedException ex, WebRequest request){
        ex.printStackTrace();
        ApiErrorResponse response = new ApiErrorResponse(ex.getLocalizedMessage(), HttpStatus.UNAUTHORIZED, getRequestedUrl());
        log.error(AppConstant.LOG_ERROR,response);
        return ResponseEntity.status(response.getStatus().value()).body(response);
    }

    /**
     * Handle http client error exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException ex, WebRequest request){
        ex.printStackTrace();
        ApiErrorResponse response = new ApiErrorResponse(ex.getLocalizedMessage(), ex.getStatusCode(), getRequestedUrl());
        log.error(AppConstant.LOG_ERROR,response);
        return ResponseEntity.status(response.getStatus().value()).body(response);
    }

    /**
     * Handle phr exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler({ApplicationException.class})
    public ResponseEntity<Object> handlePHRException(ApplicationException ex, WebRequest request){
        ex.printStackTrace();
        ApiErrorResponse response = new ApiErrorResponse(ex.getMessage(), ex.getHttpStatusCode(), getRequestedUrl());
        log.error(AppConstant.LOG_ERROR,response);
        return ResponseEntity.status(response.getStatus().value()).body(response);
    }

    /**
     * Handle all exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request){
        ex.printStackTrace();
        ApiErrorResponse response = new ApiErrorResponse(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR, getRequestedUrl());
        log.error(AppConstant.LOG_ERROR,response);
        return ResponseEntity.status(response.getStatus().value()).body(response);
    }

    private String getRequestedUrl(){
        return this.request.getRequestURL().toString();
    }

}
