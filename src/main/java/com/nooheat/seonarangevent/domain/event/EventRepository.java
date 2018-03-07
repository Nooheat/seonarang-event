package com.nooheat.seonarangevent.domain.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e "+
            "FROM Event e "+
            "ORDER BY e.id DESC")
    Stream<Event> findAllDesc();

}
