/**
 * 
 */
package com.springboot.smartteapot.common.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springboot.smartteapot.properties.SecurityProperties;
import org.springframework.security.web.session.InvalidSessionStrategy;

/**
 * 默认的session失效处理策略
 */
public class STInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {

	public STInvalidSessionStrategy(SecurityProperties securityProperties) {
		super(securityProperties);
	}

	@Override
	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		onSessionInvalid(request, response);
	}

}
