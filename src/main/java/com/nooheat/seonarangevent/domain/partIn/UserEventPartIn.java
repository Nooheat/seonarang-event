package com.nooheat.seonarangevent.domain.partIn;

import com.nooheat.seonarangevent.domain.event.Event;
import com.nooheat.seonarangevent.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(UserEventPartInId.class)
public class UserEventPartIn {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Builder
    public UserEventPartIn(User user, Event event) {
        this.user = user;
        this.event = event;
    }
}
