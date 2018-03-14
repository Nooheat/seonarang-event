package com.nooheat.seonarangevent.web;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nooheat.seonarangevent.domain.user.User;
import com.nooheat.seonarangevent.dto.user.TwitchOauthCallbackDto;
import com.nooheat.seonarangevent.service.UserService;
import com.nooheat.seonarangevent.support.JwtManager;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@NoArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login/twitch")
    public void twitchOauthCallback(TwitchOauthCallbackDto oauthDto, HttpServletResponse response) throws IOException {
        User user = userService.createOrFindUser(oauthDto);

        response.addCookie(new Cookie("twitch-event-access-token", JwtManager.generateJwtToken(user)));
        response.sendRedirect("/");
    }

}
