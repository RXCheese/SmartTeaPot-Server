package com.springboot.smartteapot.security.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

import java.util.Arrays;

@Configuration
public class STFilterConfig {

    @Autowired
    FilterRegistrationBean registrationBean;

    @Bean
    public FilterRegistrationBean registrationBean() {

        registrationBean.setFilter(new OpenEntityManagerInViewFilter());
        registrationBean.setName("openEntityManagerInViewFilter");

        registrationBean.setUrlPatterns(Arrays.asList("/*"));
        registrationBean.setOrder(Integer.MAX_VALUE);

        return registrationBean;
    }


}
