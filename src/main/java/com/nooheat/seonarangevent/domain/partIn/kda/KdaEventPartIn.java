package com.nooheat.seonarangevent.domain.partIn.kda;

import com.nooheat.seonarangevent.domain.event.Event;
import com.nooheat.seonarangevent.domain.partIn.EventPartInId;
import com.nooheat.seonarangevent.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(EventPartInId.class)
public class KdaEventPartIn {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Builder
    public KdaEventPartIn(User user, Event event) {
        this.user = user;
        this.event = event;
    }
}
