package com.github.renatolsjf.authgateway.step.oauth;

import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.context.oauth.IncomingOAuthContext;
import com.github.renatolsjf.authgateway.step.AuthStep;

import java.util.UUID;

public class GenerateAccessTokenStep extends AuthStep {
    @Override
    protected AuthContext doExecuteStep(AuthContext context) {
        context.as(IncomingOAuthContext.class).setOAuthContext(UUID.randomUUID().toString(),
                UUID.randomUUID().toString(), System.currentTimeMillis(), 3600000);
        System.out.println("GENERATING INCOMING ACCESS TOKEN - "
                + context.as(IncomingOAuthContext.class).getAccessToken());
        return context;
    }
}
