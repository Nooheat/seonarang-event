package com.nooheat.seonarangevent.interceptors;

import com.google.gson.JsonObject;
import com.nooheat.seonarangevent.advice.ErrorResponse;
import com.nooheat.seonarangevent.exception.JwtTokenStringNotFoundException;
import com.nooheat.seonarangevent.support.JwtManager;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Intercepter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
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
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("PREHANDLE [ONLYSTREAMER]");

        String tokenStr = WebUtils.getCookie(request, "twitch-event-access-token").getValue();

        if (tokenStr == null) {
            throw new JwtTokenStringNotFoundException();
        }


        Jws<Claims> claims = JwtManager.parse(tokenStr);
        if (!claims.getBody().get("permission", Boolean.class)) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .message("You have not permission")
                    .path(request.getRequestURI())
                    .timestamp(LocalDateTime.now())
                    .build();
            response.setStatus(403);
            response.setHeader("Content-Type", "application/json");
            PrintWriter writer = response.getWriter();
            writer.write(errorResponse.toString());
            writer.close();
            return false;
        }
        
        return true;
    }
}
