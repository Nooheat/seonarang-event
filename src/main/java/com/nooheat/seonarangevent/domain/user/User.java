package com.nooheat.seonarangevent.domain.user;


import com.nooheat.seonarangevent.domain.BaseTimeEntity;
import com.nooheat.seonarangevent.domain.event.Event;
import com.nooheat.seonarangevent.domain.partIn.UserEventPartIn;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String uid;

    @Column(nullable = false, unique = true)
    private String twitchId;

    @Column(nullable = false, unique = true)
    private String displayName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT 0")
    private Boolean permission;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "uid")
    private Collection<Event> events;

    @OneToMany(mappedBy = "user")
    private Collection<UserEventPartIn> partIns;

    @Builder
    public User(String twitchId, String displayName, String email) {
        this.twitchId = twitchId;
        this.displayName = displayName;
        this.email = email;
        this.permission = false;
    }
}


//TODO: Interceptor, JwtVerifier 클래스 수정
