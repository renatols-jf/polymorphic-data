package com.github.renatolsjf.authgateway.context.oauth;

import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.context.UserNotSignedInException;

import java.util.Map;

abstract class OAuthContext extends AuthContext {

    protected static final String REDIRECT_URI = "redirect_uri";

    protected static final String STATE = "state";
    protected static final String AUTHORIZATION_CODE = "authorization_code";
    protected static final String ACCESS_TOKEN = "access_token";
    protected static final String REFRESH_TOKEN = "refresh_token";
    protected static final String ISSUED_ON = "issued_on";
    protected static final String EXPIRES_IN = "expires_in";

    protected OAuthContext(Map<String, String> properties) {
        super(properties);
    }

    protected long issuedOn() {
        return Long.valueOf(this.getPropertyOrDefault(ISSUED_ON, "0"));
    }

    protected long expiresIn() {
        return Long.valueOf(this.getPropertyOrDefault(EXPIRES_IN, "0")) * 1000;
    }

    public String getRedirectUri() { return this.getProperty(REDIRECT_URI); }
    public void setRedirectUri(String redirectUri) { this.setProperty(REDIRECT_URI, redirectUri); }

    public String getAuthorizationCode() { return this.getProperty(AUTHORIZATION_CODE); }
    public void setAuthorizationCode(String code) { this.setProperty(AUTHORIZATION_CODE, code); }

    public String getState(){ return this.getProperty(STATE); }

    public String getAccessToken() { return this.getToken(ACCESS_TOKEN); }

    public String getRefreshToken() { return this.getToken(REFRESH_TOKEN); }

    protected String getToken(String tokenType) {

        if (this.isExpired()) {
            throw new TokenExpiredException();
        }

        String s = this.getPropertyOrDefault(tokenType, "");

        if (s.isBlank()) {
            throw new UserNotSignedInException("Access Token not available");
        }

        return s;

    }

    public void setOAuthContext(String accesToken, String refreshToken, long issuedOn, long expiresIn) {
        this.setProperty(ACCESS_TOKEN, accesToken);
        this.setProperty(REFRESH_TOKEN, refreshToken);
        this.setProperty(ISSUED_ON, String.valueOf(issuedOn));
        this.setProperty(EXPIRES_IN, String.valueOf(expiresIn));
    }

    public boolean isExpired() {
        return this.issuedOn() + this.expiresIn() < System.currentTimeMillis();
    }

    public abstract String getClientId();

}
