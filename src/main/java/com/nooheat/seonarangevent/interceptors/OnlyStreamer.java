package com.nooheat.seonarangevent.interceptors;

import com.nooheat.seonarangevent.domain.user.User;
import com.nooheat.seonarangevent.exception.JwtTokenStringNotFoundException;
import com.nooheat.seonarangevent.exception.UidNotValidException;
import com.nooheat.seonarangevent.exception.UserNotPermittedException;
import com.nooheat.seonarangevent.service.UserService;
import com.nooheat.seonarangevent.support.JwtManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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


        String uid = JwtManager.parse(tokenStr).getBody().get("userId", String.class);
        User user = userService.findByUserId(uid);

        if (user == null) {
            throw new UidNotValidException();
        } else if (!user.getPermission()) {
            throw new UserNotPermittedException(user.getTwitchId());
        }

        return true;
    }
}
