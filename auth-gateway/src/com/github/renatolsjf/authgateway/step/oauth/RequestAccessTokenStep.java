package com.github.renatolsjf.authgateway.step.oauth;

import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.context.oauth.OutgoingOAuthContext;
import com.github.renatolsjf.authgateway.step.AuthStep;

import java.util.UUID;

public class RequestAccessTokenStep extends AuthStep {

    @Override
    protected AuthContext doExecuteStep(AuthContext context) {
        context.as(OutgoingOAuthContext.class).setOAuthContext(UUID.randomUUID().toString(),
                UUID.randomUUID().toString(), System.currentTimeMillis(), 3600000);
        System.out.println("REQUESTING OUTGOING TOKEN - "
                + context.as(OutgoingOAuthContext.class).getAccessToken());
        return context;
    }

}
