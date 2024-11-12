package com.khj.mtvsfinalbe.combat.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CombatRequestDTO {

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
