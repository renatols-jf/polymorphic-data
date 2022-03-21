package com.github.renatolsjf.authgateway.step.delegatefactories.oauthauthorizationcode.source.execution;

import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.step.AuthStep;
import com.github.renatolsjf.authgateway.step.delegatefactories.DelegateFactory;
import com.github.renatolsjf.authgateway.step.oauth.ValidateAuthorizationCodeStep;
import com.github.renatolsjf.authgateway.step.oauth.ValidateIncomingContextStep;

public class OauthTokenRequestDelegateFactory extends DelegateFactory {
    @Override
    public AuthStep createCourseOfAction(AuthStep step, AuthContext context) {
        return step.next(new ValidateIncomingContextStep())
                .next(new ValidateAuthorizationCodeStep());
    }
}
