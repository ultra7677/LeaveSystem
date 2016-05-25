package com.sapanywhere.app.oauth;

import com.sapanywhere.app.exception.BusinessException;

public interface ApiClient {

	public UserInfo getUserInfo(String accessToken) throws BusinessException;


}
