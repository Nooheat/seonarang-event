package com.nooheat.seonarangevent.service;

import com.google.gson.JsonObject;
import com.nooheat.seonarangevent.domain.user.User;
import com.nooheat.seonarangevent.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    @Transactional
    public User createOrFindUser(JsonObject userInfo) {
        User user = userRepository.findByEmail(userInfo.get("email").getAsString());
        if (user != null) return user;
        else return userRepository.save(User.builder()
                .displayName(userInfo.get("display_name").getAsString())
                .twitchId(userInfo.get("login").getAsString())
                .email(userInfo.get("email").getAsString())
                .build());
    }
}
