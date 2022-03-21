package com.github.renatolsjf.authgateway.context.simpletoken;

import com.github.renatolsjf.authgateway.AuthException;
import com.github.renatolsjf.authgateway.context.session.GatewaySessionContext;

import java.util.Map;
import java.util.UUID;

public class IncomingSimpleTokenContext extends SimpleTokenContext{

    public IncomingSimpleTokenContext(Map<String, String> properties) {
        super(properties);
    }

    @Override
    public String getPartnerToke() {
        return this.as(GatewaySessionContext.class).getSourceId();
    }

    public void generateSimpleToken() {
        if (this.isUserLoggedIn()) {
            throw new AuthException("USER IS LOGGED IN");
        }
        this.setAuthToken(UUID.randomUUID().toString());
    }

}
