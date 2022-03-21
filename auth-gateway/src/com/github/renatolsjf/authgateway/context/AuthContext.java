package com.github.renatolsjf.authgateway.context;

import com.github.renatolsjf.authgateway.AuthException;
import com.github.renatolsjf.authgateway.polymorphic.core.PolymorphicData;
import com.github.renatolsjf.authgateway.step.AuthStepFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Our Polymorphic Data implementation, or rather, its main base. The way the pattern is built on this demonstration
 * is based on two structural foundations. The PolymorphicData abstraction serves as a basis for the creation of
 * many types of different kinds of data. The actual AuthContext abstraction serves as a basis for creating
 * authentication related data. Basically, we could have two or more separated sets of data in which each set could
 * only be transformed in to an implementation contained inside its own set.
 */
public abstract class AuthContext extends PolymorphicData<AuthContext> {

    protected AuthContext(Map<String, String> properties) {
        super(properties);
    }

    public AuthContext execute() {
        return AuthStepFactory.instance().createCourseOfAction(this).executeStep(this);
    }

    public static AuthContext newContext() {
        return new AuthContext(new HashMap<>()) {};
    }

}
