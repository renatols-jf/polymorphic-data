package com.github.renatolsjf.authgateway.context.oauth;

public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException() { super(); }
    public TokenExpiredException(String message) { super(message); }
    public TokenExpiredException(String message, Throwable cause) { super(message, cause); }

}
