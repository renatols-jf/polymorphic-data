package com.github.renatolsjf.authgateway.context.oauth;

import com.github.renatolsjf.authgateway.context.session.GatewaySessionContext;

import java.util.Map;
import java.util.UUID;

public class IncomingOAuthContext extends OAuthContext {

    public IncomingOAuthContext(Map<String, String> properties) {
        super(properties);
    }

    @Override
    public String getClientId() {
        return this.as(GatewaySessionContext.class).getSourceId();
    }

    public void setState(String state) { this.setProperty(STATE, state); }

    public void generateAuthorizationCode() {
        this.setAuthorizationCode(UUID.randomUUID().toString()); //Depending on the needs, a delegation strategy could be a better choice.
    }

    public boolean isCodeValid() {
        System.out.println("VALIDATE IF CODE IS PRESENT, ACCESS TOKEN HAS NOT BEEN RETRIEVED, CODE WAS ISSUED IN LAST MINUTE, ETC");
        return true;
    }

}
