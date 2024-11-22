package com.khj.mtvsfinalbe.combat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khj.mtvsfinalbe.combat.domain.dto.AIRequestDTO;
import com.khj.mtvsfinalbe.combat.domain.dto.AIResponseDTO;
import com.khj.mtvsfinalbe.profile.domain.dto.ProfileGraphRequestDTO;
import com.khj.mtvsfinalbe.profile.domain.dto.AIRequestWrapperDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * AI 서버와 통신을 담당하는 서비스 클래스
 */
@Service
@Slf4j
public class AICommunicationService {

    private final RestTemplate restTemplate;
    private final String aiServerUrl;
    private final String aggregatedFeedbackUrl;

    public AICommunicationService(RestTemplate restTemplate,
                                  @Value("${ai.server.url}") String aiServerUrl,
                                  @Value("${ai.aggregated.feedback.url}") String aggregatedFeedbackUrl) {
        this.restTemplate = restTemplate;
        this.aiServerUrl = aiServerUrl;
        this.aggregatedFeedbackUrl = aggregatedFeedbackUrl;
    }

    /**
     * Combat 데이터를 AI 서버로 전송
     *
     * @param aiRequestDTO AI로 전송할 Combat 데이터
     * @return AI의 응답 데이터
     */
    public AIResponseDTO sendDataToAI(AIRequestDTO aiRequestDTO) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            HttpEntity<AIRequestDTO> requestEntity = new HttpEntity<>(aiRequestDTO, headers);

            ResponseEntity<AIResponseDTO> responseEntity = restTemplate.exchange(
                    aiServerUrl, HttpMethod.POST, requestEntity, AIResponseDTO.class);

            return responseEntity.getBody();
        } catch (Exception e) {
            log.error("Failed to send data to AI server", e);
            throw new RuntimeException("AI communication failed", e);
        }
    }

    /**
     * AI 서버로부터 누적 피드백 데이터를 가져오는 메서드 (기존 방식)
     *
     * @param requestDTO 누적 피드백 요청 데이터
     * @return AI로부터 받은 누적 피드백
     */
    public String getAggregatedFeedback(ProfileGraphRequestDTO requestDTO) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            HttpEntity<ProfileGraphRequestDTO> requestEntity = new HttpEntity<>(requestDTO, headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    aggregatedFeedbackUrl, HttpMethod.POST, requestEntity, String.class);

            return responseEntity.getBody();
        } catch (Exception e) {
            log.error("Failed to fetch aggregated feedback (legacy)", e);
            throw new RuntimeException("AI communication failed", e);
        }
    }

    /**
     * AI 서버로부터 누적 피드백 데이터를 가져오는 메서드 (새로운 방식)
     *
     * @param requestWrapperDTO AI 요청 데이터 (현재 데이터와 이전 데이터를 포함)
     * @return AI로부터 받은 누적 피드백
     */
    public String getAggregatedFeedback(AIRequestWrapperDTO requestWrapperDTO) {
        try {
            ensureNonNullCombatData(requestWrapperDTO);

            String requestBody = new ObjectMapper().writeValueAsString(requestWrapperDTO);
            log.info("Sending aggregated feedback request: {}", requestBody);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    aggregatedFeedbackUrl, HttpMethod.POST, requestEntity, String.class);

            String response = responseEntity.getBody();
            log.info("Received aggregated feedback response: {}", response);

            return response;
        } catch (Exception e) {
            log.error("Failed to fetch aggregated feedback (new)", e);
            throw new RuntimeException("AI communication failed", e);
        }
    }

    /**
     * 요청 데이터의 CombatData가 null인 경우 기본값 설정
     *
     * @param requestWrapperDTO 요청 데이터
     */
    private void ensureNonNullCombatData(AIRequestWrapperDTO requestWrapperDTO) {
        if (requestWrapperDTO.getPreviousData() == null) {
            requestWrapperDTO.setPreviousData(
                    AIRequestWrapperDTO.CombatData.builder()
                            .assists(0)
                            .kills(0)
                            .accuracy(0.0)
                            .awareness(0.0)
                            .playTime(0)
                            .build()
            );
        }

        if (requestWrapperDTO.getCurrentData() == null) {
            requestWrapperDTO.setCurrentData(
                    AIRequestWrapperDTO.CombatData.builder()
                            .assists(0)
                            .kills(0)
                            .accuracy(0.0)
                            .awareness(0.0)
                            .playTime(0)
                            .build()
            );
        }
    }
}
