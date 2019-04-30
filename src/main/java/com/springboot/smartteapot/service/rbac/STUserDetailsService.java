package com.springboot.smartteapot.service.rbac;

import com.springboot.smartteapot.bean.Admin;
import com.springboot.smartteapot.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//用户登陆服务
@Component
@Transactional
public class STUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        String pwd = passwordEncoder.encode("123456".trim());
//        logger.info("'"+username+"'："+ pwd);
//        return new UserInfo(username, pwd,
//                true,
//                true,
//                true,
//                true,
//                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
        logger.info("登陆用户：" + username);
        Admin admin = adminRepository.findByUsername(username);
        if(null == admin.getUrls()){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return admin;

    }

}
