package com.sapanywhere.app.oauth;

import com.sapanywhere.app.exception.BusinessException;

public interface OauthClient {

	public void verifySignature(String timestamp, String hmac)
			throws BusinessException;

	public TokenInfo getTokenInfoByCode(String authorizeCode, String redirectUrl)
			throws BusinessException;

	public TokenInfo getTokenInfoByRefreshToken(String refreshToken,
			String redirectUrl) throws BusinessException;

	public String getAuthorizeURL() throws BusinessException;

	public String getTestAuthorizeURL() throws BusinessException;
}
