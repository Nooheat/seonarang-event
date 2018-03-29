package com.nooheat.seonarangevent.domain.partIn;

import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class EventPartInId implements Serializable {
    private String user;
    private Long event;

    public EventPartInId(String user, Long event) {
        this.user = user;
        this.event = event;
    }
}
