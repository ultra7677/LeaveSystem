package com.sapanywhere.app.oauth;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapanywhere.app.oauth.HttpClient;
import com.sapanywhere.app.utils.SSLContextUtils;
import com.sapanywhere.app.exception.BusinessException;

@Service
public class OauthClientImpl implements OauthClient {

	private static Logger logger = Logger.getLogger(OauthClientImpl.class);

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private HttpClient httpClient;

	@Override
	public void verifySignature(String timestamp, String hmac)
			throws BusinessException {
		this.verifyConfig();
		String apiSecret = this.appProperties.getApiSecret();
		String content = String.format("apiKey=%s&timestamp=%s",
				this.appProperties.getApiKey(), timestamp);
		try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(
					apiSecret.getBytes("UTF-8"), "HmacSHA256");
			sha256_HMAC.init(secret_key);
			String encodeContent = Hex.encodeHexString(sha256_HMAC
					.doFinal(content.getBytes("UTF-8")));
			if (!StringUtils.equalsIgnoreCase(hmac, encodeContent)) {
				throw new BusinessException(ErrorCode.APP_SIGN_VERIFY_FAILED);
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw new BusinessException(ErrorCode.APP_SIGN_VERIFY_FAILED);
		}
	}

	@Override
	public TokenInfo getTokenInfoByCode(String authorizeCode, String redirectUrl)
			throws BusinessException {
		String url = this.getTokenURL(authorizeCode, redirectUrl);
		try {
			SSLContextUtils.byPassCert();
			ObjectMapper objectMapper = new ObjectMapper();
			String content = httpClient.get(url);
			if (!StringUtils.isEmpty(content)) {
				return objectMapper.readValue(content, TokenInfo.class);
			}
		} catch (Exception ex) {
			logger.error("get access token failed", ex);
		}

		throw new BusinessException(ErrorCode.APP_GET_TOKRN_FAILED);
	}

	@Override
	public TokenInfo getTokenInfoByRefreshToken(String refreshToken,
			String redirectUrl) throws BusinessException {
		String url = this.getExchangeTokenURL(refreshToken, redirectUrl);
		try {
			SSLContextUtils.byPassCert();
			ObjectMapper objectMapper = new ObjectMapper();
			String content = httpClient.get(url);
			if (!StringUtils.isEmpty(content)) {
				return objectMapper.readValue(content, TokenInfo.class);
			}
		} catch (Exception ex) {
			logger.error("get access token failed", ex);
		}

		throw new BusinessException(ErrorCode.APP_GET_TOKRN_FAILED);
	}

	@Override
	public String getAuthorizeURL() throws BusinessException {
		this.verifyConfig();
		String url = this.join(this.appProperties.getAuthorizeServiceUrl(),
				"authorize");

		url += "?client_id=" + this.appProperties.getApiKey();
		url += "&scope=" + this.appProperties.getAppScope();
		return url;
	}

	@Override
	public String getTestAuthorizeURL() throws BusinessException {
		String url = this.getAuthorizeURL();
		return url + "&purpose=testing";
	}

	private void verifyConfig() throws BusinessException {
		if (StringUtils.isEmpty(this.appProperties.getApiKey())) {
			logger.error("You havn't configuration your app id");
			throw new BusinessException(ErrorCode.APP_CONFIG_VERIFY_FAILED);
		}

		if (StringUtils.isEmpty(this.appProperties.getApiSecret())) {
			logger.error("You havn't configuration your app secret");
			throw new BusinessException(ErrorCode.APP_CONFIG_VERIFY_FAILED);
		}

		if (StringUtils.isEmpty(this.appProperties.getAppScope())) {
			logger.error("You havn't configuration your app scope");
			throw new BusinessException(ErrorCode.APP_CONFIG_VERIFY_FAILED);
		}

		if (StringUtils.isEmpty(this.appProperties.getOpenAPIUrl())) {
			logger.error("You havn't configuration SAP Anywhere open Api url");
			throw new BusinessException(ErrorCode.APP_CONFIG_VERIFY_FAILED);
		}

		if (StringUtils.isEmpty(this.appProperties.getAuthorizeServiceUrl())) {
			logger.error("You havn't configuration SAP Anywhere idp url");
			throw new BusinessException(ErrorCode.APP_CONFIG_VERIFY_FAILED);
		}
	}

	private String getTokenURL(String authorizeCode, String redirectUrl)
			throws BusinessException {
		this.verifyConfig();
		String url = this.join(this.appProperties.getAuthorizeServiceUrl(),
				"token");
		url += "?client_id=" + this.appProperties.getApiKey();
		url += "&client_secret=" + this.appProperties.getApiSecret();
		url += "&grant_type=authorization_code";
		url += "&code=" + authorizeCode;
		url += "&redirect_uri=" + redirectUrl;

		return url;
	}

	private String getExchangeTokenURL(String refreshToken, String redirectUrl)
			throws BusinessException {
		this.verifyConfig();
		String url = this.join(this.appProperties.getAuthorizeServiceUrl(),
				"token");
		url += "?client_id=" + this.appProperties.getApiKey();
		url += "&client_secret=" + this.appProperties.getApiSecret();
		url += "&grant_type=refresh_token";
		url += "&refresh_token=" + refreshToken;
		url += "&redirect_uri=" + redirectUrl;

		return url;
	}

	private String join(String host, String path) {
		if (!host.endsWith("/")) {
			host += "/";
		}
		if (path.startsWith("/")) {
			path = path.substring(1);
		}

		return host + path;
	}

}
