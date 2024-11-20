package com.khj.mtvsfinalbe.profile.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 그래프 데이터를 저장하기 위한 요청 DTO
 */
@Getter
@Setter
@Builder // 빌더 패턴을 생성하기 위한 어노테이션 추가
public class ProfileGraphRequestDTO {
    private Long userId; // 사용자 ID
    private int assists; // 어시스트 수
    private int kills;   // 킬 수
    private double accuracy; // 정확도
    private double awareness; // 상황인지
    private int playTime; // 플레이 시간
}
