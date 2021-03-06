package com.nooheat.seonarangevent.web;

import com.nooheat.seonarangevent.domain.event.Event;
import com.nooheat.seonarangevent.dto.event.EventSaveRequestDto;
import com.nooheat.seonarangevent.service.EventService;
import com.nooheat.seonarangevent.support.JwtManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping("/event")
    public Long saveEvent(@CookieValue(value = "twitch-event-access-token", defaultValue = "null") String accessTokenStr, @RequestBody EventSaveRequestDto dto) {

        Jws<Claims> claims = JwtManager.parse(accessTokenStr);
        String uid = claims.getBody().get("userId", String.class);
        dto.setUserId(uid);

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
