package com.nooheat.seonarangevent.interceptors;

import com.google.gson.JsonObject;
import com.nooheat.seonarangevent.advice.ErrorResponse;
import com.nooheat.seonarangevent.domain.user.User;
import com.nooheat.seonarangevent.domain.user.UserRepository;
import com.nooheat.seonarangevent.exception.JwtTokenStringNotFoundException;
import com.nooheat.seonarangevent.exception.UidNotValidException;
import com.nooheat.seonarangevent.exception.UserNotPermittedException;
import com.nooheat.seonarangevent.service.UserService;
import com.nooheat.seonarangevent.support.JwtManager;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Intercepter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Component
public class OnlyStreamer extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("PREHANDLE [ONLYSTREAMER]");

        String tokenStr = WebUtils.getCookie(request, "twitch-event-access-token").getValue();

        if (tokenStr == null) {
            throw new JwtTokenStringNotFoundException();
        }


        String uid = JwtManager.parse(tokenStr).getBody().get("uid", String.class);
        User user = userService.findByUid(uid);

        if (user == null) {
            throw new UidNotValidException();
        } else if (!user.getPermission()) {
            throw new UserNotPermittedException(user.getTwitchId());
        }

        return true;
    }
}
