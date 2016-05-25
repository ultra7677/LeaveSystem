package com.sapanywhere.app.oauth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenInfo {
	private String accessToken;
	private String tokenType;
	private String refreshToken;
	private String expires;
	private String scope;

	public String getAccessToken() {
		return accessToken;
	}

	@JsonSetter("access_token")
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getToken_type() {
		return tokenType;
	}

	@JsonSetter("token_type")
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	@JsonSetter("refresh_token")
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getExpires() {
		return expires;
	}

	@JsonSetter("expires_in")
	public void setExpires(String expires) {
		this.expires = expires;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
}
