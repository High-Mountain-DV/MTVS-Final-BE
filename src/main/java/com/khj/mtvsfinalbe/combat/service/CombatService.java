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

        // AIRequestDTO 생성, id를 int로 변환하여 설정하고 nickname을 제외
        AIRequestDTO aiRequestDTO = new AIRequestDTO();
        aiRequestDTO.setId(savedCombat.getId().intValue()); // Long 타입 id를 int로 변환하여 설정
        aiRequestDTO.setDamageDealt(combat.getDamageDealt());
        aiRequestDTO.setAssists(combat.getAssists());
        aiRequestDTO.setPlayTime(combat.getPlayTime());
        aiRequestDTO.setScore(combat.getScore());
        aiRequestDTO.setAccuracy(combat.getAccuracy());
        aiRequestDTO.setAwareness(combat.getAwareness());
        aiRequestDTO.setAllyInjuries(combat.getAllyInjuries());
        aiRequestDTO.setAllyDeaths(combat.getAllyDeaths());
        aiRequestDTO.setKills(combat.getKills());

        // AI에 데이터 전송 및 응답 처리
        AIResponseDTO aiResponse = aiCommunicationService.sendDataToAI(aiRequestDTO);
        System.out.println(aiResponse);

        // AI 분석 결과 저장
        savedCombat.setAnalysisResult(aiResponse.getResult());
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
        responseDto.setImageUrl(combat.getImageUrl());
        responseDto.setAnalysisResult(combat.getAnalysisResult());
        return responseDto;
    }
}
