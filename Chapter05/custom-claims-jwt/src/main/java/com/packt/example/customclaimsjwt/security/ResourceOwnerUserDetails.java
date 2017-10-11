package com.packt.example.customclaimsjwt.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class ResourceOwnerUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;

    private ResourceOwner wrapped;

    public ResourceOwnerUserDetails(ResourceOwner wrapped) {
        this.wrapped = wrapped;
    }

    public String getEmail() {
        return wrapped.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return wrapped.getPassword();
    }

    @Override
    public String getUsername() {
        return wrapped.getUsername();
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
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
