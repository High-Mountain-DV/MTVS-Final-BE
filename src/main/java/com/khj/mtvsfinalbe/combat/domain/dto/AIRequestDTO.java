package com.khj.mtvsfinalbe.combat.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AIRequestDTO {
    private int id; // AI 서버가 요구하는 'id' 필드
    private double damageDealt;
    private int assists;
    private int playTime;
    private int score;
    private double accuracy;
    private double awareness;
    private int allyInjuries;
    private int allyDeaths;
    private int kills;
}