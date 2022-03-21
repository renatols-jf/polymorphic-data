package com.github.renatolsjf.authgateway.step.delegatefactories.simpletoken.source.postexecution;

import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.step.AuthStep;
import com.github.renatolsjf.authgateway.step.delegatefactories.DelegateFactory;
import com.github.renatolsjf.authgateway.step.simple.GenerateSimpleTokenStep;

public class SimpleTokenRequestDelegateFactory extends DelegateFactory {

    @Override
    public AuthStep createCourseOfAction(AuthStep step, AuthContext context) {
        return step.next(new GenerateSimpleTokenStep());
    }

}
