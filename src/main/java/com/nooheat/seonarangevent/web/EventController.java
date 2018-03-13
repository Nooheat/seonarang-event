package com.nooheat.seonarangevent.web;

import com.google.gson.JsonObject;
import com.nooheat.seonarangevent.domain.event.Event;
import com.nooheat.seonarangevent.domain.user.User;
import com.nooheat.seonarangevent.domain.user.UserRepository;
import com.nooheat.seonarangevent.dto.event.EventSaveRequestDto;
import com.nooheat.seonarangevent.exception.UnexpectedSortException;
import com.nooheat.seonarangevent.service.EventService;
import com.nooheat.seonarangevent.support.JwtManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Stream;

@RestController
@AllArgsConstructor
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/event")
    public Long saveEvent(@CookieValue(value = "twitch-event-access-token", defaultValue = "null") String accessToken, @RequestBody EventSaveRequestDto dto) throws Exception {

        Jws<Claims> claims = JwtManager.parse(accessToken);
        String uid = claims.getBody().get("uid", String.class);
        dto.setUid(uid);

        return eventService.save(dto);
    }

    @GetMapping("/me/event")
    public Collection<Event> getMyEvents(@CookieValue(value = "twitch-event-access-token", defaultValue = "null") String accessToken) throws Exception {
        String uid = JwtManager.parse(accessToken).getBody().get("uid", String.class);
        return userRepository.findByUid(uid).getEvents();
    }

    @GetMapping("/event/all")
    public Collection<Event> getEvents(@RequestParam(name = "sort", defaultValue = "desc") String sort) throws UnexpectedSortException {
        switch (sort.toLowerCase()) {
            case "desc":
                return eventService.findAllDesc();
            case "asc":
                return eventService.findAllAsc();
            default:
                throw new UnexpectedSortException(sort);
        }
    }

    @GetMapping("/event/open")
    public Collection<Event> getOpenEvents(@RequestParam(name = "sort", defaultValue = "desc") String sort) throws UnexpectedSortException {
        switch (sort.toLowerCase()) {
            case "desc":
                return eventService.findOpenEventsDesc();
            case "asc":
                return eventService.findOpenEventsAsc();
            default:
                throw new UnexpectedSortException(sort);
        }
    }

}
