package com.sapanywhere.app.oauth;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapanywhere.app.exception.BusinessException;
import com.sapanywhere.app.utils.SSLContextUtils;


@Service
public class ApiClientImpl implements ApiClient {

	private static Logger logger = Logger.getLogger(ApiClientImpl.class);

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private HttpClient httpClient;

	private ObjectMapper objectMapper = new ObjectMapper();

		@Override
	public UserInfo getUserInfo(String accessToken) throws BusinessException {
		String url = this.join("Users/me", accessToken);
		try {
			SSLContextUtils.byPassCert();
			String content = httpClient.get(url);
			if (!StringUtils.isEmpty(content)) {
				return objectMapper.readValue(content, UserInfo.class);
			}
		} catch (Exception ex) {
			logger.error("Get user info failed via SAP Anywhere Open API", ex);
		}

		throw new BusinessException(ErrorCode.APP_ACCESS_OPEN_API_FAILED);
	}

	private String join(String path, String accessToken)
			throws BusinessException {
		String host = this.appProperties.getOpenAPIUrl();
		if (!host.endsWith("/")) {
			host += "/";
		}
		return host + path + "?access_token=" + accessToken;

	}

	/*private URI getOpenAPIUri(){
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.appProperties.getOpenAPIUrl()).path("/{boNames}")
	}*/
}
