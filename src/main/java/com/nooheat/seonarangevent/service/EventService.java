package com.nooheat.seonarangevent.service;

import com.nooheat.seonarangevent.domain.event.Event;
import com.nooheat.seonarangevent.domain.event.EventRepository;
import com.nooheat.seonarangevent.dto.event.EventSaveRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class EventService {
    private EventRepository eventRepository;

    @Transactional
    public Long save(EventSaveRequestDto eventSaveRequestDto) {
        return eventRepository.save(eventSaveRequestDto.toEntity()).getId();
    }
}
