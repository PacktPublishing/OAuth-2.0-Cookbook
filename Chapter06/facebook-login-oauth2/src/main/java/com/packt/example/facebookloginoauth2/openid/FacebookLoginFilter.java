package com.packt.example.facebookloginoauth2.openid;

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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import java.util.Arrays;

@Component
public class FacebookLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private OAuth2RestTemplate restTemplate;

    @Autowired
    private FacebookUserIdentity userIdentity;

    @Autowired
    private UserRepository repository;

    private ApplicationEventPublisher eventPublisher;

    private final AntPathRequestMatcher localMatcher;

    public FacebookLoginFilter(
        @Value("${facebook.filter.callback-uri}") String callbackUri,
        @Value("${facebook.filter.api-base-uri}")String apiBaseUri) {
        super(new OrRequestMatcher(
            new AntPathRequestMatcher(callbackUri),
            new AntPathRequestMatcher(apiBaseUri)
        ));
        this.localMatcher = new AntPathRequestMatcher(apiBaseUri);
        setAuthenticationManager(new NoopAuthenticationManager());
    }

    private static class NoopAuthenticationManager implements AuthenticationManager {
        public Authentication authenticate(Authentication authentication)
                throws AuthenticationException {
            throw new UnsupportedOperationException("No authentication should be done with this AuthenticationManager");
        }
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException, IOException, ServletException {

        try {
            OAuth2AccessToken accessToken = restTemplate.getAccessToken();
            FacebookUser facebookUser = userIdentity.findOrCreateFrom(accessToken);

            repository.save(facebookUser);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    facebookUser, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
            publish(new AuthenticationSuccessEvent(authentication));
            return authentication;
        } catch (OAuth2Exception e) {
            BadCredentialsException error = new BadCredentialsException(
                    "Cannot retrieve the access token", e);
            publish(new OAuth2AuthenticationFailureEvent(error));
            throw error;
        }
    }

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

    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        super.setApplicationEventPublisher(eventPublisher);
    }

    private void publish(ApplicationEvent event) {
        if (eventPublisher!=null) {
            eventPublisher.publishEvent(event);
        }
    }

}
