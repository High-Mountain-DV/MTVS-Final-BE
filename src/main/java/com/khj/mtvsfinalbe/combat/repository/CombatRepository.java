package com.khj.mtvsfinalbe.combat.repository;

import com.khj.mtvsfinalbe.combat.domain.Combat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CombatRepository extends JpaRepository<Combat, Long> {

    // memberId로 최신 전투 데이터를 가져오는 메서드
    Combat findTopByMemberIdOrderByCreatedAtDesc(Long memberId);

    // memberId로 모든 전투 데이터를 최신순으로 가져오는 메서드
    List<Combat> findByMemberId(Long memberId, Sort sort);
}
