package com.nooheat.seonarangevent.domain.event;

import com.nooheat.seonarangevent.domain.user.User;
import com.nooheat.seonarangevent.domain.user.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventRepositoryTest {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    private String testAccountUid = null;

    @Before
    public void prepareUserToMakeNewEvent() {
        // 새 이벤트를 만들기 위한 계정을 미리 추가
        testAccountUid = userRepository.save(User.builder()
                .displayName("Nooheat")
                .twitchId("nooheat1228")
                .email("nooheat1228@gmail.com")
                .build()).getUid();
    }

    @After
    public void cleanUp() {
        eventRepository.deleteAll();
    }

    @Test
    public void Repository_이벤트_등록하기() {
        LocalDateTime now = LocalDateTime.now();

        Event event_ = Event.builder()
                .uid(testAccountUid)
                .type(EventType.KDA)
                .title("KDA 한글열매")
                .content("한글한글")
                .maximum(100)
                .allowDuplication(false)
                .closeDate(LocalDateTime.now())
                .build();

        eventRepository.save(event_);

        Event event = eventRepository.findAll().get(0);

        assertTrue(event.getCreatedDate().isAfter(now));
        assertTrue(event.getUid().equals(testAccountUid));
    }
}
