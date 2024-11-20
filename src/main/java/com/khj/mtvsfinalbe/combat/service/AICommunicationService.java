package com.khj.mtvsfinalbe.combat.service;

import com.khj.mtvsfinalbe.combat.domain.dto.AIRequestDTO;
import com.khj.mtvsfinalbe.combat.domain.dto.AIResponseDTO;
import com.khj.mtvsfinalbe.profile.domain.dto.ProfileGraphRequestDTO;
import com.khj.mtvsfinalbe.profile.domain.dto.AIRequestWrapperDTO;
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
     * AI 서버로부터 누적 피드백 데이터를 가져오는 메서드 (기존 방식)
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

    /**
     * AI 서버로부터 누적 피드백 데이터를 가져오는 메서드 (추가된 부분: 새로운 방식)
     *
     * @param requestWrapperDTO AI 요청 데이터 (현재 데이터와 이전 데이터를 포함)
     * @return AI로부터 받은 누적 피드백
     */
    public String getAggregatedFeedback(AIRequestWrapperDTO requestWrapperDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json"); // 요청의 Content-Type 설정

        HttpEntity<AIRequestWrapperDTO> requestEntity = new HttpEntity<>(requestWrapperDTO, headers);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                aggregatedFeedbackUrl, HttpMethod.POST, requestEntity, Map.class);

        // 응답 데이터 검증 및 기본값 처리
        Map<String, Object> feedback = validateAndProcessFeedback(responseEntity.getBody());

        return feedback.toString(); // 반환 데이터 직렬화
    }

    /**
     * 피드백 데이터 유효성 검증 및 기본값 처리
     *
     * @param feedback 응답 데이터 맵
     * @return 유효성이 검증된 데이터
     */
    private Map<String, Object> validateAndProcessFeedback(Map<String, Object> feedback) {
        if (feedback == null) {
            feedback = new HashMap<>();
        }

        // 필수 필드 및 기본값 정의
        String[] requiredFields = {"assists", "kills", "accuracy", "awareness", "playTime"};
        for (String field : requiredFields) {
            Object value = feedback.get(field);
            if (value == null) {
                feedback.put(field, field.equals("accuracy") || field.equals("awareness") ? 0.0 : 0);
            } else {
                if (field.equals("accuracy") || field.equals("awareness")) {
                    feedback.put(field, convertToDouble(value));
                } else {
                    feedback.put(field, convertToInteger(value));
                }
            }
        }

        return feedback;
    }

    /**
     * 숫자 값을 Double로 변환
     *
     * @param value 변환할 값
     * @return 변환된 Double 값
     */
    private double convertToDouble(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else if (value instanceof String) {
            try {
                return Double.parseDouble((String) value);
            } catch (NumberFormatException e) {
                return 0.0; // 잘못된 값의 경우 기본값 반환
            }
        }
        return 0.0; // 예상치 못한 데이터 타입 처리
    }

    /**
     * 숫자 값을 Integer로 변환
     *
     * @param value 변환할 값
     * @return 변환된 Integer 값
     */
    private int convertToInteger(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return 0; // 잘못된 값의 경우 기본값 반환
            }
        }
        return 0; // 예상치 못한 데이터 타입 처리
    }
}
