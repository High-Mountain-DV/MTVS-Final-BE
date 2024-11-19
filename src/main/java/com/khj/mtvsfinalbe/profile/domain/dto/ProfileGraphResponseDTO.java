package com.khj.mtvsfinalbe.profile.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

/**
 * 그래프 데이터를 반환하기 위한 응답 DTO
 */
@Getter
@Builder
public class ProfileGraphResponseDTO {
    private LocalDate date; // 날짜
    private int assists;    // 어시스트 수
    private int kills;      // 킬 수
    private double accuracy; // 정확도
    private double awareness; // 상황인지
    private int playTime;   // 플레이 시간
    private String aggregatedFeedback; // AI 누적 피드백
}
