package com.packt.example.oauth2provider.client;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientRegistrationService clientRegistrationService;

    @GetMapping("/register")
    public ModelAndView register(ModelAndView mv) {
        mv.setViewName("client/register");
        mv.addObject("registry", new BasicClientInfo());

        return mv;
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard(ModelAndView mv) {
        mv.addObject("applications",
                clientRegistrationService.listClientDetails());
        return mv;
    }

    @GetMapping("/remove")
    public ModelAndView remove(
            @RequestParam(value = "client_id", required = false) String clientId) {

        clientRegistrationService.removeClientDetails(clientId);

        ModelAndView mv = new ModelAndView("redirect:/client/dashboard");
        mv.addObject("applications",
                clientRegistrationService.listClientDetails());
        return mv;
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid BasicClientInfo clientDetails,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("client/register");
        }

        Application app = new Application();
        app.setName(clientDetails.getName());
        app.addRedirectUri(clientDetails.getRedirectUri());
        app.setClientType(ClientType.valueOf(clientDetails.getClientType()));
        app.setClientId(UUID.randomUUID().toString());
        app.setClientSecret(UUID.randomUUID().toString());
        app.setAccessTokenValidity(3000);
        app.addScope("read_profile");
        app.addScope("read_contacts");

        clientRegistrationService.addClientDetails(app);

        // create client details in database
        ModelAndView mv = new ModelAndView("redirect:/client/dashboard");
        mv.addObject("applications",
                clientRegistrationService.listClientDetails());

        return mv;
    }
}
