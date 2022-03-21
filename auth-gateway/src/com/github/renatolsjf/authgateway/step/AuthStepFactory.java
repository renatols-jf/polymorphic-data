package com.github.renatolsjf.authgateway.step;

import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.step.delegatefactories.DelegatingAuthStepFactory;

/**
 * Just some abstract factory, nothing to see here - or is there? More details on its implementation :)
 */
public abstract class AuthStepFactory {

    public static AuthStepFactory instance() {
        return new DelegatingAuthStepFactory();
    }

    public abstract AuthStep createCourseOfAction(AuthContext context);

}
