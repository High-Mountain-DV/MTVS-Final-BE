package com.khj.mtvsfinalbe.combat.repository;

import com.khj.mtvsfinalbe.combat.domain.Combat;
import com.khj.mtvsfinalbe.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CombatRepository extends JpaRepository<Combat, Long> {
    Combat findTopByUserOrderByCreatedAtDesc(User user);
    List<Combat> findByUser(User user, Sort sort);
}
