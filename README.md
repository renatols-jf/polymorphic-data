# Polymorphic Data

Polymorphic Data is (an attempt to create) a pattern to solving data sharing among different contexts and needs. It favors encapsulation and high cohesion to share a source of truth that needs to be shaped accordingly to the context. This repository presents a brief proof of concept for the idea.

## (Attempted) Pattern contextualization

**Context:** To solve a given problem, you need to execute a set of steps that is not known before runtime evaluation. The steps are diverse and often work on different types of information to update a higher shared context. 

**Problem:** Patterns such as strategy and chain of responsibility are adequate for this scenario but have one shortcoming - they require the same parameters for each strategy/step. Albeit a map would work, it exposes the data and creates an anemic model.

**Forces:** We need a way to provide different information to different steps so each step can collaborate to work on and update a higher shared context.

**Solution:** Polymorphic Data acts as a single repository/source of truth to all contextual information while exposing only the behavior and information related to a given bounded context. To do so, Polymorphic Data morphs into one of its implementations exposing only the adequate correlated subset of information. This enables data to be shared while maintaining high cohesion for each step - each step requests the data to be transformed into the desired kind, exposing only desired behavior. The result is that each step can collaborate to change an overall context without actually understanding the higher picture.


## Demo structure

### Application purpose
This demo is based on the real-world use case that motivated the pattern creation. This proof of concept acts as an authentication and authorization gateway in which the service must act as a middle man to translate between different authentication and authorization contexts (OAuth, SAML, Basic Authentication, etc) for API consumption.

### Polymorphic Data
In this proof of concept, the pattern is implemented using two layers of abstraction: one to represent a polymorphic structure and the other to represent the actual overall context to be shared (in this case, information about authentication and authorization). This is done so that there could be two or more different universes of data that could implement the pattern and, yet, a subset of a universe be only morphed to a kind of its own universe. Be that as it may, it's not a requirement nor a necessity.

### Application Design
To reach its goal, the application needs to execute a set of steps in a chain of responsibility fashion. The steps needed to attain a goal are evaluated at runtime, based on the overall context. This promotes behavior composition.

The behavior composition mechanism might not be relevant to the pattern implementation but it's a strong force that drove its creation.

This application composes behavior by analyzing context information. In this demonstration, mainly four aspects are used: incoming authentication flow, outgoing authentication flow, requested action, and execution phase. The first thing that comes to mind is probably a factory with some (or a lot) of conditionals to compose the chain. The thing is that reflection (kind of) works well in this case.

The idea behind the business behavior is to transition between different authentication contexts. In an OAuth 2.0 Authentication Code flow end-to-end, we have to wait for the end service to call us back to give a code to the caller. What if we try to connect an OAuth 2.0 partner to a simple token authentication? The code must be returned without a callback.

Reflection is really useful (again, kinda) in this type of situation. First, we must understand that each execution phase has to compose its behavior independently of others. So an Authorization Code request should not be aware of how it's going to be served on the other end. Still, each phase must understand the overall context to compose the behavior correctly.

This factory delegates the composition to other, more specialized factories. Those factories are discovered on runtime based on the request context. For that, those factories have to obey some naming rules:

* The first package must be the flow used - those may be different for source and target and will result in factories from different main packages. This means that each type of flow knows how to compose behavior for each action and each phase.

* The following package identifies if the behavior is to be created for the source (incoming) or target (outgoing) context. This makes sense because we obviously cannot request an authorization code to a source context - after all, it was the one that requested it in the first place.

* The next package identifies the execution phase. It's divided into two phases only: execution and post-execution. This was created for each context (source/incoming and target/outgoing) to be able to execute behavior before and after one another. The order is source->execution, target->execution, source->post_execution, and target->post_execution. This enables us to chain calls that would be made in different requests depending on the overall authentication context.

* Finally, the factory name must be the same as the action requested (plus DelegateFactory). This mechanism enables the application to know what kind of behavior fits best for the source and for the target based on the requested action. This means that, yes, there is a factory for a simple token flow that knows what to do in case of an OAuth 2.0 Authorization Code flow.

While this might be a clever way to reach the desired behavior, there are scaling concerns. This might prove to be too problematic in a situation where lots of different actions and flows are available. But, as we all know: context is king, there is no silver bullet, and I kinda like this solution! :)

As one last note, don't implement this kind of logic without a higher abstraction. The type of solution should never be exposed directly to client code as it depends on the right code structure to work. To depend on such things is not necessarily bad, but to expose it sure is!
