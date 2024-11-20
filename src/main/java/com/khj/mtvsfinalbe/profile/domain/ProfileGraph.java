package com.khj.mtvsfinalbe.profile.domain;

import com.khj.mtvsfinalbe.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_profile_graph", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "date"}))
public class ProfileGraph {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate date;

    private int assists;
    private int kills;
    private double accuracy;
    private double awareness;
    private int playTime;

    @Column(columnDefinition = "TEXT")
    private String aggregatedFeedback;

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
