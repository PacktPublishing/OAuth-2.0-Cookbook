package com.packt.example.googleconnect.openid;

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
public class GoogleUser implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private OpenIDAuthentication openIDAuthentication;

    @Deprecated
    GoogleUser() {}

    public GoogleUser(String email, OpenIDAuthentication openIDAuthentication) {
        this.email = email;
        this.openIDAuthentication = openIDAuthentication;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !openIDAuthentication.hasExpired();
    }

    // standard getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public OpenIDAuthentication getOpenIDAuthentication() {
        return openIDAuthentication;
    }

    public void setOpenIDAuthentication(OpenIDAuthentication openIDAuthentication) {
        this.openIDAuthentication = openIDAuthentication;
    }


    @Override
    public String getPassword() {
        return null;
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
