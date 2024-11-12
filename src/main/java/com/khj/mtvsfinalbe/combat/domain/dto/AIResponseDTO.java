package com.khj.mtvsfinalbe.combat.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AIResponseDTO {
    @JsonProperty("radar_chart")
    private String radarChart;  // JSON의 "radar_chart" 필드를 매핑

    private String feedback;    // JSON의 "feedback" 필드를 매핑
}
