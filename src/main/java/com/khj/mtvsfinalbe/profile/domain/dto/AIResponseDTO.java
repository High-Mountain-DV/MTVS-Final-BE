package com.khj.mtvsfinalbe.profile.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AI에서 반환된 누적 피드백 데이터를 받는 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIResponseDTO {
    private String aggregatedFeedback; // AI가 반환한 누적 피드백
}
