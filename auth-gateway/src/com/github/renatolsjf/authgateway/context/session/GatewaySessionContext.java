package com.github.renatolsjf.authgateway.context.session;

import com.github.renatolsjf.authgateway.context.Action;
import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.context.Flow;

import java.util.Map;

public class GatewaySessionContext extends AuthContext {

    private static final String SOURCE_ID = "source_id";
    private static final String TARGET_ID = "target_id";
    private static final String ACTION = "action";
    private static final String SOURCE_FLOW = "source_flow";
    private static final String TARGET_FLOW = "target_flow";
    private static final String USER_PROFILE_AVAILABLE = "user_profile_available";

    public GatewaySessionContext(Map<String, String> properties) {
        super(properties);
    }

    public String getSourceId() { return this.getSharedProperty(SOURCE_ID); }
    public void setSourceId(String id) { this.setSharedProperty(SOURCE_ID, id); }

    public String getTargetId() { return this.getSharedProperty(TARGET_ID); }
    public void setTargetId(String id) { this.setSharedProperty(TARGET_ID, id); }

    public Action getAction() { return Action.valueOf(this.getProperty(ACTION)); }
    public void setAction(Action action) { this.setProperty(ACTION, action.toString()); }

    public Flow getSourceFlow() { return Flow.valueOf(this.getProperty(SOURCE_FLOW)); }
    public void setSourceFLow(Flow flow) { this.setProperty(SOURCE_FLOW, flow.toString()); }

    public Flow getTargetFlow() { return Flow.valueOf(this.getProperty(TARGET_FLOW)); }
    public void setTargetFLow(Flow flow) { this.setProperty(TARGET_FLOW, flow.toString()); }

    public boolean isUserProfileAvailable() {
        return Boolean.valueOf(this.getPropertyOrDefault(USER_PROFILE_AVAILABLE, "false"));
    }

    public void setUserProfileAvailable(boolean userProfileAvailable) {
        this.setProperty(USER_PROFILE_AVAILABLE, String.valueOf(userProfileAvailable));
    }


}
