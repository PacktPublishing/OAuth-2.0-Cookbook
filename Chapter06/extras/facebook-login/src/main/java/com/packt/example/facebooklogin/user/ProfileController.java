package com.packt.example.facebooklogin.user;

import com.packt.example.facebooklogin.facebook.FacebookAccountRepository;
import com.packt.example.facebooklogin.facebook.FacebookAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private FacebookAccountRepository facebookAccountRepository;

    @GetMapping
    public ModelAndView profile() {
        DefaultOAuth2User user = (DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<FacebookAuth> facebookAuth = facebookAccountRepository.findById((String) user.getAttributes().get("id"));
        Optional<Profile> profile = profileRepository.findByUser(facebookAuth.get().getUser());

        ModelAndView mv = new ModelAndView("profile");
        mv.addObject("facebook", user.getAttributes());
        if (profile.isPresent()) {
            mv.addObject("profile", profile.get());
            return mv;
        }

        return new ModelAndView("redirect:/profile/form");
    }

    @GetMapping("/form")
    public ModelAndView form() {
        DefaultOAuth2User user = (DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<FacebookAuth> facebookAuth = facebookAccountRepository.findById((String) user.getAttributes().get("id"));
        Optional<Profile> profile = profileRepository.findByUser(facebookAuth.get().getUser());

        ModelAndView mv = new ModelAndView("form");
        if (profile.isPresent()) {
            mv.addObject("profile", profile.get());
        } else {
            mv.addObject("profile", new Profile());
        }

        return mv;
    }

    @PostMapping
    public ModelAndView save(Profile profile) {
        DefaultOAuth2User user = (DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<FacebookAuth> facebookAuth = facebookAccountRepository.findById((String) user.getAttributes().get("id"));
        profile.setUser(facebookAuth.get().getUser());

        Profile newProfile = profileRepository.save(profile);
        ModelAndView mv = new ModelAndView("redirect:/profile");
        mv.addObject("profile", newProfile);
        return mv;
    }
}
