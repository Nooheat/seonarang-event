package com.nooheat.seonarangevent.web;

import com.nooheat.seonarangevent.domain.event.Event;
import com.nooheat.seonarangevent.domain.user.User;
import com.nooheat.seonarangevent.dto.user.TwitchOauthCallbackDto;
import com.nooheat.seonarangevent.exception.UidNotValidException;
import com.nooheat.seonarangevent.service.UserService;
import com.nooheat.seonarangevent.support.JwtManager;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@RestController
@NoArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login/twitch")
    public void twitchOauthCallback(TwitchOauthCallbackDto oauthDto, HttpServletResponse response) throws IOException {
        User user = userService.createOrFindUser(oauthDto);
        Cookie cookie = new Cookie("twitch-event-access-token", JwtManager.generateJwtToken(user));
        cookie.setPath("/"); // 설정 안할시 /login으로 지정됨
        cookie.setMaxAge(60 * 60 * 24 * 365);
        response.addCookie(cookie);
        response.sendRedirect("/");
    }

    @GetMapping("/me/events")
    public Collection<Event> getMyEvents(@CookieValue(value = "twitch-event-access-token", defaultValue = "null") String accessToken) throws Exception {
        String uid = JwtManager.parse(accessToken).getBody().get("userId", String.class);
        User user = userService.findByUserId(uid);

        if (user == null) throw new UidNotValidException();
        else return user.getEvents();
    }
}
