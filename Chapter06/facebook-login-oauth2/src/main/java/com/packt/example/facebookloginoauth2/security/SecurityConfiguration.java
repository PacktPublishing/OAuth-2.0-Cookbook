package com.packt.example.facebookloginoauth2.security;

import com.packt.example.facebookloginoauth2.openid.FacebookLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private FacebookLoginFilter facebookLoginFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterAfter(new OAuth2ClientContextFilter(), AbstractPreAuthenticatedProcessingFilter.class)
            .addFilterAfter(facebookLoginFilter, OAuth2ClientContextFilter.class)
            .authorizeRequests()
            .antMatchers("/", "/callback").permitAll().and()
            .authorizeRequests()
            .antMatchers("/profile/*").authenticated().and()
            .authorizeRequests().anyRequest().authenticated().and()
            .httpBasic().authenticationEntryPoint(
                new LoginUrlAuthenticationEntryPoint("/callback")).and()
                .logout().logoutSuccessUrl("/").permitAll().and()
                .headers().frameOptions().disable().and()
                .csrf().disable();
    }

}
