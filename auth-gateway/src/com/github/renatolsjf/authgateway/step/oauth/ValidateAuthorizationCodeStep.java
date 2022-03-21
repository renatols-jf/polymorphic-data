package com.github.renatolsjf.authgateway.step.oauth;

import com.github.renatolsjf.authgateway.AuthException;
import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.context.oauth.IncomingOAuthContext;
import com.github.renatolsjf.authgateway.step.AuthStep;

public class ValidateAuthorizationCodeStep extends AuthStep {

    @Override
    protected AuthContext doExecuteStep(AuthContext context) {
        if (!context.as(IncomingOAuthContext.class).isCodeValid()) {
            throw new AuthException("INVALID CONTEXT");
        }
        return context;
    }

}
