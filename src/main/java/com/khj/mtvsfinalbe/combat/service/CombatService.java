package com.khj.mtvsfinalbe.combat.service;

import com.khj.mtvsfinalbe.combat.domain.Combat;
import com.khj.mtvsfinalbe.combat.domain.dto.AIRequestDTO;
import com.khj.mtvsfinalbe.combat.domain.dto.AIResponseDTO;
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
    private final AICommunicationService aiCommunicationService;
    private final S3Service s3Service;

    public CombatService(CombatRepository combatRepository, UserRepository userRepository,
                         AICommunicationService aiCommunicationService, S3Service s3Service) {
        this.combatRepository = combatRepository;
        this.userRepository = userRepository;
        this.aiCommunicationService = aiCommunicationService;
        this.s3Service = s3Service;
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

        AIRequestDTO aiRequestDTO = new AIRequestDTO(
                savedCombat.getId().intValue(),
                combat.getDamageDealt(),
                combat.getAssists(),
                combat.getPlayTime(),
                combat.getScore(),
                combat.getAccuracy(),
                combat.getAwareness(),
                combat.getAllyInjuries(),
                combat.getAllyDeaths(),
                combat.getKills()
        );

        AIResponseDTO aiResponse = aiCommunicationService.sendDataToAI(aiRequestDTO);
        System.out.println("----------------> AI Response: " + aiResponse);

        String radarChartUrl = s3Service.uploadImage(aiResponse.getRadarChart());
        savedCombat.setRadarChart(radarChartUrl);
        savedCombat.setFeedback(aiResponse.getFeedback());

        combatRepository.save(savedCombat);

        return convertToResponseDto(savedCombat);
    }

    public CombatResponseDTO getCombatById(Long id) {
        Combat combat = combatRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid combat ID"));
        return convertToResponseDto(combat);
    }

    public List<CombatResponseDTO> getCombatsByUser(User user) {
        List<Combat> combats = combatRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "createdAt"));
        return combats.stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    public CombatResponseDTO getLatestCombatByUser(User user) {
        Combat latestCombat = combatRepository.findTopByUserOrderByCreatedAtDesc(user);
        return convertToResponseDto(latestCombat);
    }

    @Transactional(readOnly = true)
    public CombatResponseDTO getPreviousCombat(User user, LocalDateTime currentCombatDate) {
        Combat previousCombat = combatRepository.findFirstByUserAndCreatedAtBeforeOrderByCreatedAtDesc(user, currentCombatDate);

        if (previousCombat == null) {
            CombatResponseDTO defaultResponse = new CombatResponseDTO();
            defaultResponse.setId(0L);
            defaultResponse.setCreatedAt(currentCombatDate.minusDays(1));
            defaultResponse.setDamageDealt(0.0);
            defaultResponse.setAssists(0);
            defaultResponse.setPlayTime(0);
            defaultResponse.setScore(0);
            defaultResponse.setAccuracy(0.0);
            defaultResponse.setLastUpdated(LocalDateTime.now());
            defaultResponse.setNickname(user.getNickname());
            defaultResponse.setAwareness(0.0);
            defaultResponse.setAllyInjuries(0);
            defaultResponse.setAllyDeaths(0);
            defaultResponse.setKills(0);
            defaultResponse.setRadarChart("");
            defaultResponse.setFeedback("No feedback available");

            return defaultResponse;
        }

        return convertToResponseDto(previousCombat);
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
        responseDto.setNickname(combat.getUser().getNickname());
        responseDto.setAwareness(combat.getAwareness());
        responseDto.setAllyInjuries(combat.getAllyInjuries());
        responseDto.setAllyDeaths(combat.getAllyDeaths());
        responseDto.setKills(combat.getKills());
        responseDto.setRadarChart(combat.getRadarChart());
        responseDto.setFeedback(combat.getFeedback());
        return responseDto;
    }
}
