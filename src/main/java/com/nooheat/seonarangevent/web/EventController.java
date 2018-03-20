package com.nooheat.seonarangevent.web;

import com.google.gson.JsonObject;
import com.nooheat.seonarangevent.domain.event.Event;
import com.nooheat.seonarangevent.domain.user.User;
import com.nooheat.seonarangevent.domain.user.UserRepository;
import com.nooheat.seonarangevent.dto.event.EventSaveRequestDto;
import com.nooheat.seonarangevent.service.EventService;
import com.nooheat.seonarangevent.support.JwtManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Stream;

@RestController
@AllArgsConstructor
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/event")
    public Long saveEvent(@CookieValue(value = "twitch-event-access-token", defaultValue = "null") String accessToken, @RequestBody EventSaveRequestDto dto) throws Exception {

        Jws<Claims> claims = JwtManager.parse(accessToken);
        String uid = claims.getBody().get("uid", String.class);
        dto.setUid(uid);

        return eventService.save(dto);
    }


    @GetMapping("/events/all")
    public Collection<Event> getEvents(Pageable pageable) {
        return eventService.findAll(pageable);
    }

    @GetMapping("/events/open")
    public Collection<Event> getOpenEvents(Pageable pageable) {
        return eventService.findOpenEvents(pageable);
    }

    @GetMapping("/events/closed")
    public Collection<Event> getLockEvents(Pageable pageable) {
        return eventService.findClosedEvents(pageable);
    }


}
