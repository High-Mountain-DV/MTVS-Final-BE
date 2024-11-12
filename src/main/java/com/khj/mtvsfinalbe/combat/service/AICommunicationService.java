package com.khj.mtvsfinalbe.combat.service;

import com.khj.mtvsfinalbe.combat.domain.dto.AIRequestDTO;
import com.khj.mtvsfinalbe.combat.domain.dto.AIResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@Service
public class AICommunicationService {

    private final RestTemplate restTemplate;
    private final String aiServerUrl;

    public AICommunicationService(RestTemplate restTemplate,
                                  @Value("${ai.server.url}") String aiServerUrl) {
        this.restTemplate = restTemplate;
        this.aiServerUrl = aiServerUrl;
    }

    public AIResponseDTO sendDataToAI(AIRequestDTO aiRequestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<AIRequestDTO> requestEntity = new HttpEntity<>(aiRequestDTO, headers);

        ResponseEntity<AIResponseDTO> responseEntity = restTemplate.exchange(
                aiServerUrl, HttpMethod.POST, requestEntity, AIResponseDTO.class);

        return responseEntity.getBody();
    }
}
