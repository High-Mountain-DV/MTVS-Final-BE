package com.khj.mtvsfinalbe.profile.service;

import com.khj.mtvsfinalbe.profile.domain.ProfileGraph;
import com.khj.mtvsfinalbe.profile.repository.ProfileGraphRepository;
import com.khj.mtvsfinalbe.user.domain.User;
import com.khj.mtvsfinalbe.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProfileGraphService {

    private final ProfileGraphRepository profileGraphRepository;
    private final UserRepository userRepository;

    public ProfileGraphService(ProfileGraphRepository profileGraphRepository, UserRepository userRepository) {
        this.profileGraphRepository = profileGraphRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveOrUpdateGraph(Long userId, int assists, int kills, double accuracy, double awareness, int playTime) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        LocalDate today = LocalDate.now();

        ProfileGraph existingGraph = profileGraphRepository.findByUserAndDate(user, today);

        if (existingGraph != null) {
            // Update existing record
            existingGraph.setAssists(assists);
            existingGraph.setKills(kills);
            existingGraph.setAccuracy(accuracy);
            existingGraph.setAwareness(awareness);
            existingGraph.setPlayTime(playTime);
        } else {
            // Save new record
            ProfileGraph newGraph = ProfileGraph.builder()
                    .user(user)
                    .date(today)
                    .assists(assists)
                    .kills(kills)
                    .accuracy(accuracy)
                    .awareness(awareness)
                    .playTime(playTime)
                    .build();

            profileGraphRepository.save(newGraph);
        }
    }

    @Transactional(readOnly = true)
    public List<ProfileGraph> getGraphsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        return profileGraphRepository.findByUserOrderByDateAsc(user);
    }
}