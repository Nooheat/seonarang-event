package com.nooheat.seonarangevent.domain.user;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @After
    public void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    public void 유저_저장하기() {
        String uid = userRepository.save(User.builder()
                .displayName("Nooheat")
                .twitchId("nooheat1228")
                .email("nooheat1228@gmail.com")
                .build()).getUid();

        assertTrue(uid != null);
    }
}
