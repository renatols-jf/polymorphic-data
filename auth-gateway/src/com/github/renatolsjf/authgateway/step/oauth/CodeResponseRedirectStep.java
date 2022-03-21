package com.github.renatolsjf.authgateway.step.oauth;

import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.context.oauth.IncomingOAuthContext;
import com.github.renatolsjf.authgateway.step.AuthStep;

public class CodeResponseRedirectStep extends AuthStep {
    @Override
    protected AuthContext doExecuteStep(AuthContext context) {

        IncomingOAuthContext ioc = context.as(IncomingOAuthContext.class);
        StringBuilder sb = new StringBuilder(ioc.getRedirectUri())
                .append("?code=")
                .append(ioc.getAuthorizationCode())
                .append("&state=")
                .append(ioc.getState());

        System.out.println("REDIRECTING CODE TO SOURCE URL: " + sb.toString());

        return context;

    }
}
