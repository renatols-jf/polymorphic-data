package com.github.renatolsjf.authgateway.step.delegatefactories.simpletoken.target.execution;

import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.step.AuthStep;
import com.github.renatolsjf.authgateway.step.delegatefactories.DelegateFactory;
import com.github.renatolsjf.authgateway.step.simple.RequestAuthTokenStep;
import com.github.renatolsjf.authgateway.step.simple.ValidateOutgoingContextStep;

public class OauthCodeRequestDelegateFactory extends DelegateFactory {

    @Override
    public AuthStep createCourseOfAction(AuthStep step, AuthContext context) {
        return step.next(new ValidateOutgoingContextStep())
                .next(new RequestAuthTokenStep());
    }

}
