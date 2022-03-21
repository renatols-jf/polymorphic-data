package com.github.renatolsjf.authgateway.step.simple;

import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.step.AuthStep;

public class ValidateOutgoingContextStep extends AuthStep {

    @Override
    protected AuthContext doExecuteStep(AuthContext context) {
        System.out.println("VALIDATE OUTGOING SIMPLE TOKEN CONTEXT SUCH AS PARTNER ID, ETC");
        return context;
    }

}
