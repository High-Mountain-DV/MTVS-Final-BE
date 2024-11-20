package com.khj.mtvsfinalbe.profile.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * AI 서버로 전송되는 요청 데이터
 */
@Getter
@Setter
@Builder
public class AIRequestDTO {
    private Long userId; // 사용자 ID
    private int assists; // 어시스트
    private int kills;   // 킬
    private double accuracy; // 정확도
    private double awareness; // 상황인지
    private int playTime; // 플레이 시간

    // assists에 기본값 추가 (예: 0)
    public static class AIRequestDTOBuilder {
        private int assists = 0; // 기본값
    }
}
