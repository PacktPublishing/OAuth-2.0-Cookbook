package com.packt.example.microsoftlogin.user;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.oidc.core.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class ApplicationController {

    @GetMapping
    public String home() {
        return "home";
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard() {
        DefaultOidcUser user = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ModelAndView mv = new ModelAndView("dashboard");
        mv.addObject("profile", user.getUserInfo().getClaims());
        return mv;
    }

}
