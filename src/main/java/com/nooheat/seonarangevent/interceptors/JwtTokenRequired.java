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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        System.out.println("PREHANDLE [JWTTOKENREQUIRED]");
        String tokenStr = WebUtils.getCookie(request, "twitch-event-access-token").getValue();

        if (tokenStr == null) {
            handleUnauthorizedWithMessage(response, "'twitch-event-access-token' required");
            return false;
        }

        try {
            JwtManager.parse(tokenStr);
        } catch (UidNotFoundException te) {
            handleUnauthorizedWithMessage(response, "Your token doesn't contain 'uid' value");
            return false;
        } catch (PermissionNotFoundException te) {
            handleUnauthorizedWithMessage(response, "Your token doesn't contain 'permission' value");
            return false;
        } catch (ExpiredJwtException ee) {
            handleUnauthorizedWithMessage(response, "Your token has expired");
            return false;
        } catch (Exception e) {
            handleUnauthorizedWithMessage(response, "We can't trust your access token. please relogin");
            return false;
        }

        return true;
    }

    private static void handleUnauthorizedWithMessage(HttpServletResponse response, String message) throws IOException {
        JsonObject errMessage = new JsonObject();
        response.setStatus(401);
        response.setHeader("Content-Type", "application/json");
        errMessage.addProperty("message", message);
        PrintWriter writer = response.getWriter();
        writer.write(errMessage.toString());
        writer.close();
    }
}
