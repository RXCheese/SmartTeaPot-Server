/**
 * 
 */
package com.springboot.smartteapot.common.session;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springboot.smartteapot.common.SimpleResponse;
import com.springboot.smartteapot.properties.SecurityConstants;
import com.springboot.smartteapot.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 抽象的session失效处理器
 */
public class AbstractSessionStrategy {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 跳转的url
	 */
	private String destinationUrl;
	/**
	 * 系统配置信息
	 */
	private SecurityProperties securityProperties;
	/**
	 * 重定向策略
	 */
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	/**
	 * 跳转前是否创建新的session
	 */
	private boolean createNewSession = true;

	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * @param securityProperties
	 */
	public AbstractSessionStrategy(SecurityProperties securityProperties) {
		String invalidSessionUrl = securityProperties.getWeb().getSession().getSessionInvalidUrl();
		Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl), "url must start with '/' or with 'http(s)'");
		//Assert.isTrue(StringUtils.endsWithIgnoreCase(invalidSessionUrl, ".html"), "url must end with '.html'");
		this.destinationUrl = invalidSessionUrl;
		this.securityProperties = securityProperties;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.session.InvalidSessionStrategy#
	 * onInvalidSessionDetected(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	protected void onSessionInvalid(HttpServletRequest request, HttpServletResponse response) throws IOException {

		logger.info("session失效");
		
		if (createNewSession) {
			request.getSession();
		}


		String sourceUrl = request.getRequestURI();
		String targetUrl;
		if(StringUtils.equals(sourceUrl, securityProperties.getWeb().getLoginPage())
				|| StringUtils.equals(sourceUrl, securityProperties.getWeb().getLogoutPage())){
			targetUrl = sourceUrl;
		}else{
			targetUrl = SecurityConstants.DEFAULT_SIGN_IN_PAGE_URL;
		}
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("refresh","3;URL="+targetUrl);
		redirectStrategy.sendRedirect(request,response,SecurityConstants.DEFAULT_SESSION_INVALID_URL+".html");
//
//		String sourceUrl = request.getRequestURI();
//		String targetUrl;
//
//		if (StringUtils.endsWithIgnoreCase(sourceUrl, ".html")) {
//			if(StringUtils.equals(sourceUrl, securityProperties.getWeb().getLoginPage())
//					|| StringUtils.equals(sourceUrl, securityProperties.getWeb().getLogoutPage())){
//				targetUrl = sourceUrl;
//			}else{
//				targetUrl = destinationUrl;
//			}
//			logger.info("跳转到:"+targetUrl);
//			redirectStrategy.sendRedirect(request, response, targetUrl);
//		} else {
//			//Object result = buildResponseContent(request);
//			response.setStatus(HttpStatus.UNAUTHORIZED.value());
//			response.setContentType("application/json;charset=UTF-8");
//			//response.getWriter().write(objectMapper.writeValueAsString(result));
//			response.setHeader("refresh","3;URL="+SecurityConstants.DEFAULT_SIGN_IN_PAGE_URL);
//			redirectStrategy.sendRedirect(request,response,SecurityConstants.DEFAULT_SESSION_INVALID_URL);
//		}

	}

	/**
	 * @param request
	 * @return
	 */
	protected Object buildResponseContent(HttpServletRequest request) {
		String message = "session已失效";
		if (isConcurrency()) {
			message = message + "，有可能是并发登录导致的";
		}
		return new SimpleResponse(message);
	}

	/**
	 * session失效是否是并发导致的
	 * 
	 * @return
	 */
	protected boolean isConcurrency() {
		return false;
	}

	/**
	 * Determines whether a new session should be created before redirecting (to
	 * avoid possible looping issues where the same session ID is sent with the
	 * redirected request). Alternatively, ensure that the configured URL does
	 * not pass through the {@code SessionManagementFilter}.
	 *
	 * @param createNewSession
	 *            defaults to {@code true}.
	 */
	public void setCreateNewSession(boolean createNewSession) {
		this.createNewSession = createNewSession;
	}

}
