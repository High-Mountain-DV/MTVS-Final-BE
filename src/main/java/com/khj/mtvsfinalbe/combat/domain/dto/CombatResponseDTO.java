package com.khj.mtvsfinalbe.combat.domain.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class CombatResponseDTO {
    private Long id;
    private LocalDateTime created_at;
    private double damage_dealt;
    private int assists;
    private int play_time;
    private int score;
    private double accuracy;
    private LocalDateTime last_updated;
    private String nickname;
    private double awareness;
    private int ally_injuries;
    private int ally_deaths;
    private int kills;
}
