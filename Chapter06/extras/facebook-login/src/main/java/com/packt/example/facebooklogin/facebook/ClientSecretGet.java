package com.packt.example.facebooklogin.facebook;

import com.nimbusds.oauth2.sdk.SerializeException;
import com.nimbusds.oauth2.sdk.auth.ClientAuthenticationMethod;
import com.nimbusds.oauth2.sdk.auth.PlainClientSecret;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.http.CommonContentTypes;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.util.URLUtils;

import javax.mail.internet.ContentType;
import java.util.HashMap;
import java.util.Map;

public class ClientSecretGet extends PlainClientSecret {

    protected ClientSecretGet(ClientID clientID, Secret secret) {
        super(new ClientAuthenticationMethod("get"), clientID, secret);
    }

    @Override
    public void applyTo(final HTTPRequest httpRequest) {
        if (httpRequest.getMethod() != HTTPRequest.Method.GET)
            throw new SerializeException("The HTTP request method must be GET");

        ContentType ct = httpRequest.getContentType();
        if (ct == null)
            throw new SerializeException("Missing HTTP Content-Type header");

        if (! ct.match(CommonContentTypes.APPLICATION_URLENCODED))
            throw new SerializeException("The HTTP Content-Type header must be "
            + CommonContentTypes.APPLICATION_URLENCODED);

        Map<String,String> params = httpRequest.getQueryParameters();
        params.putAll(toParameters());
        String queryString = URLUtils.serializeParameters(params);
        httpRequest.setQuery(queryString);
    }

    private Map<String,String> toParameters() {
        Map<String,String> params = new HashMap<>();
        params.put("client_id", getClientID().getValue());
        params.put("client_secret", getClientSecret().getValue());
        return params;
    }
}