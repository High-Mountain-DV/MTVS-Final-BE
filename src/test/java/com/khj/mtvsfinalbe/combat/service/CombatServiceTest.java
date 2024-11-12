package com.khj.mtvsfinalbe.combat.service;

import com.khj.mtvsfinalbe.combat.domain.Combat;
import com.khj.mtvsfinalbe.combat.domain.dto.*;
import com.khj.mtvsfinalbe.combat.repository.CombatRepository;
import com.khj.mtvsfinalbe.user.domain.User;
import com.khj.mtvsfinalbe.user.repository.UserRepository;
import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CombatServiceTest {

    @Mock
    private CombatRepository combatRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AICommunicationService aiCommunicationService;

    @Mock
    private AmazonS3 amazonS3; // AmazonS3 모의 객체 추가

    @InjectMocks
    private S3Service s3Service; // 모의된 AmazonS3를 주입받는 S3Service

    @InjectMocks
    private CombatService combatService;

    @BeforeEach
    void setUp() {
        // Mockito가 @InjectMocks를 통해 의존성을 주입합니다.
    }

    @Test
    public void testSaveCombat() {
        // 테스트 코드 동일
        Long userId = 1L;
        // ... (생략)
    }
}
