package com.springboot.smartteapot.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.smartteapot.common.SimpleResponse;
import com.springboot.smartteapot.common.LoginType;
import com.springboot.smartteapot.properties.SecurityConstants;
import com.springboot.smartteapot.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//自定义登陆失败处理类
@Component("stAuthenticationFailureHandler")
public class STAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

//public class STAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {

        logger.info("Sign in failure");

        if(LoginType.JSON.equals(securityProperties.getWeb().getLoginType())){
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(e.getMessage())));
        }else
        {
            System.out.println("登陆失败直接转跳");
            HttpSession session = httpServletRequest.getSession(false);
            if(null!=session || isAllowSessionCreation()){
                httpServletRequest.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,e);
            }

            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, SecurityConstants.DEFAULT_SIGN_IN_FAIL_URL);
            //super.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
        }
    }
}
