package com.github.renatolsjf.authgateway.step.oauth;

import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.step.AuthStep;

public class ValidateOutgoingContextStep extends AuthStep {

    @Override
    protected AuthContext doExecuteStep(AuthContext context) {
        System.out.println("VALIDATE OUTGOING CONTEXT SUCH AS: GATEWAY CONFIG FOR TARGET");
        return context;
    }

}
