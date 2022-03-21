package com.github.renatolsjf.authgateway.step.delegatefactories.oauthauthorizationcode.source.postexecution;

import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.step.AuthStep;
import com.github.renatolsjf.authgateway.step.delegatefactories.DelegateFactory;
import com.github.renatolsjf.authgateway.step.oauth.GenerateAccessTokenStep;

public class OauthTokenRequestDelegateFactory extends DelegateFactory {

    @Override
    public AuthStep createCourseOfAction(AuthStep step, AuthContext context) {
        return step.next(new GenerateAccessTokenStep());
    }

}
