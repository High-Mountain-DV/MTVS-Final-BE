package com.khj.mtvsfinalbe.profile.service;

import com.khj.mtvsfinalbe.combat.service.AICommunicationService;
import com.khj.mtvsfinalbe.profile.domain.ProfileGraph;
import com.khj.mtvsfinalbe.profile.domain.dto.ProfileGraphRequestDTO;
import com.khj.mtvsfinalbe.profile.domain.dto.ProfileGraphResponseDTO;
import com.khj.mtvsfinalbe.profile.repository.ProfileGraphRepository;
import com.khj.mtvsfinalbe.user.domain.User;
import com.khj.mtvsfinalbe.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ProfileGraph 비즈니스 로직을 처리하는 서비스
 */
@Service
public class ProfileGraphService {

    private final ProfileGraphRepository profileGraphRepository;
    private final UserRepository userRepository;
    private final AICommunicationService aiCommunicationService;

    public ProfileGraphService(ProfileGraphRepository profileGraphRepository, UserRepository userRepository, AICommunicationService aiCommunicationService) {
        this.profileGraphRepository = profileGraphRepository;
        this.userRepository = userRepository;
        this.aiCommunicationService = aiCommunicationService;
    }

    /**
     * 그래프 데이터를 저장하거나 업데이트하는 메서드
     *
     * @param request 사용자 요청 데이터
     */
    @Transactional
    public void saveOrUpdateGraph(ProfileGraphRequestDTO request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        LocalDate today = LocalDate.now();

        // AI에서 누적 피드백 가져오기
        String aggregatedFeedback = aiCommunicationService.getAggregatedFeedback(request);

        ProfileGraph existingGraph = profileGraphRepository.findByUserAndDate(user, today);

        if (existingGraph != null) {
            // 기존 데이터 업데이트
            existingGraph.setAssists(request.getAssists());
            existingGraph.setKills(request.getKills());
            existingGraph.setAccuracy(request.getAccuracy());
            existingGraph.setAwareness(request.getAwareness());
            existingGraph.setPlayTime(request.getPlayTime());
            existingGraph.setAggregatedFeedback(aggregatedFeedback);
        } else {
            // 새 데이터 저장
            ProfileGraph newGraph = ProfileGraph.builder()
                    .user(user)
                    .date(today)
                    .assists(request.getAssists())
                    .kills(request.getKills())
                    .accuracy(request.getAccuracy())
                    .awareness(request.getAwareness())
                    .playTime(request.getPlayTime())
                    .aggregatedFeedback(aggregatedFeedback)
                    .build();
            profileGraphRepository.save(newGraph);
        }
    }

    /**
     * 특정 사용자의 누적 그래프 데이터를 조회하는 메서드
     *
     * @param userId 사용자 ID
     * @return 누적 그래프 데이터 리스트
     */
    @Transactional(readOnly = true)
    public List<ProfileGraphResponseDTO> getGraphsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        return profileGraphRepository.findByUserOrderByDateAsc(user)
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * 엔티티를 DTO로 변환하는 메서드
     *
     * @param graph ProfileGraph 엔티티
     * @return ProfileGraphResponseDTO
     */
    private ProfileGraphResponseDTO convertToResponseDto(ProfileGraph graph) {
        return ProfileGraphResponseDTO.builder()
                .date(graph.getDate())
                .assists(graph.getAssists())
                .kills(graph.getKills())
                .accuracy(graph.getAccuracy())
                .awareness(graph.getAwareness())
                .playTime(graph.getPlayTime())
                .aggregatedFeedback(graph.getAggregatedFeedback())
                .build();
    }
}