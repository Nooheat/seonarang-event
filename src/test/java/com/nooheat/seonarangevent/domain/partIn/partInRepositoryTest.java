package com.nooheat.seonarangevent.domain.partIn;

import com.nooheat.seonarangevent.domain.event.Event;
import com.nooheat.seonarangevent.domain.event.EventRepository;
import com.nooheat.seonarangevent.domain.event.EventType;
import com.nooheat.seonarangevent.domain.partIn.kda.KdaEventPartIn;
import com.nooheat.seonarangevent.domain.partIn.kda.KdaEventPartInRepository;
import com.nooheat.seonarangevent.domain.user.User;
import com.nooheat.seonarangevent.domain.user.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class partInRepositoryTest {
    @Autowired
    private KdaEventPartInRepository kdaEventPartInRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Before
    public void setUp() {
        User user = userRepository.save(User.builder()
                .displayName("terry yun")
                .email("yunth1228@naver.com")
                .twitchId("yunth1228").build());

        Event event = eventRepository.save(Event.builder().uid(user.getUserId()).allowDuplication(false).content("asd").title("asdasd").type(EventType.KDA).build());

    }

    @Test
    @Transactional
    public void saveEventPartIn() {
        User user = userRepository.findByEmail("yunth1228@naver.com");

        System.out.println(user.getUserId());
        Event event = eventRepository.findAll().get(0);

        System.out.println(event.getEventId());
        // TODO: 아래로 뺴기.
        KdaEventPartIn partIn = kdaEventPartInRepository.save(KdaEventPartIn.builder().event(event).user(user).build());
        assertTrue(event != null);
        assertTrue(user != null);
        assertTrue(partIn != null);
    }

    @After
    public void clean() {
        System.out.println("CLEAN");
        userRepository.deleteAll();
        eventRepository.deleteAll();
        kdaEventPartInRepository.deleteAll();
    }
}
