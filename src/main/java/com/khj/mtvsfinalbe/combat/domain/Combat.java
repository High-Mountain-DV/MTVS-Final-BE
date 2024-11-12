package com.khj.mtvsfinalbe.combat.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.khj.mtvsfinalbe.user.domain.User;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter // 모든 필드에 대한 setter 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_combat")
public class Combat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    private double damageDealt;
    private int assists;
    private int playTime;
    private int score;
    private double accuracy;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    private double awareness;
    private int allyInjuries;
    private int allyDeaths;
    private int kills;

    private String imageUrl;  // S3 URL for AI result image
    private String analysisResult;  // AI analysis result

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Combat(LocalDateTime createdAt, double damageDealt, int assists, int playTime, int score, double accuracy,
                  LocalDateTime lastUpdated, double awareness, int allyInjuries, int allyDeaths,
                  int kills, User user, String imageUrl, String analysisResult) {
        this.createdAt = createdAt;
        this.damageDealt = damageDealt;
        this.assists = assists;
        this.playTime = playTime;
        this.score = score;
        this.accuracy = accuracy;
        this.lastUpdated = lastUpdated;
        this.awareness = awareness;
        this.allyInjuries = allyInjuries;
        this.allyDeaths = allyDeaths;
        this.kills = kills;
        this.user = user;
        this.imageUrl = imageUrl;
        this.analysisResult = analysisResult;
    }
}
