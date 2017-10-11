package com.packt.example.googleuserinfo.openid;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2AuthenticationFailureEvent;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class OpenIdConnectFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private OAuth2RestTemplate restTemplate;

    @Autowired
    private UserIdentity userIdentity;

    @Autowired
    private UserRepository repository;

    @Autowired
    private ObjectMapper jsonMapper;

    private ApplicationEventPublisher eventPublisher;

    private final AntPathRequestMatcher localMatcher;


    // UserInfo endpoint
    @Autowired
    private UserInfoService userInfoService;


    public OpenIdConnectFilter(
        @Value("${openid.callback-uri}") String callbackUri,
        @Value("${openid.api-base-uri}") String apiBaseUri) {
        super(new OrRequestMatcher(
            new AntPathRequestMatcher(callbackUri),
            new AntPathRequestMatcher(apiBaseUri)));
        this.localMatcher = new AntPathRequestMatcher(apiBaseUri);
        setAuthenticationManager(new NoopAuthenticationManager());
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        if (localMatcher.matches(request)) {
            restTemplate.getAccessToken();
            chain.doFilter(req, res);
        } else {
            super.doFilter(req, res, chain);
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        super.setApplicationEventPublisher(eventPublisher);
    }

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException, IOException, ServletException {

        try {
            OAuth2AccessToken accessToken = restTemplate.getAccessToken();

            Claims claims = Claims.createFrom(jsonMapper, accessToken);
            GoogleUser googleUser = userIdentity.findOrCreateFrom(claims);

            String userName = getUserNameFromUserInfo(accessToken,
                googleUser.getOpenIDAuthentication().getSubject());
            googleUser.getOpenIDAuthentication().setName(userName);

            repository.save(googleUser);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    googleUser, null, googleUser.getAuthorities());
            publish(new AuthenticationSuccessEvent(authentication));
            return authentication;
        } catch (OAuth2Exception e) {
            BadCredentialsException error = new BadCredentialsException(
                    "Cannot retrieve the access token", e);
            publish(new OAuth2AuthenticationFailureEvent(error));
            throw error;
        }
    }

    private String getUserNameFromUserInfo(OAuth2AccessToken accessToken, String subject) {
        Map<String, String> userInfo = userInfoService.getUserInfoFor(accessToken);
        if (!userInfo.get("sub").equals(subject)) {
            throw new RuntimeException(
                "sub element of ID Token must be the same from UserInfo endpoint");
        }
        String userName = userInfo.get("name");
        return userName;
    }

    private void publish(ApplicationEvent event) {
        if (eventPublisher!=null) {
            eventPublisher.publishEvent(event);
        }
    }

    private static class NoopAuthenticationManager implements AuthenticationManager {

        @Override
        public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
            throw new UnsupportedOperationException(
                "No authentication should be done with this AuthenticationManager");
        }

    }

}
