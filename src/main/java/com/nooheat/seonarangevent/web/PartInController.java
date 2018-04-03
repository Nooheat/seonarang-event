package com.nooheat.seonarangevent.web;

import com.nooheat.seonarangevent.domain.event.Event;
import com.nooheat.seonarangevent.domain.user.User;
import com.nooheat.seonarangevent.dto.partIn.kda.KdaEventPartInDto;
import com.nooheat.seonarangevent.exception.EventNotFoundException;
import com.nooheat.seonarangevent.exception.UidNotValidException;
import com.nooheat.seonarangevent.service.EventService;
import com.nooheat.seonarangevent.service.KdaEventPartInService;
import com.nooheat.seonarangevent.service.UserService;
import com.nooheat.seonarangevent.support.JwtManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PartInController {
    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private KdaEventPartInService kdaEventPartInService;

    @PostMapping("/event/kda/participate")
    public void kdaEventParticipate(@CookieValue(value = "twitch-event-access-token", defaultValue = "null") String accessTokenStr, KdaEventPartInDto kdaEventPartInDto) {
        // TODO: DTO 전달 테스트
        Jws<Claims> claims = JwtManager.parse(accessTokenStr);
        String userId = claims.getBody().get("userId", String.class);
        Long eventId = kdaEventPartInDto.getEventId();

        User user = userService.findByUserId(userId);
        Event event = eventService.findByEventId(eventId);

        if (user == null) throw new UidNotValidException();
        if (event == null) throw new EventNotFoundException();
        kdaEventPartInDto.setEvent(event);
        kdaEventPartInDto.setUser(user);

        kdaEventPartInService.save(kdaEventPartInDto);
    }

}
