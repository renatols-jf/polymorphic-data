package com.github.renatolsjf.authgateway.context;

public class UserNotSignedInException extends RuntimeException {

    public UserNotSignedInException() { super(); }
    public UserNotSignedInException(String message) { super(message); }
    public UserNotSignedInException(String message, Throwable cause) { super(message, cause); }

}
