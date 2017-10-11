package com.packt.example.googleuserinfo.security;

import com.packt.example.googleuserinfo.openid.OpenIdConnectFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${openid.callback-uri}")
    private String callbackUri;

    @Value("${openid.api-base-uri}")
    private String apiBaseUri;

    @Autowired
    private OpenIdConnectFilter openIdConnectFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterAfter(new OAuth2ClientContextFilter(), AbstractPreAuthenticatedProcessingFilter.class)
                .addFilterAfter(openIdConnectFilter, OAuth2ClientContextFilter.class)
                .authorizeRequests()
                .antMatchers("/").permitAll().and()
                .authorizeRequests()
                .antMatchers(apiBaseUri).authenticated().and()
                .authorizeRequests().anyRequest().authenticated().and()
                .httpBasic().authenticationEntryPoint(
                new LoginUrlAuthenticationEntryPoint(callbackUri)).and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll().and()
                .csrf().disable();
    }

}

