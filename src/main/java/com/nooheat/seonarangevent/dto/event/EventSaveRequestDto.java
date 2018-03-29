package com.nooheat.seonarangevent.dto.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nooheat.seonarangevent.domain.LocalDateTimeDeserializer;
import com.nooheat.seonarangevent.domain.event.Event;
import com.nooheat.seonarangevent.domain.event.EventType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EventSaveRequestDto {

    private String userId;
    private EventType type;
    private String title;
    private String content;
    private Integer maximum;


    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime closeDate;
    private Boolean allowDuplication;

    public Event toEntity() {
        return Event.builder()
                .userId(userId)
                .type(type)
                .title(title)
                .content(content)
                .maximum(maximum)
                .allowDuplication(allowDuplication)
                .closeDate(closeDate)
                .build();
    }

    @Builder
    public EventSaveRequestDto(String userId, EventType type, String title, String content, Integer maximum, LocalDateTime closeDate, Boolean allowDuplication) {
        this.userId = userId;
        this.type = type;
        this.title = title;
        this.content = content;
        this.maximum = maximum;
        this.closeDate = closeDate;
        this.allowDuplication = allowDuplication;
    }
}
