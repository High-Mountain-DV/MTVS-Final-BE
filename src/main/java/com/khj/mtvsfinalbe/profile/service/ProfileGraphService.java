package com.khj.mtvsfinalbe.profile.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khj.mtvsfinalbe.combat.domain.dto.CombatResponseDTO;
import com.khj.mtvsfinalbe.combat.service.AICommunicationService;
import com.khj.mtvsfinalbe.combat.service.CombatService;
import com.khj.mtvsfinalbe.profile.domain.ProfileGraph;
import com.khj.mtvsfinalbe.profile.domain.dto.AIRequestWrapperDTO;
import com.khj.mtvsfinalbe.profile.domain.dto.ProfileGraphResponseDTO;
import com.khj.mtvsfinalbe.profile.repository.ProfileGraphRepository;
import com.khj.mtvsfinalbe.user.domain.User;
import com.khj.mtvsfinalbe.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProfileGraphService {

    private final UserRepository userRepository;
    private final CombatService combatService;
    private final AICommunicationService aiCommunicationService;
    private final ProfileGraphRepository profileGraphRepository;

    public ProfileGraphService(UserRepository userRepository,
                               CombatService combatService,
                               AICommunicationService aiCommunicationService,
                               ProfileGraphRepository profileGraphRepository) {
        this.userRepository = userRepository;
        this.combatService = combatService;
        this.aiCommunicationService = aiCommunicationService;
        this.profileGraphRepository = profileGraphRepository;
    }

    @Transactional
    public List<ProfileGraphResponseDTO> getProfileGraphs(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        log.info("Fetching Combat data for User {}", userId);

        List<CombatResponseDTO> combatData = combatService.getCombatsByUser(user);

        log.info("Combat Data for User {}: {}", userId, combatData);

        return combatData.stream().map(combat -> {
            CombatResponseDTO previousCombat = combatService.getPreviousCombat(user, combat.getCreatedAt());

            log.info("Previous Combat for User {}, Date {}: {}", userId, combat.getCreatedAt(), previousCombat);

            AIRequestWrapperDTO.CombatData previousData = previousCombat != null
                    ? AIRequestWrapperDTO.CombatData.builder()
                    .assists(previousCombat.getAssists())
                    .kills(previousCombat.getKills())
                    .accuracy(previousCombat.getAccuracy())
                    .awareness(previousCombat.getAwareness())
                    .playTime(previousCombat.getPlayTime())
                    .build()
                    : AIRequestWrapperDTO.CombatData.builder()
                    .assists(0) // 기본값
                    .kills(0)
                    .accuracy(0.0)
                    .awareness(0.0)
                    .playTime(0)
                    .build();

            AIRequestWrapperDTO.CombatData currentData = AIRequestWrapperDTO.CombatData.builder()
                    .assists(combat.getAssists())
                    .kills(combat.getKills())
                    .accuracy(combat.getAccuracy())
                    .awareness(combat.getAwareness())
                    .playTime(combat.getPlayTime())
                    .build();

            AIRequestWrapperDTO requestWrapperDTO = AIRequestWrapperDTO.builder()
                    .previousData(previousData)
                    .currentData(currentData)
                    .build();

            try {
                log.info("AIRequestWrapperDTO JSON: {}", new ObjectMapper().writeValueAsString(requestWrapperDTO));
            } catch (Exception e) {
                log.error("Failed to serialize AIRequestWrapperDTO", e);
            }

            String aggregatedFeedback = aiCommunicationService.getAggregatedFeedback(requestWrapperDTO);

            log.info("Aggregated Feedback for Combat {}: {}", combat.getId(), aggregatedFeedback);

            ProfileGraph existingGraph = profileGraphRepository.findByUserAndDate(user, combat.getCreatedAt().toLocalDate());
            if (existingGraph != null) {
                log.warn("ProfileGraph already exists for User {}, Date {}: {}", user.getId(), combat.getCreatedAt().toLocalDate(), existingGraph);
                return ProfileGraphResponseDTO.builder()
                        .date(existingGraph.getDate())
                        .assists(existingGraph.getAssists())
                        .kills(existingGraph.getKills())
                        .accuracy(existingGraph.getAccuracy())
                        .awareness(existingGraph.getAwareness())
                        .playTime(existingGraph.getPlayTime())
                        .aggregatedFeedback(existingGraph.getAggregatedFeedback())
                        .build();
            }

            ProfileGraph profileGraph = ProfileGraph.builder()
                    .user(user)
                    .date(combat.getCreatedAt().toLocalDate())
                    .assists(combat.getAssists())
                    .kills(combat.getKills())
                    .accuracy(combat.getAccuracy())
                    .awareness(combat.getAwareness())
                    .playTime(combat.getPlayTime())
                    .aggregatedFeedback(aggregatedFeedback)
                    .build();

            try {
                log.info("Saving ProfileGraph for User {}, Date {}", userId, combat.getCreatedAt().toLocalDate());
                profileGraphRepository.save(profileGraph);
                log.info("ProfileGraph saved successfully!");
            } catch (Exception e) {
                log.error("Failed to save ProfileGraph for User {}, Date {}", userId, combat.getCreatedAt().toLocalDate(), e);
            }

            return ProfileGraphResponseDTO.builder()
                    .date(combat.getCreatedAt().toLocalDate())
                    .assists(combat.getAssists())
                    .kills(combat.getKills())
                    .accuracy(combat.getAccuracy())
                    .awareness(combat.getAwareness())
                    .playTime(combat.getPlayTime())
                    .aggregatedFeedback(aggregatedFeedback)
                    .build();
        }).collect(Collectors.toList());
    }
}
