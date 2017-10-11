package com.packt.example.oauth2providerstate;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiController {

    @GetMapping("/api/read")
    public ResponseEntity<String> read() {
        CustomUser user = (CustomUser) SecurityContextHolder
            .getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok("success " + user.getUsername());
    }

}
