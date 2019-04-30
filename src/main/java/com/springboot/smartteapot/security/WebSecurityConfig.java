package com.springboot.smartteapot.security;

import com.springboot.smartteapot.common.validate.ValidateCodeSecurityConfig;
import com.springboot.smartteapot.properties.SecurityConstants;
import com.springboot.smartteapot.security.authentication.sms.SmsCodeAuthenticationSecurityConfig;
import com.springboot.smartteapot.security.authorize.AuthorizeConfigManger;
import com.springboot.smartteapot.properties.SecurityProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.sql.DataSource;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    //数据源
    @Autowired
    private DataSource dataSource;

    //读取自定义登陆页面配置参数
    @Autowired
    private SecurityProperties securityProperties;

    //用户登陆服务
    @Autowired
    private UserDetailsService  userDetailsService;

    //自定义登陆成功处理类
    @Autowired
    private AuthenticationSuccessHandler stAuthenticationSuccessHandler;

    //自定义登陆失败处理类
    @Autowired
    private AuthenticationFailureHandler stAuthenticationFailureHandler;

    //自定义注销成功处理类
    @Autowired
    private LogoutSuccessHandler stLogoutSuccessHandler;

    @Autowired
    private AuthorizeConfigManger authorizeConfigManger;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    //记住我功能的token存取器配置
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    //密码加密配置函数
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    //安全登陆认证配置类
    @Override
    protected void configure(HttpSecurity http) throws Exception {


        //验证码过滤器
        http.apply(validateCodeSecurityConfig).and()
            .apply(smsCodeAuthenticationSecurityConfig).and()
                //表单登陆配置
            .formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .permitAll()
                .loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
                .successHandler(stAuthenticationSuccessHandler)
                .failureHandler(stAuthenticationFailureHandler)
                .and()
                //记住我配置
            .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getWeb().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()
            .sessionManagement()
                .invalidSessionStrategy(invalidSessionStrategy)
                //.invalidSessionUrl("/session/invalid")
                .maximumSessions(1)
                .maxSessionsPreventsLogin(securityProperties.getWeb().getSession().isMaxSessionsPreventsLogin())
                .expiredSessionStrategy(sessionInformationExpiredStrategy)
                .and()
                .and()
            .logout()
                .logoutUrl(SecurityConstants.DEFAULT_LOG_OUT_PAGE_URL)
                //.logoutSuccessUrl("/logOut")
                .logoutSuccessHandler(stLogoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .and()
            .csrf().disable();

        //用户权限配置
        authorizeConfigManger.config(http.authorizeRequests());
    }
}