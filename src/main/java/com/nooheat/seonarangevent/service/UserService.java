package com.nooheat.seonarangevent.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.nooheat.seonarangevent.domain.user.User;
import com.nooheat.seonarangevent.domain.user.UserRepository;
import com.nooheat.seonarangevent.dto.user.TwitchOauthCallbackDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private static final String CLIENT_ID = System.getenv("SEONARANG_EVENT_CLIENT_ID");
    private static final String CLIENT_SECRET = System.getenv("SEONARANG_EVENT_CLIENT_SECRET");

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User createOrFindUser(TwitchOauthCallbackDto oauthDto) {

        String accessToken = getAccessToken(oauthDto.getCode());

        UserInfo userInfo = getUserInformation(accessToken);

        User user = userRepository.findByEmail(userInfo.getEmail());
        if (user != null) return user;
        else return userRepository.save(User.builder()
                .displayName(userInfo.getDisplayName())
                .twitchId(userInfo.getTwitchId())
                .email(userInfo.getEmail())
                .build());
    }

    private String getAccessToken(String code) {
        final String INFO_URL = "https://id.twitch.tv/oauth2/token?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&code="
                + code + "&grant_type=authorization_code&redirect_uri=http://localhost:8080/login/twitch&scope=user:read:email";

        RestTemplate getUserAccessToken = new RestTemplate();

        String res = getUserAccessToken.postForObject(INFO_URL, null, String.class);
        JsonObject tokenObj = new JsonParser().parse(res).getAsJsonObject();

        return tokenObj.get("access_token").getAsString();
    }

    private UserInfo getUserInformation(String accessToken) {
        RestTemplate getUserInformation = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);

        String infoStr = getUserInformation.exchange("https://api.twitch.tv/helix/users", HttpMethod.GET, entity, String.class).getBody();

//      형식 : {"data":[{"id":"189522245","login":"nooheat1228","display_name":"nooheat1228","type":"","broadcaster_type":"","description":"","profile_image_url":"https://static-cdn.jtvnw.net/user-default-pictures/0ecbb6c3-fecb-4016-8115-aa467b7c36ed-profile_image-300x300.jpg","offline_image_url":"","view_count":1,"email":"nooheat1228@gmail.com"}]}

        return new UserInfo(infoStr);
    }



    // POJO
    @Getter
    private class UserInfo {
        private String email;
        private String displayName;
        private String twitchId;

        UserInfo(String infoStr) throws JsonSyntaxException {
            JsonObject userInfoJson = new JsonParser().parse(infoStr).getAsJsonObject().get("data").getAsJsonArray().get(0).getAsJsonObject();
            this.email = userInfoJson.get("email").getAsString();
            this.displayName = userInfoJson.get("display_name").getAsString();
            this.twitchId = userInfoJson.get("login").getAsString();
        }
    }
}
