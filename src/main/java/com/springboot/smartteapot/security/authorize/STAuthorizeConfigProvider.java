package com.springboot.smartteapot.security.authorize;

import com.springboot.smartteapot.properties.SecurityConstants;
import com.springboot.smartteapot.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

@Component
@Order(Integer.MIN_VALUE)
public class STAuthorizeConfigProvider implements AuthorizeConfigProvider{

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {

        config
                .antMatchers(
                "/css/**","/images/**","/**.ico","/code/*","/static/**","/user/register",
                SecurityConstants.DEFAULT_REGISTER_PAGE_URL,
                SecurityConstants.DEFAULT_SESSION_INVALID_URL,
                securityProperties.getWeb().getLogoutPage(),
                securityProperties.getWeb().getRegPage(),
                securityProperties.getWeb().getLoginPage()
                    ).permitAll()
                .antMatchers("/admin/me","/admin/password","/pot","/pot/latest","pot/execute","pot/tempData","pot/isOnline","/sharing","/sharing/**").authenticated()
                .antMatchers(HttpMethod.GET,"/role","/admin/exist").authenticated();

        return false;
    }
}
