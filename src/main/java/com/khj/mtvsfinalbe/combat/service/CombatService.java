package com.khj.mtvsfinalbe.combat.service;

import com.khj.mtvsfinalbe.combat.domain.Combat;
import com.khj.mtvsfinalbe.combat.repository.CombatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CombatService {

    private final CombatRepository combatRepository;

    @Autowired
    public CombatService(CombatRepository combatRepository) {
        this.combatRepository = combatRepository;
    }

    public Combat saveCombat(Combat combat) {
        return combatRepository.save(combat);
    }

    public Optional<Combat> getCombatById(Long id) {
        return combatRepository.findById(id);
    }

    public List<Combat> getCombatsByMemberId(Long memberId) {
        return combatRepository.findByMemberId(memberId);
    }

    public void deleteCombat(Long id) {
        combatRepository.deleteById(id);
    }
}
