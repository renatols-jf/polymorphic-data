package com.github.renatolsjf.authgateway.step.oauth;

import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.context.oauth.IncomingOAuthContext;
import com.github.renatolsjf.authgateway.step.AuthStep;

import java.util.UUID;

public class GenerateIncomingCodeStep extends AuthStep {
    @Override
    protected AuthContext doExecuteStep(AuthContext context) {
        context.as(IncomingOAuthContext.class).generateAuthorizationCode();
        System.out.println("GENERATING AUTHORIZATION CODE FOR INCOMING CONTEXT - "
                + context.as(IncomingOAuthContext.class).getAuthorizationCode());
        return context;
    }
}
