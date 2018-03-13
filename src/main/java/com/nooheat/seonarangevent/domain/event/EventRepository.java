package com.nooheat.seonarangevent.domain.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e " +
            "FROM Event e " +
            "ORDER BY e.createdDate DESC")
    List<Event> findAllDesc();

    @Query("SELECT e " +
            "FROM Event e " +
            "ORDER BY e.createdDate ASC")
    List<Event> findAllAsc();


    @Query("SELECT e " +
            "FROM Event e " +
            "WHERE e.closeDate IS NULL OR e.closeDate > current_date " +
            "ORDER BY e.createdDate DESC")
    List<Event> findOpenEventsDesc();

    @Query("SELECT e " +
            "FROM Event e " +
            "WHERE e.closeDate IS NULL OR e.closeDate > current_date " +
            "ORDER BY e.createdDate ASC")
    List<Event> findOpenEventsAsc();
}
