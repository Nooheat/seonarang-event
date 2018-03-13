package com.nooheat.seonarangevent.interceptors;

import com.nooheat.seonarangevent.exception.JwtTokenClaimNotFoundException;
import com.nooheat.seonarangevent.exception.JwtTokenStringNotFoundException;
import com.nooheat.seonarangevent.support.JwtManager;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenRequired extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException, JwtTokenClaimNotFoundException, JwtTokenStringNotFoundException {
        System.out.println("PREHANDLE [JWTTOKENREQUIRED]");
        String tokenStr = WebUtils.getCookie(request, "twitch-event-access-token").getValue();

        if (tokenStr == null) {
            throw new JwtTokenStringNotFoundException();
        }

        JwtManager.parse(tokenStr);

        return true;
    }
}
