package com.nooheat.seonarangevent.web;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nooheat.seonarangevent.dto.user.TwitchOauthCallbackDto;
import lombok.NoArgsConstructor;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@NoArgsConstructor
public class UserController {

    private static final String CLIENT_ID = System.getenv("SEONARANG_EVENT_CLIENT_ID");
    private static final String CLIENT_SECRET = System.getenv("SEONARANG_EVENT_CLIENT_SECRET");

    @GetMapping("/callback")
    public void twitchOauthCallback(TwitchOauthCallbackDto params, HttpServletResponse response) throws IOException {

        String accessToken = getAccessToken(params.getCode());

        JsonObject userInfo = getUserInformation(accessToken);

        response.sendRedirect("/");
    }

    private String getAccessToken(String code) {
        final String INFO_URL = "https://id.twitch.tv/oauth2/token?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&code="
                + code + "&grant_type=authorization_code&redirect_uri=http://localhost:8080/callback";

        RestTemplate getUserAccessToken = new RestTemplate();

        String res = getUserAccessToken.postForObject(INFO_URL, null, String.class);
        JsonObject tokenObj = new JsonParser().parse(res).getAsJsonObject();

        return tokenObj.get("access_token").getAsString();
    }

    private JsonObject getUserInformation(String accessToken) {
        RestTemplate getUserInformation = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);
        String infoStr = getUserInformation.exchange("https://api.twitch.tv/helix/users", HttpMethod.GET, entity, String.class).getBody();

//        형식 : {"data":[{"id":"189522245","login":"nooheat1228","display_name":"nooheat1228","type":"","broadcaster_type":"","description":"","profile_image_url":"https://static-cdn.jtvnw.net/user-default-pictures/0ecbb6c3-fecb-4016-8115-aa467b7c36ed-profile_image-300x300.jpg","offline_image_url":"","view_count":1}]}
        return new JsonParser().parse(infoStr).getAsJsonObject().get("data").getAsJsonArray().get(0).getAsJsonObject();
    }

}
