package com.github.renatolsjf.authgateway.step.delegatefactories;

import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.step.AuthStep;

/**
 * The composition happens here but the magic is elsewhere :(
 */
public abstract class DelegateFactory {

    public abstract AuthStep createCourseOfAction(AuthStep step, AuthContext context);

}


