package com.example.dynamicserver.oauth.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.dynamicserver.oauth.registration.ClientRegistrationRequest;
import com.example.dynamicserver.oauth.registration.ClientRegistrationResponse;
import com.example.dynamicserver.oauth.registration.DynamicClientDetails;
import com.example.dynamicserver.oauth.registration.DynamicClientDetailsFactory;
import com.example.dynamicserver.oauth.registration.RedirectFlowSpecification;
import com.example.dynamicserver.oauth.registration.RegistrationError;

@Controller
public class DynamicClientRegistrationController {

    @Autowired
    private ClientRegistrationService clientRegistration;

    @Autowired
    private DynamicClientDetailsFactory clientDetailsFactory;

    @Autowired
    private RedirectFlowSpecification redirectFlowSpecification;

    /**
     * RFC7591
     * Definitions this endpoint MAY be an OAuth 2.0 protected resource
     * and it MAY accept an initial access token.
     *
     * As we are using open registration, these endpoint MAY be rate-limited to prevent a denial-of-service attack.
     */
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody ClientRegistrationRequest clientMetadata) {

        if (!redirectFlowSpecification.isSatisfiedBy(clientMetadata)) {
            RegistrationError error = new RegistrationError(RegistrationError.INVALID_REDIRECT_URI);
            error.setErrorDescription("You must specify redirect_uri when using flows with redirection");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        DynamicClientDetails clientDetails = clientDetailsFactory.create(clientMetadata);
        clientRegistration.addClientDetails(clientDetails);

        return new ResponseEntity<>(createResponse(clientDetails), HttpStatus.CREATED);
    }

    private ClientRegistrationResponse createResponse(DynamicClientDetails clientDetails) {
        ClientRegistrationResponse response = new ClientRegistrationResponse();
        response.setClientId(clientDetails.getClientId());
        response.setClientSecret(clientDetails.getClientSecret());
        response.setClientName(clientDetails.getClientName());
        response.setClientUri(clientDetails.getClientUri());
        response.setGrantTypes(clientDetails.getAuthorizedGrantTypes());
        response.setRedirectUris(clientDetails.getRegisteredRedirectUri());
        response.setResponseTypes(clientDetails.getResponseTypes());
        response.setScope(clientDetails.getScope().stream().reduce((a, b) -> a + " " + b).get());
        response.setSoftwareId(clientDetails.getSoftwareId());
        response.setTokenEndpointAuthMethod(clientDetails.getTokenEndpointAuthMethod());
        response.setClientSecretExpiresAt(clientDetails.getClientSecretExpiresAt());
        return response;
    }

    @GetMapping("/clients")
    public ResponseEntity<List<ClientDetails>> list() {
        return new ResponseEntity<>(clientRegistration.listClientDetails(), HttpStatus.OK);
    }

}
