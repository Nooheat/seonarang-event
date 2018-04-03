package com.nooheat.seonarangevent.domain.partIn.kda;

import com.nooheat.seonarangevent.domain.BaseTimeEntity;
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
@Table(name = "user_kda_event_part_in")
public class KdaEventPartIn extends BaseTimeEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    private Integer k;
    private Integer d;
    private Integer a;

    @Builder
    public KdaEventPartIn(User user, Event event, Integer k, Integer d, Integer a) {
        this.user = user;
        this.event = event;
        this.k = k;
        this.d = d;
        this.a = a;
    }
}
