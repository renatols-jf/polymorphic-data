package com.github.renatolsjf.authgateway.context.simpletoken;

import com.github.renatolsjf.authgateway.context.AuthContext;

import java.util.Map;

abstract class SimpleTokenContext extends AuthContext {

    protected static final String AUTH_TOKEN = "auth_token";

    protected SimpleTokenContext(Map<String, String> properties) {
        super(properties);
    }

    public boolean isUserLoggedIn() {
        return !(this.getPropertyOrDefault(AUTH_TOKEN, "").isBlank());
    }


    public String getAuthToken() { return this.getProperty(AUTH_TOKEN); }
    public void setAuthToken(String token) { this.setProperty(AUTH_TOKEN, token); }

    public abstract String getPartnerToke();


}
