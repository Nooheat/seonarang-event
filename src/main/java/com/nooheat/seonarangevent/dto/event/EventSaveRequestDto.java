package com.nooheat.seonarangevent.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nooheat.seonarangevent.domain.LocalDateTimeDeserializer;
import com.nooheat.seonarangevent.domain.event.Event;
import com.nooheat.seonarangevent.domain.event.EventType;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.util.converter.LocalDateTimeStringConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class EventSaveRequestDto {

    private String uid;
    private EventType type;
    private String title;
    private String content;
    private Integer maximum;


    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime closeDate;
    private Boolean allowDuplication;

    public Event toEntity() {
        return Event.builder()
                .uid(uid)
                .type(type)
                .title(title)
                .content(content)
                .maximum(maximum)
                .allowDuplication(allowDuplication)
                .closeDate(closeDate)
                .build();
    }

    @Builder
    public EventSaveRequestDto(String uid, EventType type, String title, String content, Integer maximum, LocalDateTime closeDate, Boolean allowDuplication) {
        this.uid = uid;
        this.type = type;
        this.title = title;
        this.content = content;
        this.maximum = maximum;
        this.closeDate = closeDate;
        this.allowDuplication = allowDuplication;
    }
}
