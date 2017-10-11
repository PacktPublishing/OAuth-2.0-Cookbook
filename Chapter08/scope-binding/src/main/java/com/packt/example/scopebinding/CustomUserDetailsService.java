package com.packt.example.scopebinding;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomUserDetailsService implements UserDetailsService, InitializingBean {
    private Map<String, CustomUser> users = new HashMap<>();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.get(username);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        users.put("adolfo", new CustomUser("adolfo", "123", Arrays.asList("ROLE_USER", "read_x")));
    }
}
