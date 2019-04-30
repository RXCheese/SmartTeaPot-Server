package com.springboot.smartteapot.common.session;

import com.springboot.smartteapot.properties.SecurityProperties;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

public class STExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

    public STExpiredSessionStrategy(SecurityProperties securityProperties) {
        super(securityProperties);
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
//        event.getResponse().setContentType("application/json;charset=UTF-8");
//        event.getResponse().getWriter().write("Concurrent login");
        onSessionInvalid(event.getRequest(), event.getResponse());

    }


    @Override
    public boolean isConcurrency() {
        return true;
    }

}
