package com.exceptions;


/**
 * The type Token verification failed exception.
 */
public class TokenVerificationFailedException extends RuntimeException{

    /**
     * Instantiates a new Token verification failed exception.
     */
    public TokenVerificationFailedException() {
        super();
    }

    /**
     * Instantiates a new Token verification failed exception.
     *
     * @param message the message
     */
    public TokenVerificationFailedException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Token verification failed exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public TokenVerificationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Token verification failed exception.
     *
     * @param cause the cause
     */
    public TokenVerificationFailedException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Token verification failed exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public TokenVerificationFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
