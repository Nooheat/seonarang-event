package com.nooheat.seonarangevent.interceptors;

import com.nooheat.seonarangevent.domain.user.User;
import com.nooheat.seonarangevent.domain.user.UserRepository;
import com.nooheat.seonarangevent.exception.JwtTokenClaimNotFoundException;
import com.nooheat.seonarangevent.exception.JwtTokenStringNotFoundException;
import com.nooheat.seonarangevent.exception.UidNotValidException;
import com.nooheat.seonarangevent.service.UserService;
import com.nooheat.seonarangevent.support.JwtManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenRequired extends HandlerInterceptorAdapter {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException, JwtTokenClaimNotFoundException, JwtTokenStringNotFoundException {
        System.out.println("PREHANDLE [JWTTOKENREQUIRED]");
        Cookie token = WebUtils.getCookie(request, "twitch-event-access-token");

        if (token == null) {
            throw new JwtTokenStringNotFoundException();
        }

        String tokenStr = token.getValue();

        String uid = JwtManager.parse(tokenStr).getBody().get("userId", String.class);

        User user = userService.findByUid(uid);
        if (user == null) {
            throw new UidNotValidException();
        }

        return true;
    }
}
