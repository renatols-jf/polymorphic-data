package com.github.renatolsjf.authgateway.step.simple;

import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.context.simpletoken.OutgoingSimpleTokenContext;
import com.github.renatolsjf.authgateway.step.AuthStep;

import java.util.UUID;

public class RequestAuthTokenStep extends AuthStep {

    @Override
    protected AuthContext doExecuteStep(AuthContext context) {
        context.as(OutgoingSimpleTokenContext.class).setAuthToken(UUID.randomUUID().toString());
        System.out.println("REQUESTING SIMPLE TOKEN - "
                + context.as(OutgoingSimpleTokenContext.class).getAuthToken());
        return context;
    }

}
