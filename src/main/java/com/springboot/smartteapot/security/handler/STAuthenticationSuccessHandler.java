package com.springboot.smartteapot.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.smartteapot.common.LoginType;
import com.springboot.smartteapot.common.SimpleResponse;
import com.springboot.smartteapot.properties.SecurityConstants;
import com.springboot.smartteapot.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//自定义登陆成功处理类
@Component("stAuthenticationSuccessHandler")
public class STAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

//public class STAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {

        logger.info("login success");

        SavedRequest savedRequest = requestCache.getRequest(httpServletRequest, httpServletResponse);

        if (null != savedRequest) {
            logger.info("Direct Url Request: " + savedRequest.getRedirectUrl());
        }
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, SecurityConstants.DEFAULT_MANAGE_PAGE_URL);

//        if (null != savedRequest) {
//            String targetUrl = savedRequest.getRedirectUrl();
//            logger.info("Direct Url Request: " + targetUrl);
//
//            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, targetUrl);
//        }else{
//            logger.info("Direct Url Request: " + SecurityConstants.DEFAULT_MANAGE_PAGE_URL);
//            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, SecurityConstants.DEFAULT_MANAGE_PAGE_URL);
//        }
//        super.onAuthenticationSuccess(httpServletRequest,httpServletResponse,authentication);


//        if(LoginType.JSON.equals(securityProperties.getWeb().getLoginType())){
//            httpServletResponse.setContentType("application/json;charset=UTF-8");
//            String type = authentication.getClass().getSimpleName();
//            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(type)));
//        }else {
//            super.onAuthenticationSuccess(httpServletRequest,httpServletResponse,authentication);
//        }



    }
}
