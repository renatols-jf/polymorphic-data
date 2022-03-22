package com.github.renatolsjf.authgateway.step.delegatefactories.oauthauthorizationcode.target.execution;

import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.AuthException;
import com.github.renatolsjf.authgateway.step.AuthStep;
import com.github.renatolsjf.authgateway.step.delegatefactories.DelegateFactory;

public class SimpleTokenRequestDelegateFactory extends DelegateFactory {

    @Override
    public AuthStep createCourseOfAction(AuthStep step, AuthContext context) {
        throw new AuthException("ERROR: INCOMPATIBLE SOURCE / TARGET FLOW COMBINATION");
    }

}
