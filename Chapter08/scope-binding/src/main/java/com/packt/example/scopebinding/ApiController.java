package com.packt.example.scopebinding;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApiController {

    @PreAuthorize("#oauth2.hasScope('read_x')")
    @RequestMapping("/api/x")
    public ResponseEntity<String> resourceX() {
        return ResponseEntity.ok("resource X");
    }

    @PreAuthorize("#oauth2.hasScope('read_y')")
    @RequestMapping("/api/y")
    public ResponseEntity<String> resourceY() {
        return ResponseEntity.ok("resource Y");
    }

}
