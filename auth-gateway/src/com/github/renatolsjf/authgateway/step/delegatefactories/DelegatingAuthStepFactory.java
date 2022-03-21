package com.github.renatolsjf.authgateway.step.delegatefactories;

import com.github.renatolsjf.authgateway.context.Action;
import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.context.Flow;
import com.github.renatolsjf.authgateway.context.session.GatewaySessionContext;
import com.github.renatolsjf.authgateway.step.AuthStep;
import com.github.renatolsjf.authgateway.step.AuthStepFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * One of the mains aspects of behavior composition is how to compose the behavior - duh! While this might
 * seem obvious, and it truly is, sometimes the obvious must also be spoken about. The behavior composition
 * mechanism might not be relevant to the pattern implementation but it's a strong force that drove its creation.
 *
 * This application composes behavior by analysing context information. On this demonstration, mainly four
 * aspects are used: incoming authentication flow, outgoing authentication flow, requested action,
 * and execution phase. The first thing that comes to mind is probably a factory with some (or a lot) of
 * conditionals to compose the chain. The thing is that reflection (kind of) works well on this case.
 *
 * The idea behind the business behavior is to transition between different authentication contexts. In an
 * OAuth 2.0 Authentication Code flow end-to-end, we have to wait for the end service to call us back in order
 * to give a code to the caller. What if we try to connect an OAuth 2.0 partner to a simple token authentication?
 * The code must be returned without a callback.
 *
 * Reflection is really useful (again, kinda) on this type of situation. First, we must understand that
 * each execution phase has to compose its behavior independently of others. So an Authorization Code request
 * should not be aware of how it's actually going to be served on the other end. Still, it's important that each
 * phase understands the overall context to compose the behavior correctly.
 *
 * This factory delegates the composition to other, more specialized factories. Those factories are discovered
 * on runtime based on the request context. For that, those factories have to obey some naming rules:
 *
 * <ul>
 *     <li>
 *         The first package must be the the flow used - those may be different for source and target and
 *         will result in factories from different main packages. This means that each type of flow knows
 *         how to compose behavior for each action and each phase.
 *     <li/>
 *     <li>
 *         The following package identifies if the behavior is to be created for the source (incoming)
 *         or target (outgoing) context. This makes sense because we obviously cannot request
 *         an authorization code to a source context - after all, it was the one that requested it in
 *         the first place.
 *     <li/>
 *     <li>
 *         The following package identifies the execution phase. It's divided in tow phases only: execution
 *         and post-execution. This was created for each context (source / incoming and target / outgoing)
 *         to be able to execution behavior before and after one another. The order is: source->execution,
 *         target->execution, source->post_execution, and target->post_execution. This enables us to chain
 *         calls that would be made in different requests depending on the overall authentication context.
 *     <li/>
 *     <li>
 *         Finally, the factory name must be the same as the action requested (plus DelegateFactory).
 *         This mechanism enables the application to know what kind of behavior fits best for source and for target
 *         based on requested action. This means that, yes, there is a factory for a simple token flow that knows
 *         what to do in case of an OAuth 2.0 Authorization Code flow.
 *     <li/>
 * <ul/>
 *
 * While this might be a clever way to reach the desired behavior, there is scaling concerns. This might
 * prove to be too problematic in a situation where lots of different actions and flows are available.
 * But, as we all know: context is king, there is no silver bullet and I kinda like this solution :)
 *
 * As one last note, don't implement this kind of logic without a higher abstraction. The type of solution
 * should never be exposed directly to client code as it depends on right code structure to work. To depend
 * on such things is not necessarily bad, but to expose it sure is!
 */
public class DelegatingAuthStepFactory extends AuthStepFactory {

    private static final String[] EXECUTION_PHASES = new String[] {
            "source.execution", "target.execution", "source.postexecution", "target.postexecution"
    };
    private static final String DELEGATE_CLASS_PREFIX = "com.github.renatolsjf.authgateway.step.delegatefactories";

    @Override
    public AuthStep createCourseOfAction(AuthContext context) {

        GatewaySessionContext gsc = context.as(GatewaySessionContext.class);
        Flow sourceFlow = gsc.getSourceFlow();
        Flow targetFlow = gsc.getTargetFlow();
        Action action = gsc.getAction();

        AuthStep step = new VoidAuthStep();

        for (String phase: EXECUTION_PHASES) {

            String flowPackage = (phase.startsWith("source")
                                    ? sourceFlow.toString()
                                    : targetFlow.toString())
                    .toLowerCase().replace("_", "");

            String actionClass = camelCase(action.toString());

            String className = DELEGATE_CLASS_PREFIX
                    + "."
                    + flowPackage
                    + "."
                    + phase
                    + "."
                    + actionClass
                    + "DelegateFactory";

            try {
                Constructor<DelegateFactory> c = ((Class<DelegateFactory>) Class.forName(className)).getConstructor();
                DelegateFactory df = c.newInstance();
                step = df.createCourseOfAction(step, context);
            } catch (NoSuchMethodException | ClassNotFoundException | NoClassDefFoundError |
                    InstantiationException | IllegalAccessException | InvocationTargetException e) { }

        }

        return step;

    }

    private String camelCase(String s) {

        String[] parts = s.split("_");

        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].substring(0, 1) + parts[i].substring(1).toLowerCase();
        }

        return String.join("", parts);

    }
}

class VoidAuthStep extends AuthStep {

    @Override
    protected AuthContext doExecuteStep(AuthContext context) {
        return context;
    }

}
