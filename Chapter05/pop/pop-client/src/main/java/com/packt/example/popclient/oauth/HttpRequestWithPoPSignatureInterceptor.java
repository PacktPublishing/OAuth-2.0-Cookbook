package com.packt.example.popclient.oauth;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class HttpRequestWithPoPSignatureInterceptor
    implements ClientHttpRequestInterceptor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private JwkKeyPairManager keyPairManager;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
        ClientHttpRequestExecution execution) throws IOException {
        OAuth2ClientContext clientContext = applicationContext.getBean(OAuth2ClientContext.class);
        OAuth2AccessToken accessToken = clientContext.getAccessToken();

        request.getHeaders().set("Authorization", "Bearer " + accessToken.getValue());
        request.getHeaders().set("nonce", keyPairManager.getSignedContent(UUID.randomUUID().toString()));

        return execution.execute(request, body);
    }

}
