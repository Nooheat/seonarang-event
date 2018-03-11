package com.nooheat.seonarangevent.web;

import com.nooheat.seonarangevent.dto.event.EventSaveRequestDto;
import com.nooheat.seonarangevent.service.EventService;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping("/event")
    public Long saveEvent(@RequestHeader(value = "twitch-event-access-token", defaultValue = "null") String accessToken, @RequestBody EventSaveRequestDto dto) {
        System.out.println(accessToken);
        dto.setUid("yunth1228");
        return eventService.save(dto);
    }

}
