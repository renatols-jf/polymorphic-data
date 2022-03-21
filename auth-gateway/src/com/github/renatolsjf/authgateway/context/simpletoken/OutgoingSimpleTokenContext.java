package com.github.renatolsjf.authgateway.context.simpletoken;

import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.context.session.GatewaySessionContext;

import java.util.Map;

public class OutgoingSimpleTokenContext extends SimpleTokenContext {

    public OutgoingSimpleTokenContext(Map<String, String> properties) {
        super(properties);
    }

    @Override
    public String getPartnerToke() {
        return this.as(GatewaySessionContext.class).getTargetId();
    }

}
