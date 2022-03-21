package com.github.renatolsjf.authgateway;

/**
 * Random generic unchecked Authentication Exception. Probably not the best approach, but not relevant
 * to the demonstration either.
 */
public class AuthException extends RuntimeException {

    public AuthException(){}
    public AuthException(String message) { super(message); }
    public AuthException(String message, Throwable cause) { super(message, cause); }

}
