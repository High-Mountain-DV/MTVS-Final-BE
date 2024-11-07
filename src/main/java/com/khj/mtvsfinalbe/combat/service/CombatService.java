package com.khj.mtvsfinalbe.combat.service;

import com.khj.mtvsfinalbe.combat.domain.Combat;
import com.khj.mtvsfinalbe.combat.dto.CombatRequestDTO;
import com.khj.mtvsfinalbe.combat.dto.CombatResponseDTO;
import com.khj.mtvsfinalbe.combat.repository.CombatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CombatService {

    private final CombatRepository combatRepository;

    public CombatService(CombatRepository combatRepository) {
        this.combatRepository = combatRepository;
    }

    @Transactional
    public CombatResponseDTO saveCombat(CombatRequestDTO requestDto) {
        Combat combat = Combat.builder()
                .created_at(requestDto.getCreated_at())
                .damage_dealt(requestDto.getDamage_dealt())
                .assists(requestDto.getAssists())
                .play_time(requestDto.getPlay_time())
                .score(requestDto.getScore())
                .accuracy(requestDto.getAccuracy())
                .nickname(requestDto.getNickname())
                .awareness(requestDto.getAwareness())
                .ally_injuries(requestDto.getAlly_injuries())
                .ally_deaths(requestDto.getAlly_deaths())
                .kills(requestDto.getKills())
                .memberId(requestDto.getMemberId())
                .last_updated(LocalDateTime.now())
                .build();
        Combat savedCombat = combatRepository.save(combat);
        return convertToResponseDto(savedCombat);
    }

    public CombatResponseDTO getCombatById(Long id) {
        return combatRepository.findById(id)
                .map(this::convertToResponseDto)
                .orElse(null);
    }

    public List<CombatResponseDTO> getCombatsByMemberId(Long memberId) {
        return combatRepository.findByMemberId(memberId).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    private CombatResponseDTO convertToResponseDto(Combat combat) {
        CombatResponseDTO responseDto = new CombatResponseDTO();
        responseDto.setId(combat.getId());
        responseDto.setCreated_at(combat.getCreated_at());
        responseDto.setDamage_dealt(combat.getDamage_dealt());
        responseDto.setAssists(combat.getAssists());
        responseDto.setPlay_time(combat.getPlay_time());
        responseDto.setScore(combat.getScore());
        responseDto.setAccuracy(combat.getAccuracy());
        responseDto.setLast_updated(combat.getLast_updated());
        responseDto.setNickname(combat.getNickname());
        responseDto.setAwareness(combat.getAwareness());
        responseDto.setAlly_injuries(combat.getAlly_injuries());
        responseDto.setAlly_deaths(combat.getAlly_deaths());
        responseDto.setKills(combat.getKills());
        return responseDto;
    }
}
