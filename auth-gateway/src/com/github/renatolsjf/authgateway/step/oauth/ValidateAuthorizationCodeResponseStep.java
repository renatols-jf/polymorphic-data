package com.github.renatolsjf.authgateway.step.oauth;

import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.step.AuthStep;

public class ValidateAuthorizationCodeResponseStep extends AuthStep {

    @Override
    protected AuthContext doExecuteStep(AuthContext context) {
        System.out.println("VALIDATE CODE RESPONSE SUCH AS: ERROR CODES, REQUIRED FIELDS, ETC");
        return context;
    }

}
