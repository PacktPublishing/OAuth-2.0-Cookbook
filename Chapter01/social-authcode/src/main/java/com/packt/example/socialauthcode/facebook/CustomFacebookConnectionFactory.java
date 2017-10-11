package com.packt.example.socialauthcode.facebook;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookAdapter;

public class CustomFacebookConnectionFactory extends
		OAuth2ConnectionFactory<Facebook> {

	public CustomFacebookConnectionFactory(
			String appId, String appSecret, String apiVersion) {

		super("facebook",
			new CustomFacebookServiceProvider(appId, appSecret, apiVersion),
			new FacebookAdapter());
	}

}
