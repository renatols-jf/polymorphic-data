package com.github.renatolsjf.authgateway.step.delegatefactories.oauthauthorizationcode.target.execution;

import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.step.AuthStep;
import com.github.renatolsjf.authgateway.step.delegatefactories.DelegateFactory;
import com.github.renatolsjf.authgateway.step.oauth.ValidateAuthorizationCodeResponseStep;
import com.github.renatolsjf.authgateway.step.oauth.ValidateOutgoingContextStep;

public class OauthCodeResponseDelegateFactory extends DelegateFactory {
    @Override
    public AuthStep createCourseOfAction(AuthStep step, AuthContext context) {
        return step.next(new ValidateOutgoingContextStep())
                .next(new ValidateAuthorizationCodeResponseStep());
    }
}
