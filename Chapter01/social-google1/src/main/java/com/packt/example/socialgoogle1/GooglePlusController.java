package com.packt.example.socialgoogle1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.google.api.Google;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GooglePlusController {

	@Autowired
	private Google google;

	@Autowired
	private ConnectionRepository connectionRepository;

	@GetMapping
	public String profile(Model model) {
		if (connectionRepository.findPrimaryConnection(Google.class) == null) {
			return "redirect:/connect/google";
		}

		String name = google.plusOperations().getGoogleProfile()
			.getDisplayName();
		model.addAttribute("name", name);

		return "profile";
	}

}
