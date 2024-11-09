package com.khj.mtvsfinalbe.combat.service;

import com.khj.mtvsfinalbe.combat.domain.Combat;
import com.khj.mtvsfinalbe.combat.domain.dto.CombatRequestDTO;
import com.khj.mtvsfinalbe.combat.domain.dto.CombatResponseDTO;
import com.khj.mtvsfinalbe.combat.repository.CombatRepository;
import org.springframework.data.domain.Sort;
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

    /**
     * 새로운 Combat 데이터를 저장하는 메서드
     * @param memberId 사용자 ID
     * @param requestDto 전투 데이터 요청 DTO
     * @return 저장된 Combat 데이터 응답 DTO
     */
  
   @Transactional
    public CombatResponseDTO saveCombat(Long memberId, CombatRequestDTO requestDto) {
        Combat combat = Combat.builder()
                .createdAt(LocalDateTime.now())
                .damageDealt(requestDto.getDamageDealt())
                .assists(requestDto.getAssists())
                .playTime(requestDto.getPlayTime())
                .score(requestDto.getScore())
                .accuracy(requestDto.getAccuracy())
                .nickname(requestDto.getNickname())
                .awareness(requestDto.getAwareness())
                .allyInjuries(requestDto.getAllyInjuries())
                .allyDeaths(requestDto.getAllyDeaths())
                .kills(requestDto.getKills())
                .memberId(memberId)
                .lastUpdated(LocalDateTime.now())
                .build();
        Combat savedCombat = combatRepository.save(combat);
        return convertToResponseDto(savedCombat);
    }


    /**
     * ID를 사용하여 특정 Combat 데이터를 조회하는 메서드
     * @param id Combat 데이터의 ID
     * @return Combat 데이터 응답 DTO
     */
    public CombatResponseDTO getCombatById(Long id) {
        return combatRepository.findById(id)
                .map(this::convertToResponseDto)
                .orElse(null);
    }

    /**
     * 특정 사용자의 최신 Combat 데이터를 조회하는 메서드
     * @param memberId 사용자 ID
     * @return 최신 Combat 데이터 응답 DTO
     */
    public CombatResponseDTO getLatestCombatByMemberId(Long memberId) {
        Combat latestCombat = combatRepository.findTopByMemberIdOrderByCreatedAtDesc(memberId);
        return latestCombat != null ? convertToResponseDto(latestCombat) : null;
    }

    /**
     * 특정 사용자의 모든 Combat 데이터를 최신순으로 조회하는 메서드
     * @param memberId 사용자 ID
     * @return 사용자 Combat 데이터 리스트 응답 DTO
     */
    public List<CombatResponseDTO> getCombatsByMemberId(Long memberId) {
        List<Combat> combats = combatRepository.findByMemberId(memberId, Sort.by(Sort.Direction.DESC, "createdAt"));
        return combats.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Combat 엔티티를 CombatResponseDTO로 변환하는 메서드
     * @param combat 변환할 Combat 엔티티
     * @return 변환된 CombatResponseDTO
     */
    private CombatResponseDTO convertToResponseDto(Combat combat) {
        CombatResponseDTO responseDto = new CombatResponseDTO();
        responseDto.setId(combat.getId());
        responseDto.setCreatedAt(combat.getCreatedAt());
        responseDto.setDamageDealt(combat.getDamageDealt());
        responseDto.setAssists(combat.getAssists());
        responseDto.setPlayTime(combat.getPlayTime());
        responseDto.setScore(combat.getScore());
        responseDto.setAccuracy(combat.getAccuracy());
        responseDto.setLastUpdated(combat.getLastUpdated());
        responseDto.setNickname(combat.getNickname());
        responseDto.setAwareness(combat.getAwareness());
        responseDto.setAllyInjuries(combat.getAllyInjuries());
        responseDto.setAllyDeaths(combat.getAllyDeaths());
        responseDto.setKills(combat.getKills());
        return responseDto;
    }
}
