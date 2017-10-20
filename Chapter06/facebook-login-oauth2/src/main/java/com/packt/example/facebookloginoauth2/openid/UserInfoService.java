package com.packt.example.facebookloginoauth2.openid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@Service
public class UserInfoService {

    @Autowired
    private FacebookProperties properties;

    public Map<String, String> getUserInfoFor(OAuth2AccessToken accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<Void> requestEntity = new RequestEntity<>(
                getHeader(accessToken.getValue()),
                HttpMethod.GET,
                URI.create(properties.getUserInfoUri())
        );

        ParameterizedTypeReference<Map<String, String>> typeRef =
                new ParameterizedTypeReference<Map<String, String>>() {};
        ResponseEntity<Map<String, String>> result = restTemplate.exchange(
                requestEntity, typeRef);

        if (result.getStatusCode().is2xxSuccessful()) {
            return result.getBody();
        }

        throw new RuntimeException("It wasn't possible to retrieve userInfo");
    }

    private MultiValueMap<String, String> getHeader(String accessToken) {
        MultiValueMap<String, String> httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + accessToken);
        return httpHeaders;
    }

}
