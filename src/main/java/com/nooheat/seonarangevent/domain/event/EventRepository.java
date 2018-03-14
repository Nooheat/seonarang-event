package com.nooheat.seonarangevent.domain.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e " +
            "FROM Event e " +
            "WHERE e.closed = FALSE ")
    Page<Event> findOpenEvents(Pageable pageable);

    @Query("SELECT e " +
            "FROM Event e " +
            "WHERE  e.closed = TRUE ")
    Page<Event> findClosedEvents(Pageable pageable);

}