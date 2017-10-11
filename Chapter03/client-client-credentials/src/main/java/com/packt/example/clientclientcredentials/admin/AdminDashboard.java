package com.packt.example.clientclientcredentials.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import com.packt.example.clientclientcredentials.oauth.UserProfile;

@Controller
public class AdminDashboard {
    //@formatter:off

    @Autowired
    private OAuth2RestTemplate restTemplate;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/callback")
    public ModelAndView callback() {
        return new ModelAndView("forward:/dashboard");
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard() {
        ModelAndView mv = new ModelAndView("dashboard");

        String endpoint = "http://localhost:8080/api/users";
        try {
            UserProfile[] users = restTemplate.getForObject(endpoint, UserProfile[].class);
            mv.addObject("users", users);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("it was not possible to retrieve all users");
        }

        return mv;
    }


    //@formatter:on
}
