package com.khj.mtvsfinalbe.combat.service;

import com.khj.mtvsfinalbe.combat.domain.dto.AIRequestDTO;
import com.khj.mtvsfinalbe.combat.domain.dto.AIResponseDTO;
import com.khj.mtvsfinalbe.profile.domain.dto.ProfileGraphRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * AI 서버와 통신을 담당하는 서비스 클래스
 */
@Service
public class AICommunicationService {

    private final RestTemplate restTemplate;
    private final String aiServerUrl; // Combat 데이터를 처리하는 AI 서버 URL
    private final String aggregatedFeedbackUrl; // 누적 피드백을 처리하는 AI 서버 URL

    public AICommunicationService(RestTemplate restTemplate,
                                  @Value("${ai.server.url}") String aiServerUrl,
                                  @Value("${ai.aggregated.feedback.url}") String aggregatedFeedbackUrl) {
        this.restTemplate = restTemplate;
        this.aiServerUrl = aiServerUrl;
        this.aggregatedFeedbackUrl = aggregatedFeedbackUrl;
    }

    /**
     * Combat 데이터를 AI 서버로 전송하는 메서드
     *
     * @param aiRequestDTO AI로 전송할 Combat 데이터
     * @return AI의 응답 데이터
     */
    public AIResponseDTO sendDataToAI(AIRequestDTO aiRequestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json"); // 요청의 Content-Type 설정

        HttpEntity<AIRequestDTO> requestEntity = new HttpEntity<>(aiRequestDTO, headers);

        ResponseEntity<AIResponseDTO> responseEntity = restTemplate.exchange(
                aiServerUrl, HttpMethod.POST, requestEntity, AIResponseDTO.class);

        return responseEntity.getBody(); // AI 응답 반환
    }

    /**
     * AI 서버로부터 누적 피드백 데이터를 가져오는 메서드
     *
     * @param requestDTO 누적 피드백 요청 데이터
     * @return AI로부터 받은 누적 피드백
     */
    public String getAggregatedFeedback(ProfileGraphRequestDTO requestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json"); // 요청의 Content-Type 설정

        HttpEntity<ProfileGraphRequestDTO> requestEntity = new HttpEntity<>(requestDTO, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                aggregatedFeedbackUrl, HttpMethod.POST, requestEntity, String.class);

        return responseEntity.getBody(); // 누적 피드백 반환
    }
}