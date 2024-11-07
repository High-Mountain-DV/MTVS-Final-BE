package com.khj.mtvsfinalbe.combat.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_combat")
public class Combat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String created_at;
    private String damage_dealt;
    private int assists;
    private int play_time;
    private int score;
    private int accuracy;
    private LocalDateTime last_updated;
    private String nickname;
    private boolean awareness;
    private int ally_injuries;
    private int ally_deaths;
    private int kills;


    private Long memberId;

    @Builder
    public Combat(String created_at, String damage_dealt, int assists, int play_time, int score, int accuracy, LocalDateTime last_updated, String nickname, boolean awareness, int ally_injuries, int ally_deaths, int kills, Long memberId) {
        this.created_at = created_at;
        this.damage_dealt = damage_dealt;
        this.assists = assists;
        this.play_time = play_time;
        this.score = score;
        this.accuracy = accuracy;
        this.last_updated = last_updated;
        this.nickname = nickname;
        this.awareness = awareness;
        this.ally_injuries = ally_injuries;
        this.ally_deaths = ally_deaths;
        this.kills = kills;
        this.memberId = memberId;
    }
}
