package com.sapanywhere.app.properties;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(locations = "classpath:application.properties", ignoreUnknownFields = false, prefix = "sapanywhere.app")
public class AppProperties {

	@NotBlank
	private String appId;
	@NotBlank
	private String appSecret;
	@NotBlank
	private String appScope;
	@NotBlank
	private String rootCallbackUrl;
	@NotBlank
	private String installCallbackUrl;
	@NotBlank
	private String sapanywhereIdpUrl;
	@NotBlank
	private String sapanywhereOpenApiUrl;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getAppScope() {
		return appScope;
	}
	public void setAppScope(String appScope) {
		this.appScope = appScope;
	}
	public String getRootCallbackUrl() {
		return rootCallbackUrl;
	}
	public void setRootCallbackUrl(String rootCallbackUrl) {
		this.rootCallbackUrl = rootCallbackUrl;
	}
	public String getInstallCallbackUrl() {
		return installCallbackUrl;
	}
	public void setInstallCallbackUrl(String installCallbackUrl) {
		this.installCallbackUrl = installCallbackUrl;
	}
	public String getSapanywhereIdpUrl() {
		return sapanywhereIdpUrl;
	}
	public void setSapanywhereIdpUrl(String sapanywhereIdpUrl) {
		this.sapanywhereIdpUrl = sapanywhereIdpUrl;
	}
	public String getSapanywhereOpenApiUrl() {
		return sapanywhereOpenApiUrl;
	}
	public void setSapanywhereOpenApiUrl(String sapanywhereOpenApiUrl) {
		this.sapanywhereOpenApiUrl = sapanywhereOpenApiUrl;
	}
}
