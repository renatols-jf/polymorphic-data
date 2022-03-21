package com.github.renatolsjf.authgateway.step;

import com.github.renatolsjf.authgateway.context.AuthContext;

import java.util.*;

/**
 * In conjunction with AuthContext (our polymorphic data implementation), AuthStep holds the majority of
 * business logic. It is implemented in a chain of responsability fashion in which the business logic is built
 * through composition. Polymorphic Data was created to support this behavior composition in which each step has
 * to interact with different data and behavior. In a sense, Polymorphic Data can also be seen as a
 * anti-corruption mechanism. The data is always shaped to fit the current context and there is no encapsulation
 * breach.
 */
public abstract class AuthStep {

    protected AuthStep previousStep;

    protected abstract AuthContext doExecuteStep(AuthContext context);

    public final AuthContext executeStep(AuthContext context) {

        if (previousStep != null) {
            context = this.previousStep.executeStep(context);
        }

        return this.doExecuteStep(context);

    }

    public AuthStep next(AuthStep otherStep) {
        otherStep.previousStep = this;
        return otherStep;
    }

}


