package com.github.renatolsjf.authgateway.step.oauth;

import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.context.oauth.OutgoingOAuthContext;
import com.github.renatolsjf.authgateway.step.AuthStep;

public class CodeRequestRedirectStep extends AuthStep {

    @Override
    protected AuthContext doExecuteStep(AuthContext context) {

        OutgoingOAuthContext ooc = context.as(OutgoingOAuthContext.class);
        StringBuilder sb = new StringBuilder(ooc.getCodeUri())
                .append("?client_id=")
                .append(ooc.getClientId())
                .append("&state=")
                .append(ooc.getState())
                .append("&response_type=code")
                .append("&redirect_uri=")
                .append(ooc.getRedirectUri());

        System.out.println("REDIRECTING CODE REQUEST TO TARGET URL: " + sb.toString());

        return context;

    }

}
