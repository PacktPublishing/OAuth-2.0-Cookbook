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
    private FacebookLoginData facebookLoginData;

    @Deprecated
    FacebookUser() {}

    public FacebookUser(FacebookLoginData facebookLoginData) {
        this.facebookLoginData = facebookLoginData;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    public FacebookLoginData getFacebookLoginData() {
        return facebookLoginData;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !facebookLoginData.hasExpired();
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
        return facebookLoginData.getName();
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
