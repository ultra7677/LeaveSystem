package com.sapanywhere.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import com.sapanywhere.app.entity.OauthData;
import com.sapanywhere.app.entity.User;
import com.sapanywhere.app.exception.BusinessException;
import com.sapanywhere.app.model.BindForm;
import com.sapanywhere.app.oauth.ApiClient;
import com.sapanywhere.app.oauth.AppProperties;
import com.sapanywhere.app.oauth.ErrorCode;
import com.sapanywhere.app.oauth.OauthClient;
import com.sapanywhere.app.oauth.TokenInfo;
import com.sapanywhere.app.oauth.UserInfo;
import com.sapanywhere.app.repository.OauthDataRepository;
import com.sapanywhere.app.service.UserService;
import com.sapanywhere.app.utils.SessionNameUtils;



@Controller
public class OauthController {

	private static Logger logger = Logger.getLogger(OauthController.class);

	public final static String OP_INSTALL = "install";
	public final static String OP_UNINSTALL = "uninstall";
	public final static String PURPOSE_TESTING = "testing";

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private OauthDataRepository oauthDataRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private OauthClient oauthClient;

	@Autowired
	private ApiClient apiClient;

	@Autowired
	private HttpSession session;

	@RequestMapping(value = "/oauth/bind.html", method = RequestMethod.GET)
	public String bindPage(HttpSession session, Model model) {
		OauthData oauthData = (OauthData) session
				.getAttribute(SessionNameUtils.OAUTH);
		if (oauthData == null) {
			return "redirect:/500.html";
		}

		BindForm bindForm = new BindForm(oauthData);
		model.addAttribute(bindForm);
		return "/oauth/bind";
	}

	@RequestMapping(value = "/oauth/userBind", method = RequestMethod.POST)
	public String bind(@Valid BindForm bindForm, BindingResult result,
			HttpSession session) {
		OauthData oauthData = (OauthData) session
				.getAttribute(SessionNameUtils.OAUTH);
		if (oauthData == null) {
			return "redirect:/500.html";
		}

		bindForm.onValid(result, oauthData);
		if (result.hasErrors()) {
			return "/oauth/bind";
		}

		try {
			User user = this.userService.create(bindForm.parse());
			this.userService.autoLogin(user.getEmail());
		} catch (Exception ex) {
			logger.error(ex);
			if (ex.getCause() != null
					&& ex.getCause().getClass() == ConstraintViolationException.class) {
				bindForm.addFieldError(result, "email", "bind.error.emailexist");
			} else {
				bindForm.addError(result, "bind.error.failed");
			}

			return "/oauth/bind";
		}

		return "redirect:/";
	}

	@RequestMapping(value = "/oauth/login", method = RequestMethod.POST)
	public String login() throws BusinessException {
		return "redirect:" + this.oauthClient.getAuthorizeURL();
	}

	@RequestMapping(value = "/oauth/callback", method = RequestMethod.GET)
	public String callback(
			@RequestParam(value = "code", required = false) String code,
			HttpSession session) throws BusinessException {

		if (StringUtils.isEmpty(code)) {
			return this.login();
		}

		TokenInfo oauthToken = this.oauthClient.getTokenInfoByCode(code,
				this.appProperties.getApplicationUrl());

		UserInfo userInfo = this.apiClient.getUserInfo(oauthToken
				.getAccessToken());

		OauthData oauthData = this.updateOauthData(oauthToken, userInfo);
		session.setAttribute(SessionNameUtils.OAUTH, oauthData);

		User user = this.userService.findBySAPAccount(userInfo.getEmail());
		if (user == null) {
			return "redirect:/oauth/bind.html";
		}

		this.userService.autoLogin(user.getEmail());
		return "redirect:/";
	}

	@RequestMapping(value = "/oauth/install", method = RequestMethod.GET)
	public void install(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws BusinessException, IOException {
		String timestamp = request.getParameter("timestamp");
		String hmac = request.getParameter("hmac");
		String op = request.getParameter("op");
		this.oauthClient.verifySignature(timestamp, hmac);
		if (StringUtils.equalsIgnoreCase(op, OP_INSTALL)) {
			this.startAuthorize(request, response);
		} else if (StringUtils.equalsIgnoreCase(op, OP_UNINSTALL)) {
			this.uninstall(request, response);
		} else {
			this.exchangeToken(request, response);
		}
	}

	private OauthData updateOauthData(TokenInfo oauthToken, UserInfo userInfo) {
		List<OauthData> oauthDatas = this.oauthDataRepository
				.findByCompanyCodeAndUserCode(userInfo.getCompanyCode(),
						userInfo.getCode());
		OauthData oauthData = null;
		if (oauthDatas.size() == 0) {
			oauthData = new OauthData();
		} else {
			oauthData = oauthDatas.get(0);
		}

		oauthData.setAccessToken(oauthToken.getAccessToken());
		oauthData.setRefreshToken(oauthToken.getRefreshToken());
		oauthData.setScope(oauthToken.getScope());
		oauthData.setUserEmail(userInfo.getEmail());
		oauthData.setCompanyCode(userInfo.getCompanyCode());
		oauthData.setUserCode(userInfo.getCode());
		return this.oauthDataRepository.save(oauthData);
	}

	private void uninstall(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String companyCode = request.getParameter("companyCode");
		this.oauthDataRepository.deleteByCompanyCode(companyCode);
		response.sendRedirect("/");
	}

	private void exchangeToken(HttpServletRequest request,
			HttpServletResponse response) throws BusinessException, IOException {

		String code = request.getParameter("code");
		if (StringUtils.isEmpty(code)) {
			throw new BusinessException(ErrorCode.AUTHORIZE_CODE_NONE);
		}

		TokenInfo oauthToken = this.oauthClient.getTokenInfoByCode(code,
				this.appProperties.getInstallationUrl());

		UserInfo userInfo = this.apiClient.getUserInfo(oauthToken
				.getAccessToken());

		OauthData oauthData = this.updateOauthData(oauthToken, userInfo);
		User user = this.userService.findBySAPAccount(oauthData.getUserEmail());
		session.setAttribute(SessionNameUtils.OAUTH, oauthData);
		if (user == null) {
			response.sendRedirect("/oauth/bind.html");
		} else {
			response.sendRedirect("/");
		}
	}

	private void startAuthorize(HttpServletRequest request,
			HttpServletResponse response) throws BusinessException, IOException {
		String purpose = request.getParameter("purpose");
		String redirectUrl = null;
		if (StringUtils.equalsIgnoreCase(purpose, PURPOSE_TESTING)) {
			redirectUrl = this.oauthClient.getTestAuthorizeURL();
		} else {
			redirectUrl = this.oauthClient.getAuthorizeURL();
		}

		response.sendRedirect(redirectUrl);
	}
}
