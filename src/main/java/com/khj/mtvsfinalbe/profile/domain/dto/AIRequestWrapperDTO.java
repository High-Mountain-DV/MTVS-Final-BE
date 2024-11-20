package com.khj.mtvsfinalbe.profile.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AIRequestWrapperDTO {
    @JsonProperty("previous_data")
    private CombatData previousData;

    @JsonProperty("current_data")
    private CombatData currentData;

    @Data
    @Builder
    public static class CombatData {  // static 키워드 추가
        @JsonProperty("assists")
        private int assists = 0; // 기본값 추가
        @JsonProperty("kills")
        private int kills;
        @JsonProperty("accuracy")
        private double accuracy;
        @JsonProperty("awareness")
        private double awareness;
        @JsonProperty("playTime")
        private int playTime;  // AI 서버 요구 사항에 맞게 수정

        // CombatData 빌더에 기본값 설정
        public static class CombatDataBuilder {
            private int assists = 0; // 기본값
        }
    }
}
