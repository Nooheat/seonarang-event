package com.nooheat.seonarangevent.service;

import com.nooheat.seonarangevent.domain.event.Event;
import com.nooheat.seonarangevent.domain.event.EventRepository;
import com.nooheat.seonarangevent.dto.event.EventSaveRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class EventService {
    private EventRepository eventRepository;

    @Transactional
    public Event findByEventId(Long eventId) {
        return eventRepository.findOne(eventId);
    }

    @Transactional
    public Long save(EventSaveRequestDto eventSaveRequestDto) {
        return eventRepository.save(eventSaveRequestDto.toEntity()).getEventId();
    }

    @Transactional
    public List<Event> findOpenEvents(Pageable pageable) {
        return eventRepository.findOpenEvents(pageable).getContent();
    }

    @Transactional
    public Collection<Event> findClosedEvents(Pageable pageable) {
        return eventRepository.findClosedEvents(pageable).getContent();
    }

    @Transactional
    public Collection<Event> findAll(Pageable pageable) {
        return eventRepository.findAll(pageable).getContent();
    }
}
