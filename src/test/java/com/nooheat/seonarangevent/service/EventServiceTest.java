package com.nooheat.seonarangevent.service;

import com.nooheat.seonarangevent.domain.event.EventRepository;
import com.nooheat.seonarangevent.domain.event.EventType;
import com.nooheat.seonarangevent.domain.user.User;
import com.nooheat.seonarangevent.domain.user.UserRepository;
import com.nooheat.seonarangevent.dto.event.EventSaveRequestDto;
import org.apache.tomcat.jni.Local;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventServiceTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    UserRepository userRepository;

    private String testAccountUid = null;

    @Before
    public void prepareTempAccountToMakeNewEvent() {
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
    public void Service_이벤트_등록하기() {

        //TODO Date Formatting
        // 2018-03-02T01:50:54.798

        Long id = eventService.save(EventSaveRequestDto.builder()
                .uid(testAccountUid)
                .type(EventType.KDA)
                .title("Test-Title")
                .content("Test-Content")
                .maximum(100)
                .closeDate(LocalDateTime.parse("2018-03-02T04:50:11.099Z", DateTimeFormatter.ISO_DATE_TIME).plusHours(9))
                .allowDuplication(true)
                .build());


        assertTrue(id != null);
    }

    @Test
    public void Service_이벤트_등록하기_maximum_null() {

        //TODO Date Formatting
        // 2018-03-02T01:50:54.798

        Long id = eventService.save(EventSaveRequestDto.builder()
                .uid(testAccountUid)
                .type(EventType.KDA)
                .title("Test-Title")
                .content("Test-Content")
                .maximum(null)
                .closeDate(LocalDateTime.parse("2018-03-02T04:50:11.099Z", DateTimeFormatter.ISO_DATE_TIME).plusHours(9))
                .allowDuplication(true)
                .build());


        assertTrue(id != null);
    }

    @Test
    public void Service_이벤트_등록하기_closeDate_null() {

        //TODO Date Formatting
        // 2018-03-02T01:50:54.798

        Long id = eventService.save(EventSaveRequestDto.builder()
                .uid(testAccountUid)
                .type(EventType.KDA)
                .title("Test-Title")
                .content("Test-Content")
                .maximum(null)
                .closeDate(null)
                .allowDuplication(true)
                .build());


        assertTrue("closeDate value can be null", id != null);
    }

    @Test
    public void Service_이벤트_등록하기_allowDuplication_should_not_be_null() {

        DataIntegrityViolationException exception = null;

        try {
            Long id = eventService.save(EventSaveRequestDto.builder()
                    .uid(testAccountUid)
                    .type(EventType.KDA)
                    .title("Test-Title")
                    .content("Test-Content")
                    .maximum(null)
                    .closeDate(null)
                    .allowDuplication(null)
                    .build());
        } catch (DataIntegrityViolationException e) {
            exception = e;
        }

        assertTrue("allowDuplication value should not be null", exception != null);
    }

    @Test
    public void Service_이벤트_등록하기_twitchId_should_not_be_null() {
        DataIntegrityViolationException exception = null;

        try {
            Long id = eventService.save(EventSaveRequestDto.builder()
                    .uid(null)
                    .type(EventType.KDA)
                    .title("Test-Title")
                    .content("Test-Content")
                    .maximum(null)
                    .closeDate(null)
                    .allowDuplication(null)
                    .build());
        } catch (DataIntegrityViolationException e) {
            exception = e;
        }

        assertTrue("twitchId value should not be null", exception != null);
    }

    @Test
    public void Service_이벤트_등록하기_title_should_not_be_null() {
        DataIntegrityViolationException exception = null;

        try {
            Long id = eventService.save(EventSaveRequestDto.builder()
                    .uid(testAccountUid)
                    .type(EventType.KDA)
                    .title(null)
                    .content("Test-Content")
                    .maximum(null)
                    .closeDate(null)
                    .allowDuplication(null)
                    .build());
        } catch (DataIntegrityViolationException e) {
            exception = e;
        }

        assertTrue("title value should not be null", exception != null);
    }

    @Test
    public void Service_이벤트_등록하기_content_should_not_be_null() {
        DataIntegrityViolationException exception = null;

        try {
            Long id = eventService.save(EventSaveRequestDto.builder()
                    .uid(testAccountUid)
                    .type(EventType.KDA)
                    .title(null)
                    .content(null)
                    .maximum(null)
                    .closeDate(null)
                    .allowDuplication(null)
                    .build());
        } catch (DataIntegrityViolationException e) {
            exception = e;
        }

        assertTrue("content value should not be null", exception != null);
    }


}
