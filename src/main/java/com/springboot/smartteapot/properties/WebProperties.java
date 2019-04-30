package com.springboot.smartteapot.properties;

import com.springboot.smartteapot.common.LoginType;

//自定义配置读取类web
public class WebProperties {

    private SessionProperties session = new SessionProperties();

    private String loginPage ;//= SecurityConstants.DEFAULT_SIGN_IN_PAGE_URL;

    private LoginType loginType = LoginType.JSON;

    private String logoutPage;

    private String regPage;

    private int rememberMeSeconds = 604800;

    public SessionProperties getSession() {
        return session;
    }

    public void setSession(SessionProperties session) {
        this.session = session;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public String getLogoutPage() {
        return logoutPage;
    }

    public void setLogoutPage(String logoutPage) {
        this.logoutPage = logoutPage;
    }

    public String getRegPage() {
        return regPage;
    }

    public void setRegPage(String regPage) {
        this.regPage = regPage;
    }
}
