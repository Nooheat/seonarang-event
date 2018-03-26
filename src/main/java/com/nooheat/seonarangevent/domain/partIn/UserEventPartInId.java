package com.nooheat.seonarangevent.domain.partIn;

import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class UserEventPartInId implements Serializable {
    private String user;
    private Long event;

    public UserEventPartInId(String user, Long event) {
        this.user = user;
        this.event = event;
    }
}
