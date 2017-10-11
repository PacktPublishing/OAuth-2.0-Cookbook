package com.packt.linkedin.example.sociallinkd;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;

@Configuration
public class LinkedInConfiguration {

	@Bean
	public ConnectController connectController(ConnectionFactoryLocator locator,
			ConnectionRepository repository) {
		ConnectController controller = new ConnectController(locator, repository);
		controller.setApplicationUrl("http://localhost:8080");
		return controller;
	}

}
