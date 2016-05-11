package com.sapanywhere.app.controller;

import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapanywhere.app.entity.OauthData;
import com.sapanywhere.app.entity.User;
import com.sapanywhere.app.model.BindForm;
import com.sapanywhere.app.model.OauthToken;
import com.sapanywhere.app.model.SAPAnywhereUserInfo;
import com.sapanywhere.app.properties.AppProperties;
import com.sapanywhere.app.repository.OauthDataRepository;
import com.sapanywhere.app.repository.UserRepository;
import com.sapanywhere.app.utils.HttpUtils;
import com.sapanywhere.app.utils.SSLContextUtils;
import com.sapanywhere.app.utils.SessionNameUtils;

@Controller
public class OauthController {

	private static Logger logger = Logger.getLogger(OauthController.class);
	
	@Autowired
	private AppProperties appProperties;
	
	@Autowired
	private OauthDataRepository oauthDataRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value = "/oauth/bind.html", method = RequestMethod.GET)
	public String loadbindPage(BindForm bindForm, Model model) {
		if(!model.containsAttribute(SessionNameUtils.OAUTH)){
			return "redirect:/errors/500";
		}
		
		OauthData oauthData = (OauthData)model.asMap().get(SessionNameUtils.OAUTH);
		bindForm.setSapAccount(oauthData.getUserEmail());
		bindForm.setEmail(oauthData.getUserEmail());
		bindForm.setFirstName(oauthData.getUserName());
		
		return "/oauth/bind";
	}
	
	@RequestMapping(value = "/oauth/bind", method = RequestMethod.POST)
	public String bind(@Valid BindForm bindForm, BindingResult result, Model model){
		bindForm.onValid(result);
		if (result.hasErrors()) {
			return "/oauth/bind";
		}
		
		if(!model.containsAttribute(SessionNameUtils.OAUTH)){
			return "redirect:/errors/500";
		}
		
		OauthData oauthData = (OauthData)model.asMap().get(SessionNameUtils.OAUTH);
		if(StringUtils.equalsIgnoreCase(oauthData.getUserEmail(), bindForm.getSapAccount())){
			FieldError error = new FieldError("bindForm","sapAccount",null, false, new String[]{"bind.error.sapaccountnotmatch"}, null, null);
			result.addError(error);
			return "/oauth/bind";
		}

		try{
			User user = this.userRepository.save(bindForm.parse());
			model.addAttribute(SessionNameUtils.USER, user);
		}catch(Exception ex){
			logger.error(ex);
			if(ex.getCause() != null && ex.getCause().getClass() == ConstraintViolationException.class){
				FieldError error = new FieldError("bindForm","email",null, false, new String[]{"bind.error.emailexist"}, null, null);
				result.addError(error);
			}else{
				result.reject("bind.error.failed");
			}

			return "/accounts/bind";
		}
		
		return "redirect:/index";
	}
	
	@RequestMapping(value = "/oauth/login", method = RequestMethod.POST)
	public String login(){
		if(!this.verifyAppConfiguration() ){
			return "redirect:/errors/500";
		}
		
		String authorizeUrl = String.format("%s/oauth2/authorize?response_type=code&client_id=%s&scope=%s&redirect_uri=%s",
				this.appProperties.getSapanywhereIdpUrl(),
				this.appProperties.getAppId(),
				this.appProperties.getAppScope(),
				this.appProperties.getRootCallbackUrl());
		
		return "redirect:" + authorizeUrl;
	}
	
	@RequestMapping(value = "/oauth/callback", method = RequestMethod.GET)
	public String callback(@RequestParam(value="code") String code, Model model){
		OauthToken oauthToken = this.getOauthToken(code);
		if(oauthToken == null ){
			return "redirect:/errors/500";
		}
		
		SAPAnywhereUserInfo userInfo = this.getUserInfo(oauthToken.getAccess_token());
		if(userInfo == null){
			return "redirect:/errors/500";
		}
		
		OauthData oauthData = this.updateOauthData(oauthToken,userInfo);
		model.addAttribute(SessionNameUtils.OAUTH, oauthData);
		
		User user = this.userRepository.findBySapAccount(userInfo.getUserEmail());
		if(user == null){
			return "redirect:/oauth/bind";
		}
		
		model.addAttribute(SessionNameUtils.USER, user);
		
		return "redirect:/index";
	}
	
	@RequestMapping(value = "/oauth/install", method = RequestMethod.GET)
	public String install(@RequestParam(value="sapanywhere") String sapanywhere,
			@RequestParam(value="timestamp") String timestamp,
			@RequestParam(value="hmac") String hmac,
			@RequestParam(value="op",required = false) String op,
			@RequestParam(value="code",required = false) String code,
			Model model){
		
		if(!this.verifyAppConfiguration() || !this.verifyInstall(sapanywhere, timestamp, hmac)){
			return "redirect:/errors/500";
		}
		
		if(StringUtils.equalsIgnoreCase(op, "install")){
			String authorizeUrl = this.getInstallAuthorizeUrl(sapanywhere);
			if(StringUtils.isEmpty(authorizeUrl)){
				return "redirect:/errors/500";
			}
			
			return "redirect:" + authorizeUrl;
		}
		
		if(StringUtils.isEmpty(code)){
			return "redirect:/errors/500";
		}
		
		OauthToken oauthToken = this.getInstallOauthToken(sapanywhere, code);
		if(oauthToken == null ){
			return "redirect:/errors/500";
		}
		
		SAPAnywhereUserInfo userInfo = this.getUserInfo(oauthToken.getAccess_token());
		if(userInfo == null){
			return "redirect:/errors/500";
		}
		OauthData oauthData = this.updateOauthData(oauthToken,userInfo);
		model.addAttribute(SessionNameUtils.OAUTH, oauthData);
		return "redirect:/index";
	}
	
	private OauthToken getOauthToken(String authorizeCode) {
		try{
			SSLContextUtils.byPassCert();
			
			String url = String.format("%s/oauth2/token?client_id=%s&client_secret=%s&grant_type=authorization_code&code=%s&redirect_uri=%s",
					this.appProperties.getSapanywhereIdpUrl(),
					this.appProperties.getAppId(), 
					this.appProperties.getAppSecret(), 
					authorizeCode, 
					this.appProperties.getRootCallbackUrl());
			
			ObjectMapper objectMapper = new ObjectMapper();
			String content = HttpUtils.get(url);
			if(!StringUtils.isEmpty(content)){
				return objectMapper.readValue(content, OauthToken.class);
			}
		}catch(Exception ex){
			logger.error(ex);
		}

		return null;
    }
	
	private OauthToken getInstallOauthToken(String sapanywhere, String authorizeCode){
		try{
			SSLContextUtils.byPassCert();
			
			String url = String.format("%s/oauth/token?client_id=%s&client_secret=%s&grant_type=authorization_code&code=%s&redirect_uri=%s",
					sapanywhere, 
					this.appProperties.getAppId(),
					this.appProperties.getAppSecret(), 
					authorizeCode,
					this.appProperties.getInstallCallbackUrl());
			
			ObjectMapper objectMapper = new ObjectMapper();
			String content = HttpUtils.get(url);
			if(!StringUtils.isEmpty(content)){
				return objectMapper.readValue(content, OauthToken.class);
			}
			
		}catch(Exception ex){
			logger.error(ex);
		}

		return null;
    }
	
	
	private SAPAnywhereUserInfo getUserInfo(String accessToken){
		try{
			SSLContextUtils.byPassCert();
			String url = String.format("%s/UserInfo?access_token=%s", this.appProperties.getSapanywhereOpenApiUrl(),accessToken);
			ObjectMapper objectMapper = new ObjectMapper();
			String content = HttpUtils.get(url);
			if(!StringUtils.isEmpty(content)){
				return objectMapper.readValue(content, SAPAnywhereUserInfo.class);
			}
		}catch(Exception ex){
			logger.error(ex);
		}

		return null;
	}
	
	private OauthData updateOauthData(OauthToken oauthToken, SAPAnywhereUserInfo userInfo){
		List<OauthData> oauthDatas = this.oauthDataRepository.findByUserEmail(userInfo.getUserEmail());
		OauthData oauthData = null;
		if(oauthDatas.size() == 0){
			oauthData = new OauthData();
		}else{
			oauthData = oauthDatas.get(0);
		}
		
		oauthData.setAccessToken(oauthToken.getAccess_token());
		oauthData.setRefreshToken(oauthToken.getRefresh_token());
		oauthData.setScope(oauthToken.getScope());
		oauthData.setAudience(userInfo.getAudience());
		oauthData.setTenantId(userInfo.getTenantId());
		oauthData.setUserEmail(userInfo.getUserEmail());
		oauthData.setUserName(userInfo.getUserName());
		oauthData.setUserPhone(userInfo.getUserPhone());
		return this.oauthDataRepository.save(oauthData);
	}
	
	private boolean verifyInstall(String sapanywhere, String timestamp,String hmac){
		String appSecret = this.appProperties.getAppSecret();
		
		String content = String.format("sapanywhere=%s&timestamp=%s", sapanywhere, timestamp);
		try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(appSecret.getBytes("UTF-8"),
					"HmacSHA256");
			sha256_HMAC.init(secret_key);
			String encodeContent=  Hex.encodeHexString(sha256_HMAC.doFinal(content.getBytes("UTF-8")));
			return StringUtils.equalsIgnoreCase(hmac, encodeContent);
		} catch (Exception ex) {
			logger.error(ex);
			return false;
		}
	}
	
	private String getInstallAuthorizeUrl(String sapanywhere){
		return String.format("%s/oauth/authorize?response_type=code&client_id=%s&scope=%s&redirect_uri=%s",
				sapanywhere, 
				this.appProperties.getAppId(),
				this.appProperties.getAppScope(), 
				this.appProperties.getInstallCallbackUrl());
	}
	
	private boolean verifyAppConfiguration(){
		if(StringUtils.isEmpty(this.appProperties.getAppId())){
			logger.error("You havn't configuration your app id");
			return false;
		}
		
		if(StringUtils.isEmpty(this.appProperties.getAppScope())){
			logger.error("You havn't configuration your app scope");
			return false;
		}
		
		if(StringUtils.isEmpty(this.appProperties.getAppSecret())){
			logger.error("You havn't configuration your app secret");
			return false;
		}
		
		if(StringUtils.isEmpty(this.appProperties.getRootCallbackUrl())){
			logger.error("You havn't configuration your app root callback url");
			return false;
		}

		if(StringUtils.isEmpty(this.appProperties.getInstallCallbackUrl())){
			logger.error("You havn't configuration your app install callback url");
			return false;
		}
		
		if(StringUtils.isEmpty(this.appProperties.getSapanywhereOpenApiUrl())){
			logger.error("You havn't configuration SAP Anywhere open Api url");
			return false;
		}
		
		if(StringUtils.isEmpty(this.appProperties.getSapanywhereIdpUrl())){
			logger.error("You havn't configuration SAP Anywhere idp url");
			return false;
		}
		
		return true;
	}
	
}
