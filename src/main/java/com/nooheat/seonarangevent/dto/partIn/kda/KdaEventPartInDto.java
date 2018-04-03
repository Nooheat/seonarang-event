package com.nooheat.seonarangevent.dto.partIn.kda;

import com.nooheat.seonarangevent.domain.event.Event;
import com.nooheat.seonarangevent.domain.partIn.kda.KdaEventPartIn;
import com.nooheat.seonarangevent.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KdaEventPartInDto {
    private Long eventId;
    private String userId;
    private Integer k;
    private Integer d;
    private Integer a;
    private User user;
    private Event event;

    @Builder
    public KdaEventPartInDto(Long eventId, String userId, Integer k, Integer d, Integer a) {
        this.eventId = eventId;
        this.userId = userId;
        this.k = k;
        this.d = d;
        this.a = a;
    }

    public KdaEventPartIn toEntity() {
        return KdaEventPartIn.builder()
                .event(event)
                .user(user)
                .k(k)
                .d(d)
                .a(a)
                .build();
    }
}
