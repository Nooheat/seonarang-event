package com.nooheat.seonarangevent.domain.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nooheat.seonarangevent.domain.BaseTimeEntity;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.autoconfigure.web.ResourceProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String uid;

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

    @Builder
    public Event(String uid, EventType type, String title, String content, Integer maximum, LocalDateTime closeDate, Boolean allowDuplication) {
        this.uid = uid;
        this.type = type;
        this.title = title;
        this.content = content;
        this.maximum = maximum;
        this.closeDate = closeDate;
        this.allowDuplication = allowDuplication;
        this.current = 0;
    }
}
