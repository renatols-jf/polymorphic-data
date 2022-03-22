package com.github.renatolsjf.authgateway.step.delegatefactories.oauthauthorizationcode.target.execution;

import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.step.AuthStep;
import com.github.renatolsjf.authgateway.step.delegatefactories.DelegateFactory;
import com.github.renatolsjf.authgateway.step.oauth.CodeRequestRedirectStep;
import com.github.renatolsjf.authgateway.step.oauth.GenerateStateStep;
import com.github.renatolsjf.authgateway.step.oauth.ValidateOutgoingContextStep;

public class OauthCodeRequestDelegateFactory extends DelegateFactory {

    @Override
    public AuthStep createCourseOfAction(AuthStep step, AuthContext context) {
        return step.next(new ValidateOutgoingContextStep())
                .next(new GenerateStateStep())
                .next(new CodeRequestRedirectStep());
    }

}
