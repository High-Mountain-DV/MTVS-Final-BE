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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 전투 기록의 고유 ID

    @Column(name = "created_at")
    private LocalDateTime createdAt;   // 전투 세션 생성 시간

    private double damageDealt;        // 총 피해량
    private int assists;               // 어시스트 횟수
    private int playTime;              // 총 전투 시간
    private int score;                 // 전투 점수
    private double accuracy;           // 명중률

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated; // 마지막 데이터 업데이트 시간

    private String nickname;           // 사용자 닉네임
    private double awareness;          // 상황 인지도
    private int allyInjuries;          // 아군 부상자 수
    private int allyDeaths;            // 아군 사망자 수
    private int kills;                 // 적군 사살 횟수

    @Column(name = "member_id")
    private Long memberId; // 사용자 ID

    @Builder
    public Combat(LocalDateTime createdAt, double damageDealt, int assists, int playTime, int score, double accuracy,
                  LocalDateTime lastUpdated, String nickname, double awareness, int allyInjuries, int allyDeaths,
                  int kills, Long memberId) {
        this.createdAt = createdAt;
        this.damageDealt = damageDealt;
        this.assists = assists;
        this.playTime = playTime;
        this.score = score;
        this.accuracy = accuracy;
        this.lastUpdated = lastUpdated;
        this.nickname = nickname;
        this.awareness = awareness;
        this.allyInjuries = allyInjuries;
        this.allyDeaths = allyDeaths;
        this.kills = kills;
        this.memberId = memberId;
    }
}
