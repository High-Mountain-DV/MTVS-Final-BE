package com.khj.mtvsfinalbe.combat.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CombatRequestDTO {

    private double damageDealt;     // 총 피해량
    private int assists;            // 어시스트 횟수
    private int playTime;           // 전투 시간
    private int score;              // 전투 점수
    private double accuracy;        // 명중률
    private double awareness;       // 상황 인지도
    private int allyInjuries;       // 아군 부상자 수
    private int allyDeaths;         // 아군 사망자 수
    private int kills;              // 적군 사살 횟수
}
