package com.khj.mtvsfinalbe.profile.controller;

import com.khj.mtvsfinalbe.profile.domain.ProfileGraph;
import com.khj.mtvsfinalbe.profile.service.ProfileGraphService;
import com.khj.mtvsfinalbe.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
@Tag(name = "Profile API", description = "사용자 프로필 누적 그래프 API")
public class ProfileGraphController {

    private final ProfileGraphService profileGraphService;
    private final UserRepository userRepository;

    public ProfileGraphController(ProfileGraphService profileGraphService, UserRepository userRepository) {
        this.profileGraphService = profileGraphService;
        this.userRepository = userRepository;
    }

    @PostMapping("/graph")
    @Operation(summary = "오늘의 그래프 데이터 저장 또는 업데이트", description = "Combat 데이터를 활용하여 그래프 데이터를 저장하거나 업데이트합니다.")
    public ResponseEntity<Void> saveOrUpdateGraph(
            @RequestParam Long userId,
            @RequestParam int assists,
            @RequestParam int kills,
            @RequestParam double accuracy,
            @RequestParam double awareness,
            @RequestParam int playTime) {
        profileGraphService.saveOrUpdateGraph(userId, assists, kills, accuracy, awareness, playTime);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/graph")
    @Operation(summary = "사용자의 누적 그래프 조회", description = "사용자의 모든 누적 그래프 데이터를 조회합니다.")
    public ResponseEntity<List<ProfileGraph>> getGraphsByUser(@RequestParam Long userId) {
        List<ProfileGraph> graphs = profileGraphService.getGraphsByUser(userId);
        return ResponseEntity.ok(graphs);
    }
}