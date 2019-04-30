package com.springboot.smartteapot.security;

import com.springboot.smartteapot.common.SimpleResponse;
import com.springboot.smartteapot.properties.SecurityConstants;
import com.springboot.smartteapot.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//身份认证控制器
@RestController
public class WebSecurityController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

//    @Value("${st.web.loginPage}")
//    String temp;


    //当需要身份认证时，跳转到此处控制器
    @RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);


        if (null != savedRequest) {
            String targetUrl = savedRequest.getRedirectUrl();
            logger.info("Direct Url Request: " + targetUrl);

            response.setContentType("text/html;charset=UTF-8");
            redirectStrategy.sendRedirect(request, response, securityProperties.getWeb().getLoginPage());
        }
        response.setContentType("text/html;charset=UTF-8");
        return new SimpleResponse("The Service need identity authentication, Please direct users to the Sign in page");
    }

//    @RequestMapping("/session/invalid")
//    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
//    public SimpleResponse sessionInvalid() {
//        String message = "session time over";
//        return new SimpleResponse(message);
//    }

//    @GetMapping("/getcuruser")
//    public Object getCurrentUser(Authentication authentication) {
//        return authentication;
//    }
//
//    @GetMapping("/getcuruser2")
//    public Object getCurrentUser2(){
//        return SecurityContextHolder.getContext().getAuthentication();
//    }
//
//    @GetMapping("/getcuruser3")
//    public Object getCurrentUser3(@AuthenticationPrincipal UserDetails user) {
//        return user;
//    }
}
