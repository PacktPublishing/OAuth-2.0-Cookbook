package com.packt.linkedin.example.sociallinkd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

	@Autowired
	private LinkedIn linkedin;

	@Autowired
	private ConnectionRepository connectionRepository;

	@GetMapping
	public String profile(Model model) {
		if (connectionRepository.findPrimaryConnection(LinkedIn.class) == null) {
			return "redirect:/connect/linkedin";
		}

		String firstName = linkedin.profileOperations().getUserProfile()
			.getFirstName();

		model.addAttribute("name", firstName);

		return "profile";
	}

}
