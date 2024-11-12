package com.khj.mtvsfinalbe;

import com.khj.mtvsfinalbe.combat.service.AICommunicationService;
import com.khj.mtvsfinalbe.combat.service.S3Service;
import com.khj.mtvsfinalbe.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

@SpringBootTest
public class MtvsFinalBeApplicationTests {

    @MockBean
    private AICommunicationService aiCommunicationService;

    @MockBean
    private S3Service s3Service;

    @MockBean
    private UserRepository userRepository;

    @Test
    void contextLoads() {
        // 실제 테스트 코드 작성, 현재는 context load 여부만 확인합니다.
    }

    @Configuration
    static class TestConfig {
        // 만약에 테스트 용도의 특정 Bean이 필요하면 여기에 등록
        // 예) Test DB 설정, 또는 Mock 서비스 빈 등
    }
}
