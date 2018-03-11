package com.nooheat.seonarangevent.interceptors;

import com.google.gson.JsonObject;
import com.nooheat.seonarangevent.exception.PermissionNotFoundException;
import com.nooheat.seonarangevent.exception.UidNotFoundException;
import com.nooheat.seonarangevent.support.JwtManager;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

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
        String tokenStr = WebUtils.getCookie(request, "twitch-event-access-token").getValue();
        JsonObject errMessage = new JsonObject();

        if (tokenStr == null) {
            response.setStatus(401);
            response.setHeader("Content-Type", "application/json");
            errMessage.addProperty("message", "'twitch-event-access-token' required");
            PrintWriter writer = response.getWriter();
            writer.write(errMessage.toString());
            writer.close();
            return false;
        }

        try {
            JwtManager.parse(tokenStr);
        } catch (UidNotFoundException te) {
            response.setStatus(401);
            response.setHeader("Content-Type", "application/json");
            errMessage.addProperty("message", "Your token doesn't contain 'uid' value");
            PrintWriter writer = response.getWriter();
            writer.write(errMessage.toString());
            writer.close();
            return false;
        } catch (PermissionNotFoundException te) {
            response.setStatus(401);
            response.setHeader("Content-Type", "application/json");
            errMessage.addProperty("message", "Your token doesn't contain 'permission' value");
            PrintWriter writer = response.getWriter();
            writer.write(errMessage.toString());
            writer.close();
            return false;
        } catch (ExpiredJwtException ee) {
            response.setStatus(401);
            response.setHeader("Content-Type", "application/json");
            errMessage.addProperty("message", "Your token has expired");
            PrintWriter writer = response.getWriter();
            writer.write(errMessage.toString());
            writer.close();
            return false;
        } catch (Exception e) {
            response.setStatus(401);
            response.setHeader("Content-Type", "application/json");
            errMessage.addProperty("message", "We can't trust your access token. please relogin");
            PrintWriter writer = response.getWriter();
            writer.write(errMessage.toString());
            writer.close();
            return false;
        }

        return true;
    }
}
