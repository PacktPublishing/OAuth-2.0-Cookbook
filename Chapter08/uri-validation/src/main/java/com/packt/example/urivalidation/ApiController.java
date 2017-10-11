package com.packt.example.urivalidation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiController {

    @GetMapping("/api/read")
    public ResponseEntity<String> read() {
        return ResponseEntity.ok("success");
    }

}
