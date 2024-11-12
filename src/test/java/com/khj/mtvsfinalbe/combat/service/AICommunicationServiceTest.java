package com.khj.mtvsfinalbe.combat.service;

import com.khj.mtvsfinalbe.combat.domain.dto.AIRequestDTO;
import com.khj.mtvsfinalbe.combat.domain.dto.AIResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
public class AICommunicationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AICommunicationService aiCommunicationService;

    @BeforeEach
    void setUp() {
        aiCommunicationService = new AICommunicationService(restTemplate, "http://your-ai-server-url");
    }

    @Test
    public void testSendDataToAI() {
        // 준비 단계
        AIRequestDTO requestDTO = new AIRequestDTO(1L, 100.0, 5, 300, 80, 0.75, 0.8, 1, 0, 3, "UserNickname");
        AIResponseDTO expectedResponse = new AIResponseDTO("base64ImageString", "Analysis result");
        ResponseEntity<AIResponseDTO> responseEntity = new ResponseEntity<>(expectedResponse, HttpStatus.OK);

        // Mock 설정
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(AIResponseDTO.class)))
                .thenReturn(responseEntity);

        // 실행
        AIResponseDTO actualResponse = aiCommunicationService.sendDataToAI(requestDTO);

        // 검증
        assertEquals(expectedResponse.getBase64Image(), actualResponse.getBase64Image());
        assertEquals(expectedResponse.getResult(), actualResponse.getResult());
    }
}
