package com.khj.mtvsfinalbe.combat.domain;

import com.khj.mtvsfinalbe.user.domain.User;
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

    private LocalDateTime created_at;   // 전투 세션이 생성된 날짜 및 시간
    private double damage_dealt;        // 전투에서 적에게 가한 총 피해량
    private int assists;                // 어시스트 횟수
    private int play_time;              // 전투에 참여한 총 시간 (초 단위로 저장 가능)
    private int score;                  // 전투 결과에 따른 종합 점수
    private double accuracy;            // 명중률
    private LocalDateTime last_updated; // 마지막으로 데이터가 업데이트된 시간
    private String nickname;            // 사용자 닉네임
    private double awareness;           // 상황 인지도
    private int ally_injuries;          // 전투 중 아군 부상자 수
    private int ally_deaths;            // 전투 중 아군 사망자 수
    private int kills;                  // 적군을 사살한 횟수

    @Column(name = "member_id")
    private Long memberId; // User ID

    @Builder
    public Combat(LocalDateTime created_at, double damage_dealt, int assists, int play_time, int score, double accuracy,
                  LocalDateTime last_updated, String nickname, double awareness, int ally_injuries, int ally_deaths,
                  int kills, Long memberId) {
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
