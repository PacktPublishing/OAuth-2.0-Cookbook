package com.packt.example.openidspring5.user;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.oidc.core.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping
    public ModelAndView profile() {
        DefaultOidcUser user = (DefaultOidcUser) SecurityContextHolder
            .getContext().getAuthentication().getPrincipal();

        ModelAndView mv = new ModelAndView("dashboard");
        mv.addObject("profile", user.getUserInfo().getClaims());
        return mv;
    }

}
