package com.github.renatolsjf.authgateway.step.simple;

import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.context.simpletoken.IncomingSimpleTokenContext;
import com.github.renatolsjf.authgateway.step.AuthStep;

public class GenerateSimpleTokenStep extends AuthStep {

    @Override
    protected AuthContext doExecuteStep(AuthContext context) {
        context.as(IncomingSimpleTokenContext.class).generateSimpleToken();
        System.out.println("GENERATING INCOMING SIMPLE TOKEN - "
                + context.as(IncomingSimpleTokenContext.class).getAuthToken());
        return context;
    }

}
