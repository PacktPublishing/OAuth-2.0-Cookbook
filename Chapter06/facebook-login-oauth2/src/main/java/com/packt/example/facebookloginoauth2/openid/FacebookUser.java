package com.packt.example.facebookloginoauth2.openid;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class FacebookUser implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private FacebookLoginData openIDAuthentication;

    @Deprecated
    FacebookUser() {}

    public FacebookUser(FacebookLoginData openIDAuthentication) {
        this.openIDAuthentication = openIDAuthentication;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    public FacebookLoginData getOpenIDAuthentication() {
        return openIDAuthentication;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !openIDAuthentication.hasExpired();
    }

    public Long getId() {
        return id;
    }


    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return openIDAuthentication.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
