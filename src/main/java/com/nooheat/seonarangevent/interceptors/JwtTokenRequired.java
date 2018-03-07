package com.nooheat.seonarangevent.interceptors;

import com.nooheat.seonarangevent.exception.UidNotFoundException;
import com.nooheat.seonarangevent.support.JwtVerifier;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtTokenRequired extends HandlerInterceptorAdapter {

    static String secret = System.getenv("SEONARANG_EVENT_SECRET");

    static {
        if (secret == null) secret = "default secret";
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String tokenStr = request.getHeader("seonarang-event-access-token");
        PrintWriter writer = response.getWriter();

        System.out.println(tokenStr);
        if (tokenStr == null) {
            response.setStatus(401);
            writer.write("'seonarang-event-access-token' required");
            writer.close();
            return false;
        }

        try {
            JwtVerifier.isAuthenticToken(tokenStr);
        } catch (UidNotFoundException te) {
            response.setStatus(401);
            writer.write("Your token doesn't contain 'uid' value");
            writer.close();
            return false;
        } catch (ExpiredJwtException ee) {
            response.setStatus(401);
            writer.write("Your token has expired");
            writer.close();
            return false;
        } catch (Exception e) {
            response.setStatus(401);
            writer.write("We can't trust your access token. please relogin");
            writer.close();
            return false;
        }

        return true;
    }
}
