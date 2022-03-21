package com.github.renatolsjf.authgateway.context.oauth;

import com.github.renatolsjf.authgateway.AuthException;
import com.github.renatolsjf.authgateway.context.session.GatewaySessionContext;

import java.util.Map;
import java.util.UUID;

public class OutgoingOAuthContext extends OAuthContext {

    private static final String CODE_URI = "code_uri";

    public OutgoingOAuthContext(Map<String, String> properties) {
        super(properties);
    }

    @Override
    public String getClientId() {
        return this.as(GatewaySessionContext.class).getTargetId();
    }

    public String getCodeUri() { return this.getProperty(CODE_URI); }
    public void setCodeUri(String codeUri) { this.setProperty(CODE_URI, codeUri); }

    public String generateState() {

        String state = this.getPropertyOrDefault(STATE, "");
        if (!(state.isBlank())) {
            throw new AuthException("OAuth state is already set");
        }

        state = UUID.randomUUID().toString();
        this.setProperty(STATE, state);

        return state;

    }

}
