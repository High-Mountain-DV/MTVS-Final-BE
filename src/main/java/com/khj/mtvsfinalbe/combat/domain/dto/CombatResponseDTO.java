package com.khj.mtvsfinalbe.combat.domain.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class CombatResponseDTO {
    private Long id;
    private LocalDateTime createdAt;
    private double damageDealt;
    private int assists;
    private int playTime;
    private int score;
    private double accuracy;
    private LocalDateTime lastUpdated;
    private String nickname;
    private double awareness;
    private int allyInjuries;
    private int allyDeaths;
    private int kills;
    private String imageUrl;
    private String analysisResult;
}
