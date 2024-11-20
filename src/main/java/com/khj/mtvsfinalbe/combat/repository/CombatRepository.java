package com.khj.mtvsfinalbe.combat.repository;

import com.khj.mtvsfinalbe.combat.domain.Combat;
import com.khj.mtvsfinalbe.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CombatRepository extends JpaRepository<Combat, Long> {
    Combat findTopByUserOrderByCreatedAtDesc(User user);
    List<Combat> findByUser(User user, Sort sort);

    // 이전 Combat 데이터를 조회하기 위한 메서드 추가
    Combat findFirstByUserAndCreatedAtBeforeOrderByCreatedAtDesc(User user, LocalDateTime createdAt);
}
