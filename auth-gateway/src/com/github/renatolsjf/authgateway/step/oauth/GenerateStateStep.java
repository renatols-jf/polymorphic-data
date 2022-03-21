package com.github.renatolsjf.authgateway.step.oauth;

import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.context.oauth.OutgoingOAuthContext;
import com.github.renatolsjf.authgateway.step.AuthStep;

public class GenerateStateStep extends AuthStep {

    @Override
    protected AuthContext doExecuteStep(AuthContext context) {
        context.as(OutgoingOAuthContext.class).generateState();
        System.out.println("GENERATING OUTGOING CONTEXT STATE - "
                + context.as(OutgoingOAuthContext.class).getState());
        return context;
    }

}
