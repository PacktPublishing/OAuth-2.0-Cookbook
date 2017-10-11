package com.example.dynamicserver.oauth.registration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.dynamicserver.util.RandomHelper;

@Component
public class DynamicClientDetailsFactory {

    private RandomHelper randomHelper;

    @Autowired
    DynamicClientDetailsFactory(RandomHelper randomHelper) {
        this.randomHelper = randomHelper;
    }

    public DynamicClientDetails create(ClientRegistrationRequest request) {
        DynamicClientDetails clientDetails = new DynamicClientDetails();

        clientDetails.setClientName(request.getClientName());
        clientDetails.setClientUri(request.getClientUri());
        clientDetails.setSoftwareId(request.getSoftwareId());

        setClientCredentials(request, clientDetails);

        request.getRedirectUris().forEach(
                uri -> clientDetails.addRegisteredRedirectUri(uri));

        if (request.getScope() != null) {
            for (String scope : request.getScope().split("\\s")) {
                clientDetails.addScope(scope);
            }
        }

        if (request.getGrantTypes().size() == 0) {
            clientDetails.addAuthorizedGrantTypes("authorization_code");
        } else {
            request.getGrantTypes().forEach(
                grantType -> clientDetails.addAuthorizedGrantTypes(grantType));
        }

        clientDetails.setTokenEndpointAuthMethod("client_secret_basic");

        if (clientDetails.getAuthorizedGrantTypes().contains("implicit")) {
            clientDetails.getResponseTypes().add("token");
        }

        if (clientDetails.getAuthorizedGrantTypes().contains("authorization_code")) {
            clientDetails.getResponseTypes().add("code");
        }

        return clientDetails;
    }

    private void setClientCredentials(ClientRegistrationRequest clientMetadata, DynamicClientDetails clientDetails) {
        clientDetails.setClientId(randomHelper.nextString(10, 32));

        Set<String> grantTypes = clientMetadata.getGrantTypes();

        long otherThanImplicit = grantTypes.stream()
            .filter(grantType -> !grantType.equals("implicit")).count();

        if (otherThanImplicit > 0 || grantTypes.size() == 0) {
            clientDetails.setClientSecret(randomHelper.nextString(32, 32));

            LocalDateTime after30Days = LocalDateTime.now().plusDays(30);
            clientDetails.setClientSecretExpiresAt(
                after30Days.atZone(ZoneId.systemDefault()).toEpochSecond());

        }
    }

}
