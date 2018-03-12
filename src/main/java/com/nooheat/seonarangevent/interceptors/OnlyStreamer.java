package com.nooheat.seonarangevent.interceptors;

import com.google.gson.JsonObject;
import com.nooheat.seonarangevent.exception.PermissionNotFoundException;
import com.nooheat.seonarangevent.exception.UidNotFoundException;
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

@Component
public class OnlyStreamer extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("PREHANDLE [ONLYSTREAMER]");

        System.out.println("PREHANDLE [JWTTOKENREQUIRED]");
        String tokenStr = WebUtils.getCookie(request, "twitch-event-access-token").getValue();

        if (tokenStr == null) {
            handleUnauthorizedWithMessage(response, "'twitch-event-access-token' required");
            return false;
        }

        try {
            Jws<Claims> claims = JwtManager.parse(tokenStr);
            if (!claims.getBody().get("permission", Boolean.class)) {
                handleUnauthorizedWithMessage(response, "You are not permitted.");
                return false;
            }
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
