package com.khj.mtvsfinalbe.combat.service;

import com.khj.mtvsfinalbe.combat.domain.Combat;
import com.khj.mtvsfinalbe.combat.domain.dto.CombatRequestDTO;
import com.khj.mtvsfinalbe.combat.domain.dto.CombatResponseDTO;
import com.khj.mtvsfinalbe.combat.repository.CombatRepository;
import com.khj.mtvsfinalbe.user.domain.User;
import com.khj.mtvsfinalbe.user.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CombatService {

    private final CombatRepository combatRepository;
    private final UserRepository userRepository;

    public CombatService(CombatRepository combatRepository, UserRepository userRepository) {
        this.combatRepository = combatRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CombatResponseDTO saveCombat(Long userId, CombatRequestDTO requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        Combat combat = Combat.builder()
                .createdAt(LocalDateTime.now())
                .damageDealt(requestDto.getDamageDealt())
                .assists(requestDto.getAssists())
                .playTime(requestDto.getPlayTime())
                .score(requestDto.getScore())
                .accuracy(requestDto.getAccuracy())
                .awareness(requestDto.getAwareness())
                .allyInjuries(requestDto.getAllyInjuries())
                .allyDeaths(requestDto.getAllyDeaths())
                .kills(requestDto.getKills())
                .user(user)
                .lastUpdated(LocalDateTime.now())
                .build();

        Combat savedCombat = combatRepository.save(combat);
        return convertToResponseDto(savedCombat);
    }

    public CombatResponseDTO getCombatById(Long id) {
        return combatRepository.findById(id)
                .map(this::convertToResponseDto)
                .orElse(null);
    }

    public CombatResponseDTO getLatestCombatByUser(User user) {
        Combat latestCombat = combatRepository.findTopByUserOrderByCreatedAtDesc(user);
        return latestCombat != null ? convertToResponseDto(latestCombat) : null;
    }

    public List<CombatResponseDTO> getCombatsByUser(User user) {
        List<Combat> combats = combatRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "createdAt"));
        return combats.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

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
        responseDto.setNickname(combat.getUser().getNickname()); // User의 닉네임 설정
        responseDto.setAwareness(combat.getAwareness());
        responseDto.setAllyInjuries(combat.getAllyInjuries());
        responseDto.setAllyDeaths(combat.getAllyDeaths());
        responseDto.setKills(combat.getKills());
        return responseDto;
    }
}
