package com.khj.mtvsfinalbe.profile.domain.dto;

import lombok.Builder;
import lombok.Data;

/**
 * AI 서버로 전달되는 데이터 구조를 나타내는 Wrapper DTO
 */
@Data
@Builder
public class AIRequestWrapperDTO {
    private CombatData previousData; // 내부 클래스 이름 변경
    private CombatData currentData;  // 내부 클래스 이름 변경

    @Data
    @Builder
    public static class CombatData {
        private int assists;
        private int kills;
        private double accuracy;
        private double awareness;
        private int playTime;
    }
}
