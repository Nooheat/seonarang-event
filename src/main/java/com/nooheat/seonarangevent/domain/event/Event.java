package com.nooheat.seonarangevent.domain.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nooheat.seonarangevent.domain.BaseTimeEntity;
import com.nooheat.seonarangevent.domain.partIn.kda.KdaEventPartIn;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long eventId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType type;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private Integer maximum;

    @Column(columnDefinition = "DATETIME")
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime closeDate;

    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE")
    private Boolean allowDuplication;

    @Column(columnDefinition = "INT NOT NULL DEFAULT 0")
    private Integer current;

    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE")
    private Boolean closed;

    @OneToMany(mappedBy = "event")
    private Collection<KdaEventPartIn> kdaEventPartIns;

    @Builder
    public Event(String userId, EventType type, String title, String content, Integer maximum, LocalDateTime closeDate, Boolean allowDuplication) {
        this.userId = userId;
        this.type = type;
        this.title = title;
        this.content = content;
        this.maximum = maximum;
        this.closeDate = closeDate;
        this.allowDuplication = allowDuplication;
        this.current = 0;
        this.closed = false;
    }
}
