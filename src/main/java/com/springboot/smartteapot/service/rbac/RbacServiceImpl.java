package com.springboot.smartteapot.service.rbac;

import com.springboot.smartteapot.bean.Admin;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Component("rbacService")
public class RbacServiceImpl implements RbacService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object principal = authentication.getPrincipal();

        boolean hasPermission = false;

        if (principal instanceof UserDetails) {

            String username = ((UserDetails) principal).getUsername();
            if(StringUtils.equals(username,"admin")){
                hasPermission = true;
            }else {

            //读取用户所用有权限的URL
            Set<String> urls = ((Admin) principal).getUrls();

            for (String url :
                    urls) {
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }

            }

        }
        return hasPermission;
    }
}
