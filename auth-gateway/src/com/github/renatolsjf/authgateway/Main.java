package com.github.renatolsjf.authgateway;

import java.util.UUID;

/**
 * This application shows the applicability of Polymorphic Data. It is based on the real world use case that motivated
 * the structure / pattern, albeit simplified and missing all of it actual business logic. It is based on an
 * authentication gateway that needed to connect content providers to service (identity) providers in which
 * different authentication contexts could be used. For more information on this authentication gateway, visit one of
 * the following links:
 *
 * @see <a href="https://dev.to/renatolsjf/en-us-modeling-an-oo-domain-a-case-study-about-the-creation-of-a-model-for-an-authentication-and-authorization-gateway-3o9j">[en-US] Modeling an OO domain: a case study about the creation of a model for an authentication and authorization gateway.</a>
 * @see <a href="https://dev.to/renatolsjf/pt-br-modelagem-de-um-dominio-oo-um-estudo-de-caso-sobre-a-criacao-de-um-modelo-para-um-gateway-de-autenticacao-e-autorizacao-5ab3">[pt-BR] Modelagem de um domínio OO: um estudo de caso sobre a criação de um modelo para um gateway de autenticação e autorização</a>
 */
public class Main {

    public static void main(String... args) {

        JustSomeRandomApplicationLogicMock j = new JustSomeRandomApplicationLogicMock();

        System.out.println("OAUTH X OUATH FLOW");

        System.out.println("1. CODE REQUEST (INITIATED BY SOURCE) -------------------");
        String state = j.code("2cdbc56e-6389-4060-88bf-82557faa92b8",
                "UjennJOI39snNJSK2=-n*I2");

        System.out.println("2. CODE RESPONSE (INITIATED BY TARGET) ------------------");
        String code = j.codeResponse(UUID.randomUUID().toString(), state);

        System.out.println("3. ACCESS TOKEN REQUEST (INITIATED BY SOURCE) -----------");
        String token = j.oauthToken(code);

        System.out.println();
        System.out.println("OAUTH X CUSTOM / SIMPLE TOKEN");

        System.out.println("1. CODE REQUEST (INITIATED BY SOURCE) -------------------");
        code = j.code("308203f3-948e-4a13-86da-a38fa28d18a6",
                "uudi8iYEJS==ldue");

        System.out.println("2. ACCESS TOKEN REQUEST (INITIATED BY SOURCE) -----------");
        token = j.oauthToken(code);

        System.out.println();
        System.out.println("CUSTOM / SIMPLE TOKEN X OAUTH");

        System.out.println("1. REQUEST SIMPLE TOKEN (INITIATED BY SOURCE) -----------");
        token = j.simpleToken("90acfe01-5657-4729-800c-2a5222a7cfa6");

        System.out.println();
        System.out.println("CUSTOM / SIMPLE TOKEN X CUSTOM / SIMPLE TOKEN");

        System.out.println("1. REQUEST SIMPLE TOKEN (INITIATED BY SOURCE) -----------");
        token = j.simpleToken("2f5a8220-5aee-4123-82b7-8e3f1ec64288");

    }

}
