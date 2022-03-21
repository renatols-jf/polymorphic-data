package com.github.renatolsjf.authgateway;

import com.github.renatolsjf.authgateway.context.Action;
import com.github.renatolsjf.authgateway.context.AuthContext;
import com.github.renatolsjf.authgateway.context.Flow;
import com.github.renatolsjf.authgateway.context.oauth.IncomingOAuthContext;
import com.github.renatolsjf.authgateway.context.oauth.OutgoingOAuthContext;
import com.github.renatolsjf.authgateway.context.session.GatewaySessionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * As the class name says, this is just some general and random application logic. Normally, this
 * behavior would be scattered among a diversity of classes, there would be better treatment, etc.
 * The purpose of this demo is to show the polymorphic data applicability and, as such, this class
 * is of no importance to the actual pattern. In essence, this is just glue code.
 */
public class JustSomeRandomApplicationLogicMock {

    private static final String SRC_OAUTH_TO_OAUTH_ID = "2cdbc56e-6389-4060-88bf-82557faa92b8";
    private static final String SRC_OUATH_TO_SIMPLE_TOKEN_ID = "308203f3-948e-4a13-86da-a38fa28d18a6";
    private static final String SRC_SIMPLE_TOKEN_TO_OAUTH_ID = "90acfe01-5657-4729-800c-2a5222a7cfa6";
    private static final String SRC_SIMPLE_TOKEN_TO_SIMPLE_TOKEN = "2f5a8220-5aee-4123-82b7-8e3f1ec64288";
    private static final String SRC_REDIRECT_URI = "https://random-src.com/code";

    private static final String TARGET_OAUTH_ID = "89e80b85-fbae-45a2-9758-6b22ff808321";
    private static final String TARGET_REDIRECT_URI = "https://gateway.com/code";

    private static final String TARGET_SIMPLE_TOKEN_ID = "4e72d4db-f8ea-46f2-be75-674bf8888201";

    private final List<AuthContext> contexts = new ArrayList<>();

    public String code(String sourceId, String state) {

        if (SRC_OAUTH_TO_OAUTH_ID.equals(sourceId)) {

            GatewaySessionContext gsc = AuthContext.newContext().as(GatewaySessionContext.class);
            gsc.setSourceId(sourceId);
            gsc.setTargetId(TARGET_OAUTH_ID);
            gsc.setSourceFLow(Flow.OAUTH_AUTHORIZATION_CODE);
            gsc.setTargetFLow(Flow.OAUTH_AUTHORIZATION_CODE);
            gsc.setAction(Action.OAUTH_CODE_REQUEST);
            gsc.setUserProfileAvailable(false);

            IncomingOAuthContext ioc = gsc.as(IncomingOAuthContext.class);
            ioc.setState(state);
            ioc.setRedirectUri(SRC_REDIRECT_URI);

            OutgoingOAuthContext ooc = ioc.as(OutgoingOAuthContext.class); //or gsc.as(OutgoingOAuthContext.class)
            ooc.setCodeUri("http://sample-oauth.ssoprovider.com/authorize");
            ooc.setRedirectUri(TARGET_REDIRECT_URI);

            contexts.add(ooc);

            try {
                return ooc.execute().as(OutgoingOAuthContext.class).getState();
            } catch (AuthException e) {
                System.out.println(e.getMessage());
                return null;
            }

        } else if (SRC_OUATH_TO_SIMPLE_TOKEN_ID.equals(sourceId)) {

            GatewaySessionContext gsc = AuthContext.newContext().as(GatewaySessionContext.class);
            gsc.setSourceId(sourceId);
            gsc.setTargetId(TARGET_SIMPLE_TOKEN_ID);
            gsc.setSourceFLow(Flow.OAUTH_AUTHORIZATION_CODE);
            gsc.setTargetFLow(Flow.SIMPLE_TOKEN);
            gsc.setAction(Action.OAUTH_CODE_REQUEST);
            gsc.setUserProfileAvailable(true);

            IncomingOAuthContext ioc = gsc.as(IncomingOAuthContext.class);
            ioc.setState(state);
            ioc.setRedirectUri(SRC_REDIRECT_URI);

            contexts.add(ioc);

            try {
                return ioc.execute().as(IncomingOAuthContext.class).getAuthorizationCode();
            } catch (AuthException e) {
                System.out.println(e.getMessage());
                return null;
            }

        } else {
            throw new AuthException("CLIENT_ID NOT RECOGNIZED");
        }

    }

    public String codeResponse(String code, String state) {

        Optional<AuthContext> o = this.contexts.stream()
                .filter(c -> Objects.equals(c.as(OutgoingOAuthContext.class).getState(), state))
                .findFirst();

        if (!o.isPresent()) {
            throw new AuthException("STATE NOT FOUND");
        }

        OutgoingOAuthContext ooc = o.get().as(OutgoingOAuthContext.class);
        ooc.setAuthorizationCode(code);

        GatewaySessionContext gsc = ooc.as(GatewaySessionContext.class);
        gsc.setAction(Action.OAUTH_CODE_RESPONSE);

        try {
            return gsc.execute().as(IncomingOAuthContext.class).getAuthorizationCode();
        } catch (AuthException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public String oauthToken(String code) {

        Optional<AuthContext> o = this.contexts.stream()
                .filter(c -> Objects.equals(c.as(IncomingOAuthContext.class).getAuthorizationCode(), code))
                .findFirst();

        if (!o.isPresent()) {
            throw new AuthException("CODE NOT FOUND");
        }

        GatewaySessionContext gsc = o.get().as(GatewaySessionContext.class);
        gsc.setAction(Action.OAUTH_TOKEN_REQUEST);

        try {
            return gsc.execute().as(IncomingOAuthContext.class).getAccessToken();
        } catch (AuthException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public String simpleToken(String partnerId) {

        if (SRC_SIMPLE_TOKEN_TO_OAUTH_ID.equals(partnerId)) {

            GatewaySessionContext gsc = AuthContext.newContext().as(GatewaySessionContext.class);
            gsc.setSourceId(partnerId);
            gsc.setTargetId(TARGET_OAUTH_ID);
            gsc.setSourceFLow(Flow.SIMPLE_TOKEN);
            gsc.setTargetFLow(Flow.OAUTH_AUTHORIZATION_CODE);
            gsc.setAction(Action.SIMPLE_TOKEN_REQUEST);
            gsc.setUserProfileAvailable(false);

            OutgoingOAuthContext ooc = gsc.as(OutgoingOAuthContext.class); //or gsc.as(OutgoingOAuthContext.class)
            ooc.setCodeUri("http://sample-oauth.ssoprovider.com/authorize");
            ooc.setRedirectUri(TARGET_REDIRECT_URI);

            contexts.add(ooc);

            try {
                return ooc.execute().as(OutgoingOAuthContext.class).getState();
            } catch (AuthException e) {
                System.out.println(e.getMessage());
                return null;
            }

        } else if (SRC_SIMPLE_TOKEN_TO_SIMPLE_TOKEN.equals(partnerId)) {

            GatewaySessionContext gsc = AuthContext.newContext().as(GatewaySessionContext.class);
            gsc.setSourceId(partnerId);
            gsc.setTargetId(TARGET_SIMPLE_TOKEN_ID);
            gsc.setSourceFLow(Flow.SIMPLE_TOKEN);
            gsc.setTargetFLow(Flow.SIMPLE_TOKEN);
            gsc.setAction(Action.SIMPLE_TOKEN_REQUEST);
            gsc.setUserProfileAvailable(false);

            contexts.add(gsc);

            try {
                return gsc.execute().as(OutgoingOAuthContext.class).getState();
            } catch (AuthException e) {
                System.out.println(e.getMessage());
                return null;
            }

        } else {
            throw new AuthException("PARTNER_ID NOT RECOGNIZED");
        }

    }

}
