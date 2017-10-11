package com.packt.example.customclaimsjwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ResourceOwnerDetailsService implements UserDetailsService {

    @Autowired
    private ResourceOwnerRepository repo;

    @Override
    public UserDetails loadUserByUsername(String uname) throws UsernameNotFoundException {
        ResourceOwner user = repo.findByUsername(uname)
            .orElseThrow(() -> new RuntimeException());

        return new ResourceOwnerUserDetails(user);
    }

}
