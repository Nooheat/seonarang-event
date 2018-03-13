package com.nooheat.seonarangevent.service;

import com.nooheat.seonarangevent.domain.event.Event;
import com.nooheat.seonarangevent.domain.event.EventRepository;
import com.nooheat.seonarangevent.dto.event.EventSaveRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class EventService {
    private EventRepository eventRepository;

    @Transactional
    public Long save(EventSaveRequestDto eventSaveRequestDto) {
        return eventRepository.save(eventSaveRequestDto.toEntity()).getId();
    }

    @Transactional
    public List<Event> findAllDesc() {
        return eventRepository.findAllDesc();
    }

    @Transactional
    public List<Event> findAllAsc() {
        return eventRepository.findAllAsc();
    }

    @Transactional
    public List<Event> findOpenEventsDesc() {
        return eventRepository.findOpenEventsDesc();
    }

    @Transactional
    public List<Event> findOpenEventsAsc() {
        return eventRepository.findOpenEventsAsc();
    }
}
