package com.sapanywhere.app.oauth;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(locations = "classpath:application.properties", ignoreUnknownFields = false, prefix = "app")
public class AppProperties {

	@NotBlank
	private String apiKey;
	@NotBlank
	private String apiSecret;
	@NotBlank
	private String appScope;
	@NotBlank
	private String applicationUrl;
	@NotBlank
	private String installationUrl;
	@NotBlank
	private String openAPIUrl;
	@NotBlank
	private String authorizeServiceUrl;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiSecret() {
		return apiSecret;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}

	public String getAppScope() {
		return appScope;
	}

	public void setAppScope(String appScope) {
		this.appScope = appScope;
	}

	public String getApplicationUrl() {
		return applicationUrl;
	}

	public void setApplicationUrl(String applicationUrl) {
		this.applicationUrl = applicationUrl;
	}

	public String getInstallationUrl() {
		return installationUrl;
	}

	public void setInstallationUrl(String installationUrl) {
		this.installationUrl = installationUrl;
	}

	public String getOpenAPIUrl() {
		return openAPIUrl;
	}

	public void setOpenAPIUrl(String openAPIUrl) {
		this.openAPIUrl = openAPIUrl;
	}

	public String getAuthorizeServiceUrl() {
		return authorizeServiceUrl;
	}

	public void setAuthorizeServiceUrl(String authorizeServiceUrl) {
		this.authorizeServiceUrl = authorizeServiceUrl;
	}

}
