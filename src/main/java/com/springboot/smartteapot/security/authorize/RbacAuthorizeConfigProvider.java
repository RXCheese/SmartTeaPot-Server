package com.springboot.smartteapot.security.authorize;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

@Component
public class RbacAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {

        config
                .antMatchers(HttpMethod.GET, "/fonts/**","/scripts/**","**/favicon.ico","/styles/**","/user/register","/register","/logOut"
                        ,"/404.html","/403.html","/500.html","/405.html","/js/**","/bootstrap/**","/css/**","/images/**","/sessionInvalid.html").permitAll()
                .antMatchers(HttpMethod.GET, "/**/*.html", "/manage", "/manage/*", "/resource","/views/*","/signOut").authenticated()
                .anyRequest()
                    .access("@rbacService.hasPermission(request,authentication)");


        return true;
    }
}
