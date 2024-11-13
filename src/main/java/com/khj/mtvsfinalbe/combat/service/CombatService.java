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

    // Constructor-based dependency injection for necessary repositories and services
    public CombatService(CombatRepository combatRepository, UserRepository userRepository,
                         AICommunicationService aiCommunicationService, S3Service s3Service) {
        this.combatRepository = combatRepository;
        this.userRepository = userRepository;
        this.aiCommunicationService = aiCommunicationService;
        this.s3Service = s3Service;
    }

    /**
     * 저장된 Combat 데이터를 생성하여 DB에 저장하고 AI 서버로 전송한 후 AI의 응답을 처리하여 저장
     *
     * @param userId     Combat 데이터 생성자 (사용자)의 ID
     * @param requestDto Combat 데이터 요청 객체
     * @return 생성된 Combat 데이터의 응답 DTO
     */
    @Transactional
    public CombatResponseDTO saveCombat(Long userId, CombatRequestDTO requestDto) {
        // 사용자 정보 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        // Combat 객체 생성 및 초기화
        Combat combat = Combat.builder()
                .createdAt(LocalDateTime.now())   // 생성 시간 설정
                .damageDealt(requestDto.getDamageDealt())
                .assists(requestDto.getAssists())
                .playTime(requestDto.getPlayTime())
                .score(requestDto.getScore())
                .accuracy(requestDto.getAccuracy())
                .awareness(requestDto.getAwareness())
                .allyInjuries(requestDto.getAllyInjuries())
                .allyDeaths(requestDto.getAllyDeaths())
                .kills(requestDto.getKills())
                .user(user)                     // 연관 사용자 설정
                .lastUpdated(LocalDateTime.now())
                .build();

        // DB에 Combat 데이터 저장
        Combat savedCombat = combatRepository.save(combat);

        // AI 서버로 보낼 데이터 생성
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

        // AI 서버에 데이터 전송하고 응답 처리
        AIResponseDTO aiResponse = aiCommunicationService.sendDataToAI(aiRequestDTO);
        System.out.println("----------------> AI Response: " + aiResponse);

        // AI 응답으로 받은 radarChart 이미지 URL을 S3에 업로드하여 URL 설정
        String radarChartUrl = s3Service.uploadImage(aiResponse.getRadarChart());
        savedCombat.setRadarChart(radarChartUrl);

        // AI의 feedback 데이터를 Combat 객체에 설정
        savedCombat.setFeedback(aiResponse.getFeedback());
        savedCombat.setRadarChart(radarChartUrl);

        // 수정된 Combat 데이터 저장
        combatRepository.save(savedCombat);

        // 응답 DTO로 변환 후 반환
        return convertToResponseDto(savedCombat);
    }

    /**
     * 특정 ID의 Combat 데이터를 조회
     *
     * @param id Combat 데이터 ID
     * @return Combat 데이터의 응답 DTO
     */
    public CombatResponseDTO getCombatById(Long id) {
        // Combat 데이터 조회 및 예외 처리
        Combat combat = combatRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid combat ID"));
        return convertToResponseDto(combat);
    }

    /**
     * 특정 사용자의 모든 Combat 데이터를 조회
     *
     * @param user 조회할 사용자
     * @return Combat 데이터의 리스트 응답 DTO
     */
    public List<CombatResponseDTO> getCombatsByUser(User user) {
        // 특정 사용자의 Combat 데이터를 내림차순 정렬하여 조회
        List<Combat> combats = combatRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "createdAt"));
        // Combat 데이터를 응답 DTO로 변환 후 반환
        return combats.stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    /**
     * 특정 사용자의 가장 최신 Combat 데이터를 조회
     *
     * @param user 조회할 사용자
     * @return 최신 Combat 데이터의 응답 DTO
     */
    public CombatResponseDTO getLatestCombatByUser(User user) {
        // 특정 사용자의 최신 Combat 데이터 조회
        Combat latestCombat = combatRepository.findTopByUserOrderByCreatedAtDesc(user);
        return convertToResponseDto(latestCombat);
    }

    /**
     * Combat 데이터를 CombatResponseDTO로 변환하는 메소드
     *
     * @param combat 변환할 Combat 데이터
     * @return 변환된 CombatResponseDTO
     */
    private CombatResponseDTO convertToResponseDto(Combat combat) {
        // DTO 객체에 Combat 데이터 설정
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
        responseDto.setRadarChart(combat.getRadarChart());  // radarChart 이미지 URL
        responseDto.setFeedback(combat.getFeedback());      // AI 피드백
        return responseDto;
    }
}
