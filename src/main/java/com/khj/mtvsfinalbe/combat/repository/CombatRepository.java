package com.khj.mtvsfinalbe.combat.repository;

import com.khj.mtvsfinalbe.combat.domain.Combat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CombatRepository extends JpaRepository<Combat, Long> {
    List<Combat> findByMemberId(Long memberId);
}