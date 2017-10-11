package com.packt.example.socialgoogle1.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.social.google")
public class GoogleProperties {
	private String appId;

	private String appSecret;

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(final String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return this.appSecret;
	}

	public void setAppSecret(final String appSecret) {
		this.appSecret = appSecret;
	}
}
