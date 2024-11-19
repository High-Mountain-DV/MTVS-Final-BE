package com.khj.mtvsfinalbe.profile.domain;

import com.khj.mtvsfinalbe.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_profile_graph", uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "date"}))
public class ProfileGraph {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false) // CamelCase 컬럼명 설정
    private User user;

    @Column(nullable = false)
    private LocalDate date;

    private int assists; // 어시스트 수
    private int kills;   // 킬 수
    private double accuracy; // 정확도
    private double awareness; // 인지력
    private int playTime; // 플레이 시간

    @Column(columnDefinition = "TEXT", length = 1000)
    private String aggregatedFeedback; // AI에서 받은 누적 피드백

    @Builder
    public ProfileGraph(User user, LocalDate date, int assists, int kills, double accuracy, double awareness, int playTime, String aggregatedFeedback) {
        this.user = user;
        this.date = date;
        this.assists = assists;
        this.kills = kills;
        this.accuracy = accuracy;
        this.awareness = awareness;
        this.playTime = playTime;
        this.aggregatedFeedback = aggregatedFeedback;
    }
}