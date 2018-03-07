package com.nooheat.seonarangevent.dto.user;


import com.nooheat.seonarangevent.domain.user.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class TwitchOauthCallbackDto {

    private String code;
    private String scope;
    private String state;

    @Builder
    public TwitchOauthCallbackDto(String code, String scope, String state) {
        this.code = code;
        this.scope = scope;
        this.state = state;
    }
}
