package com.github.renatolsjf.authgateway.step.oauth;

import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.step.AuthStep;

public class ValidateIncomingContextStep extends AuthStep {

    @Override
    protected AuthContext doExecuteStep(AuthContext context) {
        System.out.println("VALIDATE INCOMING CONTEXT SUCH AS: GATEWAY CONFIG FOR SOURCE, CORRECT REDIRECT_URI, STATE PRESENT, ETC");
        return context;
    }

}
